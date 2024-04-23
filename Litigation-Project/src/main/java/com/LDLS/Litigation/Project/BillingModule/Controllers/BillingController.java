package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Services.ClientSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class BillingController {
    @Autowired
    private ClientSearchService clientSearchService;

    @GetMapping("/billing/search")
    public Mono<List> searchClients(@RequestParam(required = false) String clientCode, @RequestParam(required = false) Long loanAccNo) {
        return clientSearchService.searchClients(clientCode, loanAccNo);
    }
}
