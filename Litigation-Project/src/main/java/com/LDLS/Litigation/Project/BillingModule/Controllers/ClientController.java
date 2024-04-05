package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Entities.Client;
import com.LDLS.Litigation.Project.BillingModule.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.findClientById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAllClients();
        return ResponseEntity.ok(clients);
    }

    // ... other methods for client information retrieval
}

