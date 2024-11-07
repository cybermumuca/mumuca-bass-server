package com.mumuca.mumucabass.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "track_id", nullable = false)
    private String trackId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status = JobStatus.PENDING;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public Job() {
    }

    public Job(String trackId) {
        this.trackId = trackId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
