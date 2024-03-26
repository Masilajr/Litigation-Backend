package com.LDLS.Litigation.Project.BillingModule.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Case {
    @Id
    private Long id;
    private Long clientId;

    @ManyToOne
    @JoinColumn(name = "client", nullable = false) // Single override
    private Client client;
}

//    private Long clientId; // Foreign key referencing Client
//
//    // Getters and Setters (omitted for brevity)
//
//    @ManyToOne
//    @Column(name = "client_id")
//    private Client client;
//}

