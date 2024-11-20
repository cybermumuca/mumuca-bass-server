package com.mumuca.mumucabass.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Value("${mq.queues.track-download}")
    private String trackDownloadQueue;

    @Value("${mq.queues.track-download-dlq}")
    private String trackDownloadDeadLetterQueue;

    @Value("${mq.exchanges.track}")
    private String trackExchange;

    @Value("${mq.exchanges.track-dlx}")
    private String trackDeadLetterExchange;

    @Value("${mq.routing-keys.track-download}")
    private String trackDownloadRoutingKey;

    @Value("${mq.routing-keys.track-download-dlq}")
    private String trackDownloadDeadLetterRoutingKey;

    @Bean
    public Queue trackDownloadQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", trackDeadLetterExchange);
        args.put("x-dead-letter-routing-key", trackDownloadDeadLetterRoutingKey);
        args.put("x-message-ttl", 10000); // 10 seconds
        return new Queue(trackDownloadQueue, true, false, false, args);
    }

    @Bean
    public Queue trackDownloadDeadLetterQueue() {
        return new Queue(trackDownloadDeadLetterQueue, true);
    }

    @Bean
    public DirectExchange trackExchange() {
        return new DirectExchange(trackExchange, true, false);
    }

    @Bean
    public DirectExchange trackDeadLetterExchange() {
        return new DirectExchange(trackDeadLetterExchange, true, false);
    }

    @Bean
    public Binding trackDownloadBinding(Queue trackDownloadQueue, DirectExchange trackExchange) {
        return BindingBuilder
                .bind(trackDownloadQueue)
                .to(trackExchange)
                .with(trackDownloadRoutingKey);
    }

    @Bean
    public Binding trackDownloadDeadLetterBinding(Queue trackDownloadDeadLetterQueue, DirectExchange trackDeadLetterExchange) {
        return BindingBuilder
                .bind(trackDownloadDeadLetterQueue)
                .to(trackDeadLetterExchange)
                .with(trackDownloadDeadLetterRoutingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        template.setBeforePublishPostProcessors(message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });

        return template;
    }
}
