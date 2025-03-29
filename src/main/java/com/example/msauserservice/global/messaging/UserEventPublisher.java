package com.example.msauserservice.global.messaging;

import com.example.msauserservice.global.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public UserEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserCreated(String userId, String username, String email) {
        UserEventDto event = new UserEventDto(userId, username, email, "USER_CREATED");
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.USER_CREATED_ROUTING_KEY,
                event
        );
    }

    public void publishUserUpdated(String userId, String username, String email) {
        UserEventDto event = new UserEventDto(userId, username, email, "USER_UPDATED");
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.USER_UPDATED_ROUTING_KEY,
                event
        );
    }

    public void publishUserDeleted(String userId) {  // 삭제 이벤트 추가
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.USER_DELETED_ROUTING_KEY,
                userId
        );
    }
}
