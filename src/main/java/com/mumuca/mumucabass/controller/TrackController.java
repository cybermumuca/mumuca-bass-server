package com.mumuca.mumucabass.controller;

import com.mumuca.mumucabass.api.deezer.data.DeezerTrackSearch;
import com.mumuca.mumucabass.dto.response.TrackDTO;
import com.mumuca.mumucabass.model.Job;
import com.mumuca.mumucabass.service.JobService;
import com.mumuca.mumucabass.service.TrackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TrackController {

    private static final Logger logger = LoggerFactory.getLogger(TrackController.class);

    private final TrackService trackService;
    private final JobService jobService;

    @Autowired
    public TrackController(TrackService trackService, JobService jobService) {
        this.trackService = trackService;
        this.jobService = jobService;
    }

    @GetMapping("/v1/tracks/search")
    public DeezerTrackSearch search(@RequestParam("query") String query) {
        logger.info("GET /api/v1/tracks/search?query={}", query);

        return trackService.search(query);
    }

    @GetMapping("/v1/tracks/{id}")
    public TrackDTO getTrack(@PathVariable("id") long id) {
        logger.info("GET /api/v1/tracks/{}", id);

        return trackService.getTrack(id);
    }

    @GetMapping("/v1/tracks/{id}/stream")
    public String streamTrack(@PathVariable("id") long id) {
        return trackService.streamTrack(id);
    }

    @PostMapping("/v1/tracks/{id}/download")
    public ResponseEntity<Map<String, String>> requestDownload(@PathVariable("id") long id) {
        logger.info("POST /api/v1/tracks/{}/download", id);

        Job job = jobService.createJob(String.valueOf(id));

        jobService.sendToWorker(job);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("jobId", job.getId()));
    }

    @GetMapping("/v1/tracks/download/{jobId}")
    public ResponseEntity<Object> download(@PathVariable("jobId") String jobId) {
        logger.info("GET /api/v1/tracks/download/{}", jobId);

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
