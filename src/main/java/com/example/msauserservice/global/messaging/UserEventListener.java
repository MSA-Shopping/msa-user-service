package com.example.msauserservice.global.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.example.msauserservice.global.config.RabbitMQConfig;

@Component
public class UserEventListener {

    @RabbitListener(queues = RabbitMQConfig.USER_CREATED_QUEUE)
    public void handleUserCreatedEvent(UserEventDto userCreatedEvent) {
        System.out.println("Received user created event: " + userCreatedEvent);
    }

    @RabbitListener(queues = RabbitMQConfig.USER_UPDATED_QUEUE)
    public void handleUserUpdatedEvent(UserEventDto userUpdatedEvent) {
        System.out.println("Received user updated event: " + userUpdatedEvent);
    }
}