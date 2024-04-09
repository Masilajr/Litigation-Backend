package com.LDLS.Litigation.Project.Dashboard;
import com.LDLS.Litigation.Project.ClientManagement.ClientManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl{
    @Autowired
    ClientManagementRepository clientManagementRepository;


    public long countActiveCases(){
        return clientManagementRepository.count();
    }

    public long countPendingCases() {
        return clientManagementRepository.count();
    }


    public long countClosedCases() {
        return clientManagementRepository.count();
    }


}

