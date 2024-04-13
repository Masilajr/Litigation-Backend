package com.LDLS.Litigation.Project.ClientManagement;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Configuration
public class ClientManagementService {

    @Autowired
    private ClientManagementRepository clientManagementRepository;
    public long countActiveClients() {
        return clientManagementRepository.countByStatus("active");
    }

    public long countPendingClients() {
        return clientManagementRepository.countByStatus("pending");
    }

    public long countTotalClients() {
        // Assuming "total" means all clients regardless of status
        return clientManagementRepository.count();
    }

    public long countLitigationClients() {
        return clientManagementRepository.countByStatus("litigation");
    }

    public List<ClientManagement> getActiveClients() {
        return clientManagementRepository.findByStatus("active");
    }

    public List<ClientManagement> getPendingClients() {
        return clientManagementRepository.findByStatus("pending");
    }

    public List<ClientManagement> getLitigationClients() {
        return clientManagementRepository.findByStatus("litigation");
    }

    // Assuming "total" means all clients regardless of status
    public List<ClientManagement> getTotalClients() {
        return clientManagementRepository.findAll();
    }

    public List<ClientManagement> searchByClientOrLoan(String clientCode, Long loanAccNo) {
        if (clientCode == null && loanAccNo == null) {
            throw new IllegalArgumentException("At least one of clientCode or loanAccNo must be provided.");
        }
        return clientManagementRepository.findByClientCodeOrLoanAccNo(clientCode, loanAccNo);
    }


    public EntityResponse assignOfficerToClient(Long id, ClientRequest request) {
        EntityResponse response = new EntityResponse<>();
        try {
            ClientManagement client = clientManagementRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            client.setOfficer(request.getOfficer());
            client.setAdditionalInfo(request.getAdditionalInfo());
            client.setPriority(request.getPriority());
            client.setDepartment(request.getDepartment());
            client.setDeadline(request.getDeadline());
            client.setStatus("Active");
            clientManagementRepository.save(client);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Client Assigned Successfully!");
            response.setEntity(client);
        }catch (RuntimeException e) {
            System.err.println("Error assigning officer to client: " + e.getMessage());
        }
        return response;
    }

    public void transferClientToLitigation(String clientCode) {
        try {
            ClientManagement client = clientManagementRepository.findByClientCode(clientCode)
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            client.setStatus("Litigation");
            clientManagementRepository.save(client);
            System.out.println("Client " + clientCode + " has been transferred to Litigation.");
        } catch (RuntimeException e) {
            System.err.println("Error transferring client to Litigation: " + e.getMessage());
        }
    }


    public EntityResponse add(ClientManagement clientManagement) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            String dayMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMM"));
            String randomDigits = String.format("%04d", new Random().nextInt(10000));
            String clientCode = "CAD" + "/" + dayMonth + "/" + randomDigits;
            clientManagement.setClientCode(clientCode);
            clientManagement.setStatus("Pending");
            ClientManagement savedCase = clientManagementRepository.save(clientManagement);
            entityResponse.setMessage("Client added successfully");
            entityResponse.setStatusCode(HttpStatus.CREATED.value());
            entityResponse.setEntity(savedCase);
        } catch (Exception e) {
            log.error("Error occurred while adding client: {}", e.getMessage());
            entityResponse.setMessage("Failed to add client");
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return entityResponse;
    }

    public List<ClientManagement> getAllClients() {
        return clientManagementRepository.findAll();
    }
