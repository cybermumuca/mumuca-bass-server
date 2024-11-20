package com.mumuca.mumucabass.subscriber;

import com.mumuca.mumucabass.dto.request.TrackDownloadStatus;
import com.mumuca.mumucabass.service.JobService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackDownloadStatusSubscriber {

    private final JobService jobService;

    @Autowired
    public TrackDownloadStatusSubscriber(JobService jobService) {
        this.jobService = jobService;
    }

    @RabbitListener(queues = "${mq.queues.track-download-status}")
    public void consumeMessage(TrackDownloadStatus message) {
        jobService.updateJobStatus(message.jobId(), message.status());
    }
}
