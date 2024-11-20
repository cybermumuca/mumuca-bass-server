package com.mumuca.mumucabass.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
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

    @Value("${mq.queues.track-download-status}")
    private String trackDownloadStatusQueue;

    @Value("${mq.queues.track-download-status-dlq}")
    private String trackDownloadStatusDeadLetterQueue;

    @Value("${mq.exchanges.track}")
    private String trackExchange;

    @Value("${mq.exchanges.track-dlx}")
    private String trackDeadLetterExchange;

    @Value("${mq.routing-keys.track-download}")
    private String trackDownloadRoutingKey;

    @Value("${mq.routing-keys.track-download-dlq}")
    private String trackDownloadDeadLetterRoutingKey;

    @Value("${mq.routing-keys.track-download-status}")
    private String trackDownloadStatusRoutingKey;

    @Value("${mq.routing-keys.track-download-status-dlq}")
    private String trackDownloadStatusDeadLetterRoutingKey;

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
    public Queue trackDownloadStatusQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", trackDeadLetterExchange);
        args.put("x-dead-letter-routing-key", trackDownloadStatusDeadLetterRoutingKey);
        args.put("x-message-ttl", 10000);// 10 seconds
        return new Queue(trackDownloadStatusQueue, true, false, false, args);
    }

    @Bean
    public Queue trackDownloadStatusDeadLetterQueue() {
        return new Queue(trackDownloadStatusDeadLetterQueue, true);
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
    public Binding trackDownloadStatusBinding(Queue trackDownloadStatusQueue, DirectExchange trackExchange) {
        return BindingBuilder
                .bind(trackDownloadStatusQueue)
                .to(trackExchange)
                .with(trackDownloadStatusRoutingKey);
    }

    @Bean
    public Binding trackDownloadStatusDeadLetterBinding(Queue trackDownloadStatusDeadLetterQueue, DirectExchange trackDeadLetterExchange) {
        return BindingBuilder
                .bind(trackDownloadStatusDeadLetterQueue)
                .to(trackDeadLetterExchange)
                .with(trackDownloadStatusDeadLetterRoutingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        template.setBeforePublishPostProcessors(message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });

        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter());
        return factory;
    }
}
