package com.mumuca.mumucabass.request;

import com.mumuca.mumucabass.model.JobStatus;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

public record JobStatusRequest(
        @NotNull(message = "jobId cannot be null")
        @UUID(message = "jobId must be a valid UUID")
        String jobId,

        @NotNull(message = "status cannot be null")
        JobStatus status
) {}

