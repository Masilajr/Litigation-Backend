package com.LDLS.Litigation.Project.ClientManagement;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import com.LDLS.Litigation.Project.ClientManagement.dtos.ClientManagementDTO;
import com.LDLS.Litigation.Project.ClientManagement.messages.ClientManagementMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;
@Service
@Slf4j
@Configuration
public class ClientManagementService {
    private final ClientManagementMessageProducer clientManagementMessageProducer;
    @Autowired
    private ClientManagementRepository clientManagementRepository;

    public ClientManagementService(ClientManagementMessageProducer clientManagementMessageProducer) {
        this.clientManagementMessageProducer = clientManagementMessageProducer;
    }

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

            ClientManagementDTO clientManagementDTO = convertToDTO(client);

            clientManagementMessageProducer.sendMessage(clientManagementDTO);

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

    public EntityResponse update(ClientManagement clientManagement) {
        EntityResponse entityResponse = new EntityResponse<>();
        Optional<ClientManagement> existingCase = clientManagementRepository.findById(clientManagement.getId());
        try {
            if (existingCase.isPresent()) {
                ClientManagement clientManagement1 = existingCase.get();
                clientManagement1.setLoanDescription(clientManagement.getLoanDescription());
                clientManagement1.setLoanAmount(clientManagement.getLoanAmount());
                clientManagement1.setDefaultDate(clientManagement.getDefaultDate());
                clientManagement1.setAmountRemaining(clientManagement.getAmountRemaining());
                clientManagementRepository.save(clientManagement1);
                entityResponse.setMessage("Client updated successfully");
                entityResponse.setStatusCode(HttpStatus.OK.value());
                entityResponse.setEntity(clientManagement);

            } else {
                entityResponse.setMessage("client id not found");
                entityResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
                entityResponse.setEntity(null);
            }
        } catch (Exception e) {
            entityResponse.setMessage("An error has occurred while trying to update a client {}" + e);
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
