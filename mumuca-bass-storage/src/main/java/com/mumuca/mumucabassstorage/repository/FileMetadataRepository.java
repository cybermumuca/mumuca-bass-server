package com.mumuca.mumucabassstorage.repository;

import com.mumuca.mumucabassstorage.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, String> {
    Optional<FileMetadata> findByJobId(String jobId);
}