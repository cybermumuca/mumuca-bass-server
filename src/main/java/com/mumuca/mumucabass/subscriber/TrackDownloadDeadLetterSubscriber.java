package com.mumuca.mumucabass.subscriber;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackDownloadDeadLetterSubscriber {

    private final RabbitTemplate rabbitTemplate;
    private final Binding trackDownloadBinding;

    @Autowired
    public TrackDownloadDeadLetterSubscriber(RabbitTemplate rabbitTemplate, Binding trackDownloadBinding) {
        this.rabbitTemplate = rabbitTemplate;
        this.trackDownloadBinding = trackDownloadBinding;
    }

    @RabbitListener(queues = "${mq.queues.track-download-dlq}")
    public void consumeDeadLetterQueueMessage(Message message) {
        var xDeathHeader = message.getMessageProperties().getXDeathHeader();

        long retries = xDeathHeader == null ? 0 : (long) xDeathHeader.getFirst().get("count");

        if (retries < 3) {
            System.out.println("Reenviando mensagem para a fila principal...");
            rabbitTemplate.send(trackDownloadBinding.getExchange(), trackDownloadBinding.getRoutingKey(), message);
        } else {
            System.out.println("Descartando mensagem: " + new String(message.getBody()));
        }
    }
}
