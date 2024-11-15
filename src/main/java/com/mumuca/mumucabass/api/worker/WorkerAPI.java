package com.mumuca.mumucabass.api.worker;

import com.mumuca.mumucabass.api.worker.data.JobRequest;
import com.mumuca.mumucabass.api.worker.data.JobResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "workerClient", url = "http://localhost:3001")
public interface WorkerAPI {

    @PostMapping("/job/track/download")
    @Headers("Connection: keep-alive")
    ResponseEntity<JobResponse> sendJob(@RequestBody JobRequest jobDetails);
}
