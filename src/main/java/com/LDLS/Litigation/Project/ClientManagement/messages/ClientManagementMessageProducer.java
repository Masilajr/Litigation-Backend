package com.LDLS.Litigation.Project.ClientManagement.messages;

import com.LDLS.Litigation.Project.ClientManagement.dto.ClientManagementDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClientManagementMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public ClientManagementMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ClientManagementDTO clientManagementDTO) {
        rabbitTemplate.convertAndSend("newCaseQueue", clientManagementDTO);
    }
}
