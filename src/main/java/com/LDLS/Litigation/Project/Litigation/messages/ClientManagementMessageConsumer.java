package com.LDLS.Litigation.Project.Litigation.messages;
import com.LDLS.Litigation.Project.Litigation.dtos.LitigationCaseDTO;
import com.LDLS.Litigation.Project.Litigation.services.LitigationCaseInitiator;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ClientManagementMessageConsumer {
    private final LitigationCaseInitiator litigationCaseInitiator;

    public ClientManagementMessageConsumer(LitigationCaseInitiator litigationCaseInitiator) {
        this.litigationCaseInitiator = litigationCaseInitiator;
    }

    @RabbitListener(queues = "newCaseQueue")
    public void consumeMessage(LitigationCaseDTO litigationCaseDTO) {
        litigationCaseInitiator.processClientData(litigationCaseDTO);
    }
}
