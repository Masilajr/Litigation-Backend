package com.LDLS.Litigation.Project.ClientManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Client")
public class ClientManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String firstName;
    private String lastName;
    private Long idNumber;
    private String kraPinNumber;
    private String emailAddress;
    private Long   phoneNumber;
    private Long   alternativePhoneNumber;
    private String postalAddress;
    private String postalCode;
    private String city;
    private String country;
    private String  clientCode;
    private Long loanAccountNumber;
    private Double loanAmount;
    private String interestRate;
    private Date defaultDate;
    private Double amountRemaining;
    private String loanDescription;

    // active, pending, closed
    private long activeCases;
    private long pendingCases;
    private long closedCases;

    private String status;
    private String details;
    private LocalDateTime lastUpdated;

    public ClientManagement(long activeCases, long pendingCases, long closedCases) {
        this.activeCases = activeCases;
        this.pendingCases = pendingCases;
        this.closedCases = closedCases;
    }
}
