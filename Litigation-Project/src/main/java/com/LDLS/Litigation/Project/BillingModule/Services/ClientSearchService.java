package com.LDLS.Litigation.Project.BillingModule.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class ClientSearchService {
    @Autowired
    private WebClient webClient;

    public Mono<List> searchClients(String clientCode, Long loanAccNo) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParamIfPresent("clientCode", Optional.ofNullable(clientCode))
                        .queryParamIfPresent("loanAccNo", Optional.ofNullable(loanAccNo))
                        .build())
                .retrieve()
                .bodyToMono(List.class);
    }
}
