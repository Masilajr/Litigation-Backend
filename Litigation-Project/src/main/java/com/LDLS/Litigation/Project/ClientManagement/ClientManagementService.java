package com.LDLS.Litigation.Project.ClientManagement;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
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

    @Autowired
    private ClientMangementRepository clientMangementRepository;
    public long countActiveClients() {
        return clientMangementRepository.countByStatus("active");
    }

    public long countPendingClients() {
        return clientMangementRepository.countByStatus("pending");
    }

    public long countTotalClients() {
        // Assuming "total" means all clients regardless of status
        return clientMangementRepository.count();
    }

    public long countLitigationClients() {
        return clientMangementRepository.countByStatus("litigation");
    }

    public List<ClientManagement> getActiveClients() {
        return clientMangementRepository.findByStatus("active");
    }

    public List<ClientManagement> getPendingClients() {
        return clientMangementRepository.findByStatus("pending");
    }

    public List<ClientManagement> getLitigationClients() {
        return clientMangementRepository.findByStatus("litigation");
    }

    // Assuming "total" means all clients regardless of status
    public List<ClientManagement> getTotalClients() {
        return clientMangementRepository.findAll();
    }


    public EntityResponse assignOfficerToClient(Long id, ClientRequest request) {
        EntityResponse response = new EntityResponse<>();
        try {
            ClientManagement client = clientMangementRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            client.setOfficer(request.getOfficer());
            client.setAdditionalInfo(request.getAdditionalInfo());
            client.setPriority(request.getPriority());
            client.setDepartment(request.getDepartment());
            client.setDeadline(request.getDeadline());
            client.setStatus("Active");
            clientMangementRepository.save(client);
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
            ClientManagement client = clientMangementRepository.findByClientCode(clientCode)
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            // Update the client's status to indicate they have been transferred to Litigation
            client.setStatus("Litigation");
            // Optionally, log the transfer or perform other actions related to the transfer
            // For example, you might want to update related entities or notify other parts of your application
            clientMangementRepository.save(client);
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
            ClientManagement savedCase = clientMangementRepository.save(clientManagement);
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
        Optional<ClientManagement> existingCase = clientMangementRepository.findById(clientManagement.getId());
        try {
            if (existingCase.isPresent()) {
                ClientManagement clientManagement1 = existingCase.get();
                clientManagement1.setLoanDescription(clientManagement.getLoanDescription());
                clientManagement1.setLoanAmount(clientManagement.getLoanAmount());
                clientManagement1.setDefaultDate(clientManagement.getDefaultDate());
                clientManagement1.setAmountRemaining(clientManagement.getAmountRemaining());
                clientMangementRepository.save(clientManagement1);
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
        Optional<ClientManagement> clientManagementOptional = clientMangementRepository.findById(id);
        try {
            if (clientManagementOptional.isPresent()) {
                //ClientManagement retrievedcaseManagement = caseManagementOptional.get();

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
        Optional<ClientManagement> caseManagementOptional = clientMangementRepository.findById(id);
        try{
            if (caseManagementOptional.isPresent()){
                clientMangementRepository.deleteById(id);
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
        return clientMangementRepository.findAll();
    }
}
