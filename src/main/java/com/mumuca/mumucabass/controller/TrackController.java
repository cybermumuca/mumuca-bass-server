package com.mumuca.mumucabass.controller;

import com.mumuca.mumucabass.api.deezer.data.DeezerTrackSearch;
import com.mumuca.mumucabass.dto.response.TrackDTO;
import com.mumuca.mumucabass.model.Job;
import com.mumuca.mumucabass.service.JobService;
import com.mumuca.mumucabass.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/tracks")
public class TrackController {

    private final TrackService trackService;
    private final JobService jobService;

    @Autowired
    public TrackController(TrackService trackService, JobService jobService) {
        this.trackService = trackService;
        this.jobService = jobService;
    }

    @GetMapping("/search")
    public DeezerTrackSearch search(@RequestParam("query") String query) {
        return trackService.search(query);
    }

    @GetMapping("/{id}")
    public TrackDTO getTrack(@PathVariable("id") long id) {
        return trackService.getTrack(id);
    }

    @GetMapping("/{id}/stream")
    public String streamTrack(@PathVariable("id") long id) {
        return trackService.streamTrack(id);
    }

    @PostMapping("/{id}/download")
    public ResponseEntity<Map<String, String>> requestDownload(@PathVariable("id") long id) {
        Job job = jobService.createJob(String.valueOf(id));

        jobService.sendToWorker(job);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("jobId", job.getId()));
    }

    @GetMapping("/download/{jobId}")
    public ResponseEntity<Object> download(@PathVariable("jobId") String jobId) {
        Job job = jobService.findJobById(jobId);

        if (job == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Job Not Found"));
        }

        return switch (job.getStatus()) {
            case PENDING -> ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(Map.of("status", "PENDING"));

            case IN_PROGRESS -> ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(Map.of("status", "IN_PROGRESS"));

            // TODO: send file
            case COMPLETED -> ResponseEntity.ok("Job completed, download available.");

            case FAILED -> ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Map.of("status", "FAILED"));
        };
    }
}
