package com.LDLS.Litigation.Project.Litigation.listeners;//package com.emtech.Litigation.listeners;
//import com.emtech.Litigation.dtos.LitigationCaseDTO;
//import com.emtech.Litigation.services.LitigationCaseInitiator;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CaseMessageListener {
//
//    private final LitigationCaseInitiator litigationCaseInitiator;
//
//    @Autowired
//    public CaseMessageListener(LitigationCaseInitiator litigationCaseInitiator) {
//        this.litigationCaseInitiator = litigationCaseInitiator;
//    }
//
//    @RabbitListener(queues = "newCaseQueue")
//    public void handleNewCase(LitigationCaseDTO litigationCaseDTO) {
//        litigationCaseInitiator.processClientData(litigationCaseDTO);
//    }
//}
