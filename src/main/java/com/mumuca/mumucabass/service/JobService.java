package com.mumuca.mumucabass.service;

import com.mumuca.mumucabass.dto.request.TrackDownloadRequest;
import com.mumuca.mumucabass.model.Job;
import com.mumuca.mumucabass.model.JobStatus;
import com.mumuca.mumucabass.publisher.TrackDownloadRequestPublisher;
import com.mumuca.mumucabass.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final TrackDownloadRequestPublisher worker;

    @Autowired
    public JobService(JobRepository jobRepository, TrackDownloadRequestPublisher worker) {
        this.jobRepository = jobRepository;
        this.worker = worker;
    }

    public Job createJob(String trackId) {
        return jobRepository.save(new Job(trackId));
    }

    public Job findJobById(String jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }

    public void sendToWorker(Job job) {
        TrackDownloadRequest request = new TrackDownloadRequest(job.getId(), job.getTrackId());

        worker.sendTrackDownloadRequest(request);
    }

    @Transactional
    public void updateJobStatus(String jobId, JobStatus status) {
        Job job = jobRepository.findById(jobId).orElse(null);

        if (job == null || job.getStatus() == JobStatus.COMPLETED) return;

        job.setStatus(status);

        if (status == JobStatus.COMPLETED) {
            job.setCompletedAt(LocalDateTime.now());
        }

        jobRepository.save(job);
    }
}
