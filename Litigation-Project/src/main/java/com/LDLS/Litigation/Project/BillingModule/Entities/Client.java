package com.LDLS.Litigation.Project.BillingModule.Entities;

import com.LDLS.Litigation.Project.BillingModule.Entities.Case;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contactNumber;
    private String email;
    // ... other client details

    @OneToMany(mappedBy = "client") // One client can have many cases
    private List<Case> cases;  // List of associated cases

    // Getters and Setters omitted for brevity
}

