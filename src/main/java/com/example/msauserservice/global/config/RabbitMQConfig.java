package com.example.msauserservice.global.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchange 설정
    public static final String EXCHANGE = "user.exchange";

    // Queue 설정
    public static final String USER_CREATED_QUEUE = "user.created.queue";
    public static final String USER_DELETED_QUEUE = "user.deleted.queue";
    public static final String USER_UPDATED_QUEUE = "user.updated.queue";

    // RoutingKey 설정
    public static final String USER_CREATED_ROUTING_KEY = "user.created";
    public static final String USER_DELETED_ROUTING_KEY = "user.deleted";
    public static final String USER_UPDATED_ROUTING_KEY = "user.updated";

    //Bean 등록
    // exchange 설정
    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(EXCHANGE);
    }

    // queue 설정
    @Bean
    public Queue userCreatedQueue() {
        return new Queue(USER_CREATED_QUEUE);
    }

    @Bean
    public Queue userDeletedQueue() {
        return new Queue(USER_DELETED_QUEUE);
    }

    @Bean
    public Queue userUpdatedQueue() {
        return new Queue(USER_UPDATED_QUEUE);
    }

    // binding 설정
    @Bean
    public Binding userCreatedBinding(Queue userCreatedQueue, DirectExchange exchange) {
        return BindingBuilder.bind(userCreatedQueue)
                .to(exchange)
                .with(USER_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding userUpdatedBinding(Queue userUpdatedQueue, DirectExchange exchange) {
        return BindingBuilder.bind(userUpdatedQueue)
                .to(exchange)
                .with(USER_UPDATED_ROUTING_KEY);
    }

    @Bean
    public Binding userDeletedBinding(Queue userDeletedQueue, DirectExchange exchange) {
        return BindingBuilder.bind(userDeletedQueue)
                .to(exchange)
                .with(USER_DELETED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
