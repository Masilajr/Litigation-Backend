package com.LDLS.Litigation.Project.ClientManagement;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/clientmanagement")
public class ClientManagementController {
    @Autowired
    ClientManagementService clientManagementService;

    @PostMapping("/add")
    public EntityResponse add(@RequestBody ClientManagement clientManagement) {
        return clientManagementService.add(clientManagement);
    }

    @PutMapping("/update/{id}")
    public EntityResponse update(@PathVariable Long id, @RequestBody ClientManagement clientManagement) {
        clientManagement.setId(id);
        return clientManagementService.update(clientManagement);
    }

    @GetMapping("/summary")
    public Map<String, Map<String, Long>> getLoanSummary() {
        List<ClientManagement> clients = clientManagementService.getAllClients();
        return clientManagementService.getLoanSummary(clients);
    }

    @GetMapping("/read")
    public EntityResponse read(@RequestParam Long id) {

        return clientManagementService.read(id);
    }

    @GetMapping("/fetch")
    public List<ClientManagement> fetch() {
        return clientManagementService.findAllClients();
    }

    @DeleteMapping("/delete/{id}")
    public EntityResponse delete(@RequestParam Long id) {
        return clientManagementService.delete(id);
    }

    @PostMapping("/assign")
    public EntityResponse assignCase(@RequestParam Long id, @RequestBody ClientRequest request){
        return clientManagementService.assignOfficerToClient(id, request);
    }

    @GetMapping("/search")
    public List<ClientManagement> search(@RequestParam(required = false) String clientCode, @RequestParam(required = false) Long loanAccNo) {
        return clientManagementService.searchByClientOrLoan(clientCode, loanAccNo);
    }

    @GetMapping("/registration-count-by-month")
    public ResponseEntity<EntityResponse<long[]>> getClientRegistrationCountByCurrentYear() {
        EntityResponse<long[]> response = new EntityResponse<>();
        try {
            int currentYear = YearMonth.now().getYear(); // Get the current year
            long[] registrationCountByMonth = clientManagementService.getClientRegistrationCountByMonth(currentYear);
            response.setMessage("Client registration count by month for the current year");
            response.setEntity(registrationCountByMonth);
            response.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setMessage("Error retrieving client registration count by month: " + e.getMessage());
            response.setEntity(null);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/transfer-to-litigation")
    public EntityResponse<String> transferClientToLitigation(@RequestParam(required = false) String clientCode) {
        EntityResponse<String> response = new EntityResponse<>();
        if (clientCode == null || clientCode.isEmpty()) {
            response.setMessage("The 'clientCode' parameter is required.");
            response.setEntity(null);
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return response;
        }
        try {
            clientManagementService.transferClientToLitigation(clientCode);
            response.setMessage("Client transferred to Litigation successfully.");
            response.setEntity(null);
            response.setStatusCode(HttpStatus.OK.value());
        } catch (RuntimeException e) {
            response.setMessage("Error transferring client to Litigation: " + e.getMessage());
            response.setEntity(null);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }



//        @PreAuthorize("hasRole('ROLE_ADMIN')")
//        @PostMapping("/add")
//        public EntityResponse add(@RequestBody ClientManagement clientManagement) {
//            return clientManagementService.add(clientManagement);
//        }
//
//        @PreAuthorize("hasRole('ROLE_ADMIN')")
//        @PutMapping("/update/{id}")
//        public EntityResponse update(@PathVariable Long id, @RequestBody ClientManagement clientManagement) {
//            clientManagement.setId(id);
//            return clientManagementService.update(clientManagement);
//        }
//
//    }

}
