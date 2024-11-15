package com.mumuca.mumucabass.service;

import com.mumuca.mumucabass.api.worker.WorkerAPI;
import com.mumuca.mumucabass.api.worker.data.JobRequest;
import com.mumuca.mumucabass.model.Job;
import com.mumuca.mumucabass.model.JobStatus;
import com.mumuca.mumucabass.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class JobService {

    private final JobRepository jobRepository;
    private final WorkerAPI worker;

    @Autowired
    public JobService(JobRepository jobRepository, WorkerAPI worker) {
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
        JobRequest jobRequest = new JobRequest(job.getId(), job.getTrackId());

        var workerResponse = worker.sendJob(jobRequest);

        if (workerResponse.getStatusCode().isError()) {
            job.setStatus(JobStatus.FAILED);
            jobRepository.save(job);
        }
    }

    @Transactional
    public void updateJobStatus(String jobId, JobStatus status) {
        Job job = jobRepository.findById(jobId).orElse(null);

        if (job == null) return;

        job.setStatus(status);

        if (status == JobStatus.COMPLETED) {
            job.setCompletedAt(LocalDateTime.now());
        }

        jobRepository.save(job);
    }
}
