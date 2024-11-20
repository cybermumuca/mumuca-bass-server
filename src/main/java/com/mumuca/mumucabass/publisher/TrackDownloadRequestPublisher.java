package com.mumuca.mumucabass.publisher;

import com.mumuca.mumucabass.dto.request.TrackDownloadRequest;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackDownloadRequestPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Binding trackDownloadBinding;

    @Autowired
    public TrackDownloadRequestPublisher(RabbitTemplate rabbitTemplate, Binding trackDownloadBinding) {
        this.rabbitTemplate = rabbitTemplate;
        this.trackDownloadBinding = trackDownloadBinding;
    }

    public void sendTrackDownloadRequest(TrackDownloadRequest request) {
        rabbitTemplate.convertAndSend(
                trackDownloadBinding.getExchange(),
                trackDownloadBinding.getRoutingKey(),
                request
        );
    }
}
