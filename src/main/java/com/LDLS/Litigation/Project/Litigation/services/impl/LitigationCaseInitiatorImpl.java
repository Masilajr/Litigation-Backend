package com.LDLS.Litigation.Project.Litigation.services.impl;
import com.LDLS.Litigation.Project.Litigation.dtos.LitigationCaseDTO;
import com.LDLS.Litigation.Project.Litigation.models.LitigationCase;
import com.LDLS.Litigation.Project.Litigation.repositories.LitigationCaseRepository;
import com.LDLS.Litigation.Project.Litigation.services.LitigationCaseInitiator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

@Service
public class LitigationCaseInitiatorImpl implements LitigationCaseInitiator {

    private final LitigationCaseRepository caseRepository;
    private final ModelMapper modelMapper;
    private final WebClient webClient;
    private final Validator validator;

    @Autowired
    public LitigationCaseInitiatorImpl(LitigationCaseRepository caseRepository, ModelMapper modelMapper) {
        this.caseRepository = caseRepository;
        this.modelMapper = modelMapper;
        this.webClient = WebClient.create("http://client-management-system.com/api");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = (Validator) factory.getValidator();
    }

    @Override
    public void processClientData(LitigationCaseDTO litigationCaseDTO) {
        Errors errors = new BeanPropertyBindingResult(litigationCaseDTO, "litigationCaseDTO");
        validator.validate(litigationCaseDTO, errors);
        if (errors.hasErrors()) {
            throw new IllegalArgumentException(errors.getAllErrors().toString());
        }
        try {
            LitigationCase litigationCase = modelMapper.map(litigationCaseDTO, LitigationCase.class);
            caseRepository.save(litigationCase);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process client data", e);
        }
    }

    public void fetchAndProcessCaseData(String caseId) {
        Mono<LitigationCaseDTO> caseDataMono = webClient.get()
                .uri("/cases/{caseId}", caseId)
                .retrieve()
                .bodyToMono(LitigationCaseDTO.class);

        caseDataMono.subscribe(litigationCaseDTO -> {
            try {
                processClientData(litigationCaseDTO);
            } catch (Exception e) {
                System.err.println("Failed to process fetched case data: " + e.getMessage());
            }
        });
    }
}
