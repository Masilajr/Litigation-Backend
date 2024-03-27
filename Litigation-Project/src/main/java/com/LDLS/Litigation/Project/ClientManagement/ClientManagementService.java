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
    public long countActiveCases() {
        return clientMangementRepository.countByStatus("active");
    }
    public long countPendingCases() {
        return clientMangementRepository.countByStatus("pending");
    }
    public long countClosedCases() {
        return clientMangementRepository.countByStatus("closed");
    }

    public List<ClientManagement> getActiveCases() {
        return clientMangementRepository.findByStatus("active");
    }

    public List<ClientManagement> getPendingCases() {
        return clientMangementRepository.findByStatus("pending");
    }

    public List<ClientManagement> getClosedCases() {
        return clientMangementRepository.findByStatus("closed");
    }

    public EntityResponse add(ClientManagement clientManagement) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            String dayMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMM"));
            String randomDigits = String.format("%04d", new Random().nextInt(10000));
            String clientCode = "CAD" + "/" + dayMonth + "/" + randomDigits;
            clientManagement.setClientCode(clientCode);
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
