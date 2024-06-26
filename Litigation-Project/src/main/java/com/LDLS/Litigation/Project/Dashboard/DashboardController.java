package com.LDLS.Litigation.Project.Dashboard;
import com.LDLS.Litigation.Project.ClientManagement.ClientManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("api/v1/dashboard")
@CrossOrigin
public class DashboardController {
    @Autowired
    ClientManagementService clientManagementService;

    @GetMapping("/total-clients")
    public long getTotalClientsCount() {
    return clientManagementService.countTotalClients();
}

    @GetMapping("/pending-clients")
    public long getPendingClientsCount() {
        return clientManagementService.countPendingClients();
    }

    @GetMapping("/active-clients")
    public long getActiveClientsCount() {
        return clientManagementService.countActiveClients();
    }

    @GetMapping("/litigation-clients")
    public long getLitigationClientsCount() {
        return clientManagementService.countLitigationClients();
    }

}