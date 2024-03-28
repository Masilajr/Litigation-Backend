package com.LDLS.Litigation.Project.BillingModule.Entities;

import com.LDLS.Litigation.Project.BillingModule.Services.ClientService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    // ... existing class definition

    @Id
    @GeneratedValue
    private Long id;

    // ... other client details

    @Autowired
    @Transient // Make ClientService transient if not meant for persistence
    private ClientService clientService;

    public Client fetchClientDetails(Long clientId) {
        // Delegate client retrieval to the ClientService
        return clientService.getClientById(clientId);
    }

//    @OneToMany(mappedBy = "client") // One client can have many cases
//    private List<Case> cases;  // List of associated cases

    // Getters and Setters omitted for brevity
}
