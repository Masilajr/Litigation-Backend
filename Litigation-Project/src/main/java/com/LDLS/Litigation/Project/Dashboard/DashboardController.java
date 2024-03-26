package com.LDLS.Litigation.Project.Dashboard;
import com.LDLS.Litigation.Project.ClientManagement.ClientManagement;
import com.LDLS.Litigation.Project.ClientManagement.ClientManagementService;
import com.LDLS.Litigation.Project.Events.EventManagement;
import com.LDLS.Litigation.Project.Events.EventManagementService;
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
    @Autowired
    EventManagementService eventManagementService;

    @GetMapping("/cases")
    public ResponseEntity<ClientManagement> getCasesCounts() {
        long activeCases = clientManagementService.countActiveCases();
        long pendingCases = clientManagementService.countPendingCases();
        long closedCases = clientManagementService.countClosedCases();

        ClientManagement cases = new ClientManagement(activeCases, pendingCases, closedCases);
        return new ResponseEntity<>(cases, HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventManagement>> getUpcomingEvents() {
        List<EventManagement> events = eventManagementService.getUpcomingEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}