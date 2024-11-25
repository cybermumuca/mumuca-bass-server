package com.mumuca.mumucabassstorage;

import com.mumuca.mumucabassstorage.config.UploadConfig;
import com.mumuca.mumucabassstorage.exception.FileStorageException;
import com.mumuca.mumucabassstorage.model.FileMetadata;
import com.mumuca.mumucabassstorage.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class StorageService {

    private final Path fileStorageLocation;
    private final Path tmpStorageLocation;

    private static final List<String> ALLOWED_MIME_TYPES = List.of(
            "audio/mpeg",    // MP3
            "audio/wav",     // WAV
            "audio/ogg",     // OGG
            "audio/flac"     // FLAC
    );

    private final FileMetadataRepository fileMetadataRepository;

    @Autowired
    public StorageService(UploadConfig config, FileMetadataRepository fileMetadataRepository) {
        this.fileStorageLocation = Paths.get(config.getUploadDir())
                .toAbsolutePath()
                .normalize();
        this.tmpStorageLocation = Paths.get(config.getTmpDirectory())
                .toAbsolutePath()
                .normalize();
        this.fileMetadataRepository = fileMetadataRepository;
    }

    public FileMetadata storeFileTemporarily(MultipartFile file, String jobId) {
        this.validateFile(file);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path savedPath = this.tmpStorageLocation.resolve(uniqueName);

        try {
            file.transferTo(savedPath);
        } catch (IOException ioex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ioex);
        }

        FileMetadata metadata = new FileMetadata();

        metadata.setOriginalName(fileName);
        metadata.setUniqueName(uniqueName);
        metadata.setSavedPath(savedPath);
        metadata.setJobId(jobId);
        metadata.setTemporary(true);

        return fileMetadataRepository.save(metadata);
    }

    public FileMetadata getFileMetadata(String jobId) {
        return fileMetadataRepository
                .findByJobId(jobId)
                .orElse(null);
    }

    public void deleteFileMetadata(String id) {
        fileMetadataRepository.deleteById(id);
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileStorageException("Please send an audio file.");
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null || fileName.isBlank()) {
            throw new FileStorageException("The uploaded file does not have a name.");
        }

        String mimeType = file.getContentType();

        if (mimeType == null) {
            throw new FileStorageException("Could not detect file type.");
        }

        if (!ALLOWED_MIME_TYPES.contains(mimeType)) {
            throw new FileStorageException("The uploaded file is not supported.");
        }
    }
}
