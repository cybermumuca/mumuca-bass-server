package com.mumuca.mumucabassstorage.dto;

import jakarta.validation.constraints.NotBlank;

public record JobUploadRequestDTO(
        @NotBlank(message = "trackId é obrigatório.") String trackId,
        @NotBlank(message = "jobId é obrigatório.") String jobId
) {}