//    public Map<String, Long> getLoanSummary(List<ClientManagement> clients) {
//        return clients.stream()
//                .collect(Collectors.groupingBy(ClientManagement::getLoanDescription,
//                        Collectors.summingLong(ClientManagement::getLoanAmount)));
//    }
public Map<String, Map<String, Long>> getLoanSummary(List<ClientManagement> clients) {
    return clients.stream()
            .filter(c -> c.getLoanDescription() != null && c.getOutPrincipal() != null) // Ensure loanDescription and outPrincipal are not null
            .collect(Collectors.groupingBy(ClientManagement::getLoanDescription,
                    Collectors.collectingAndThen(
                            Collectors.toList(),
                            list -> {
                                long sum = list.stream()
                                        .mapToLong(c -> {
                                            try {
                                                return Long.parseLong(c.getOutPrincipal());
                                            } catch (NumberFormatException e) {
                                                // Log the error or handle it as needed
                                                return 0L; // Return 0 for invalid values
                                            }
                                        })
                                        .sum();

                                Map<String, Long> result = new HashMap<>();
                                result.put("count", (long) list.size());
                                result.put("sum", sum);
                                return result;
                            })));
}



    public EntityResponse update(ClientManagement clientManagement) {
        EntityResponse entityResponse = new EntityResponse<>();
        Optional<ClientManagement> existingClient = clientManagementRepository.findById(clientManagement.getId());
        try {
            if (existingClient.isPresent()) {
                ClientManagement clientManagement1 = existingClient.get();
                // Update all fields from clientManagement to clientManagement1
                clientManagement1.setFirstName(clientManagement.getFirstName());
                clientManagement1.setMiddleName(clientManagement.getMiddleName());
                clientManagement1.setLastName(clientManagement.getLastName());
                clientManagement1.setIdNumber(clientManagement.getIdNumber());
                clientManagement1.setKraPin(clientManagement.getKraPin());
                clientManagement1.setEmailAddr(clientManagement.getEmailAddr());
                clientManagement1.setPhoneNo1(clientManagement.getPhoneNo1());
                clientManagement1.setPostalAddress(clientManagement.getPostalAddress());
                clientManagement1.setPostalCode(clientManagement.getPostalCode());
                clientManagement1.setCity(clientManagement.getCity());
                clientManagement1.setCountry(clientManagement.getCountry());
                //clientManagement1.setClientCode(clientManagement.getClientCode());
                clientManagement1.setLoanAccNo(clientManagement.getLoanAccNo());
                clientManagement1.setOriginalLoanAmount(clientManagement.getOriginalLoanAmount());
                clientManagement1.setInterestRate(clientManagement.getInterestRate());
                clientManagement1.setStartDate(clientManagement.getStartDate());
                clientManagement1.setEndDate(clientManagement.getEndDate());
                clientManagement1.setOutPrincipal(clientManagement.getOutPrincipal());
                clientManagement1.setOutInterest(clientManagement.getOutInterest());
                clientManagement1.setLastAccrualDate(clientManagement.getLastAccrualDate());
                clientManagement1.setLastPaymentDate(clientManagement.getLastPaymentDate());
                clientManagement1.setLastPayReceived(clientManagement.getLastPayReceived());
                clientManagement1.setLastIntAppDate(clientManagement.getLastIntAppDate());
                clientManagement1.setUserAssetClass(clientManagement.getUserAssetClass());
                clientManagement1.setClassificationDate(clientManagement.getClassificationDate());
                clientManagement1.setLoanTenor(clientManagement.getLoanTenor());
                clientManagement1.setDefaultReason(clientManagement.getDefaultReason());
                clientManagement1.setDefaultDate(clientManagement.getDefaultDate());
                clientManagement1.setTransferringOffice(clientManagement.getTransferringOffice());
                clientManagement1.setAmountRemaining(clientManagement.getAmountRemaining());
                clientManagement1.setLoanDescription(clientManagement.getLoanDescription());
                clientManagement1.setDepartment(clientManagement.getDepartment());
                clientManagement1.setOfficer(clientManagement.getOfficer());
                clientManagement1.setDeadline(clientManagement.getDeadline());
                clientManagement1.setPriority(clientManagement.getPriority());
                clientManagement1.setAdditionalInfo(clientManagement.getAdditionalInfo());
                clientManagement1.setTotalClients(clientManagement.getTotalClients());
                clientManagement1.setActiveClients(clientManagement.getActiveClients());
                clientManagement1.setPendingClients(clientManagement.getPendingClients());
                clientManagement1.setLitigationClients(clientManagement.getLitigationClients());
                //clientManagement1.setStatus(clientManagement.getStatus());
                clientManagement1.setDetails(clientManagement.getDetails());
                clientManagement1.setLastUpdated(clientManagement.getLastUpdated());
                clientManagement1.setLoanAmount(clientManagement.getLoanAmount());
                clientManagement1.setPhoneNo2(clientManagement.getPhoneNo2());

                // Save the updated entity
                clientManagementRepository.save(clientManagement1);
                entityResponse.setMessage("Client updated successfully");
                entityResponse.setStatusCode(HttpStatus.OK.value());
                entityResponse.setEntity(clientManagement1); // Return the updated entity

            } else {
                entityResponse.setMessage("Client id not found");
                entityResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
                entityResponse.setEntity(null);
            }
        } catch (Exception e) {
            entityResponse.setMessage("An error has occurred while trying to update a client: " + e.getMessage());
            entityResponse.setEntity("");
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return entityResponse;
    }

    public EntityResponse read(Long id) {
        EntityResponse entityResponse = new EntityResponse<>();
        Optional<ClientManagement> clientManagementOptional = clientManagementRepository.findById(id);
        try {
            if (clientManagementOptional.isPresent()) {
                entityResponse.setMessage("case retrieved successfully" + id);
                entityResponse.setStatusCode(HttpStatus.FOUND.value());
                entityResponse.setEntity(clientManagementOptional);

            } else {
                entityResponse.setMessage("case NOT found!");
                entityResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
                entityResponse.setEntity(null);
            }
        } catch (Exception e) {
            log.error("Error {}", e);
        }
        return entityResponse;

    }
    public EntityResponse delete(Long id){
        EntityResponse entityResponse =new EntityResponse<>();
        Optional<ClientManagement> caseManagementOptional = clientManagementRepository.findById(id);
        try{
            if (caseManagementOptional.isPresent()){
                clientManagementRepository.deleteById(id);
                entityResponse.setMessage("Client deleted successfully");
                entityResponse.setStatusCode(HttpStatus.OK.value());
                entityResponse.setEntity("");
            } else {
                entityResponse.setMessage("Client not found");
                entityResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
                entityResponse.setEntity(null);
            }
        } catch (Exception e){
            log.error("Error occurred", e);

        }
        return entityResponse;
    }
    public List<ClientManagement> findAllClients() {
        return clientManagementRepository.findAll();
    }
}
