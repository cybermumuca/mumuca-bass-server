package com.mumuca.mumucabass.dto.request;

import com.mumuca.mumucabass.model.JobStatus;

import java.time.Instant;

public record TrackDownloadStatus(String jobId, JobStatus status, Instant timestamp) {}
