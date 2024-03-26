package com.LDLS.Litigation.Project.BillingModule.Services;

import com.LDLS.Litigation.Project.BillingModule.Controllers.CustomExeption;
import com.LDLS.Litigation.Project.BillingModule.Controllers.CustomExeption.ResourceNotFoundException;
import com.LDLS.Litigation.Project.BillingModule.Entities.FeeStructure;
import com.LDLS.Litigation.Project.BillingModule.Repositories.FeeStructureRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FeeStructureService {

    private final FeeStructureRepository feeStructureRepository;

    public FeeStructureService(FeeStructureRepository feeStructureRepository) {
        this.feeStructureRepository = feeStructureRepository;
    }

    // ... CRUD methods for fee structures (similar to ExpenseTrackingService)

    public BigDecimal calculateTotalRate(Long feeStructureId, Integer hours) throws Throwable {
        FeeStructure feeStructure = (FeeStructure) feeStructureRepository.findById(feeStructureId)
                .orElseThrow(() -> new ResourceNotFoundException("fee structure not found"));

        BigDecimal totalBill;
        if (feeStructure.getHourlyRate() != null) {
            totalBill = feeStructure.getHourlyRate().multiply(BigDecimal.valueOf(hours));
        } else {
            totalBill = feeStructure.getFlatFee();
        }
        return totalBill;
    }

    public BigDecimal calculateTotalBill(Long id, Integer hours) {
        BigDecimal hourlyRate = new BigDecimal(10);
        BigDecimal totalBill = hourlyRate.multiply(new BigDecimal(hours));
        return totalBill;
    }
}