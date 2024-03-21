package com.LDLS.Litigation.Project.ClientManagement;
import com.LDLS.Litigation.Project.Authentication.Responses.EntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("api/v1/clientmanagement")
public class ClientManagementController {
    @Autowired
    ClientManagementService clientManagementService;
    @PostMapping("/add")
    public EntityResponse add(@RequestBody ClientManagement clientManagement){
        return clientManagementService.add(clientManagement);
    }
    @PutMapping("/update")
    public EntityResponse update(@RequestBody ClientManagement clientManagement){
        return clientManagementService.update(clientManagement);
    }

    @GetMapping("/read")
    public EntityResponse read(@RequestParam Long id){

        return clientManagementService.read(id);
    }

    @DeleteMapping("/delete")
    public EntityResponse delete(@RequestParam Long id){
        return clientManagementService.read(id);
    }

    @GetMapping("/active")
    public List<ClientManagement> getActiveCases() {
        return clientManagementService.getActiveCases();
    }

    @GetMapping("/pending")
    public List<ClientManagement> getPendingCases() {
        return clientManagementService.getPendingCases();
    }

    @GetMapping("/closed")
    public List<ClientManagement> getClosedCases() {
        return clientManagementService.getClosedCases();
    }
}