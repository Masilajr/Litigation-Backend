package com.LDLS.Litigation.Project.ClientManagement.dtos;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ClientManagementDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Long idNumber;
    private String kraPin;
    private String emailAddr;
    private Long phoneNo1;
    private Long phoneNo2;
    private String postalAddress;
    private String postalCode;
    private String city;
    private String country;
    private Long loanAmount;
    private String clientCode;
    private Long loanAccNo;
    private Double originalLoanAmount;
    private String interestRate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String outPrincipal;
    private String outInterest;
    private LocalDateTime lastAccrualDate;
    private LocalDateTime lastPaymentDate;
    private String lastPayReceived;
    private LocalDateTime lastIntAppDate;
    private String userAssetClass;
    private LocalDateTime classificationDate;
    private String loanTenor;
    private String defaultReason;
    private LocalDateTime defaultDate;
    private String transferringOffice;
    private Double amountRemaining;
    private String loanDescription;
    private String department;
    private String officer;
    private LocalDateTime deadline;
    private String priority;
    private String additionalInfo;
    private long totalClients;
    private long activeClients;
    private long pendingClients;
    private long litigationClients;
    private String status;
    private String details;
    private LocalDateTime lastUpdated;

}
