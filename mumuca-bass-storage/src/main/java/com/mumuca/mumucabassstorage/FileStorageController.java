package com.mumuca.mumucabassstorage;

import com.mumuca.mumucabassstorage.dto.JobUploadRequestDTO;
import com.mumuca.mumucabassstorage.exception.FileStorageException;
import com.mumuca.mumucabassstorage.model.FileMetadata;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;

@RestController
@RequestMapping("/api")
@Validated
public class FileStorageController {

    private final StorageService storageService;

    @Autowired
    public FileStorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/v1/files/track/tmp/upload")
    public ResponseEntity<Void> uploadTrack(
            @RequestParam("file") MultipartFile file,
            @Valid @ModelAttribute JobUploadRequestDTO job
    ) {
        FileMetadata fileMetadata = storageService.storeFileTemporarily(file, job.jobId());

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/files/track/tmp/")
                .path(fileMetadata.getJobId())
                .path("/download")
                .build()
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/v1/files/track/tmp/{jobId}/download")
    public ResponseEntity<Resource> serveTrack(@PathVariable String jobId) {
        FileMetadata metadata = storageService.getFileMetadata(jobId);

        if (metadata == null) {
            return ResponseEntity.notFound().build();
        }

        File file = new File(metadata.getSavedPath());

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource;

        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            throw new FileStorageException("File not found", ex);
        }

        if (file.delete()) {
            System.out.println("File deleted: " + file.getAbsolutePath());
        }

        storageService.deleteFileMetadata(metadata.getId());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + metadata.getOriginalName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
