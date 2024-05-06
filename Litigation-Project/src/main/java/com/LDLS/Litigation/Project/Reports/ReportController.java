package com.LDLS.Litigation.Project.Reports;
import com.LDLS.Litigation.Project.ClientManagement.ClientManagementRepository;
import com.LDLS.Litigation.Project.UserRegistration.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.repo.InputStreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@RequestMapping("/api/v1/reports")
@Slf4j
public class ReportController {
    @Autowired
    ReposrtsRepository reposrtsRepository;
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    ClientManagementRepository clientManagementRepository;

    @Value("${others.filesPath}")
    private String filesPath;

    @Value("${others.logo}")
    private String logo;

    @Value("${spring.datasource.url}")
    private String db;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;


    @GetMapping("/Users Report")
    public ResponseEntity<ByteArrayResource> UsersReports() throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        ClassLoader classLoader = getClass().getClassLoader();
        JasperReport compileReport = JasperCompileManager.compileReport(classLoader.getResourceAsStream("templates/UserReport.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
//        parameter.put("churchId", id);
//        parameter.put("logo", logo);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Users_Reports.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }

    @GetMapping("/{status}/LitigantsReport")
    public ResponseEntity<ByteArrayResource> LitigantsReports(
            @PathVariable String status,
            @RequestParam("FromDate") String fromDateStr,
            @RequestParam("ToDate") String toDateStr
    ) throws FileNotFoundException, JRException, SQLException {
        LocalDate fromDate = LocalDate.parse(fromDateStr, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate toDate = LocalDate.parse(toDateStr, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        ClassLoader classLoader = getClass().getClassLoader();
        JasperReport compileReport = JasperCompileManager.compileReport(classLoader.getResourceAsStream("templates/Litigants.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("status", status);
        parameter.put("From Date", fromDate);
        parameter.put("To Date", toDate);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Litigants_Reports.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }




//        @GetMapping("/{status}/LitigantsReport")
//    public ResponseEntity<ByteArrayResource> LitigantsReports(
//            @PathVariable String status,
//            @RequestParam("FromDate") @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate FromDate,
//            @RequestParam("ToDate") @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate ToDate
//
//    ) throws FileNotFoundException, JRException, SQLException {
//        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
//        ClassLoader classLoader = getClass().getClassLoader();
//        JasperReport compileReport = JasperCompileManager.compileReport(classLoader.getResourceAsStream("templates/Litigants.jrxml"));
//        Map<String, Object> parameter = new HashMap<>();
//        parameter.put("status", status);
//        parameter.put("From Date", FromDate);
//        parameter.put("To Date", ToDate);
//        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
//        byte[] data = JasperExportManager.exportReportToPdf(report);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Litigants_Reports.pdf");
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
//        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
//    }
//    @GetMapping("/{status}/LitigantsReport")
//    public ResponseEntity<ByteArrayResource> LitigantsReports(
//            @PathVariable String status,
//            @RequestParam LocalDate FromDate,
//            @RequestParam LocalDate ToDate
//    ) {
//        try {
//            // Establish connection
//            Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
//
//            // Load Jasper report template
//            ClassLoader classLoader = getClass().getClassLoader();
//            JasperReport compileReport = JasperCompileManager.compileReport(classLoader.getResourceAsStream("templates/Litigants.jrxml"));
//
//            // Set parameters
//            Map<String, Object> parameter = new HashMap<>();
//            parameter.put("status", status);
//            parameter.put("From Date", FromDate);
//            parameter.put("To Date", ToDate);
//
//            // Fill the report
//            JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
//
//            // Export report to PDF
//            byte[] data = JasperExportManager.exportReportToPdf(report);
//
//
//            // Set response headers
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Litigants_Reports.pdf");
//            headers.setContentType(MediaType.APPLICATION_PDF);
//
//            // Create ByteArrayResource and return response
//            ByteArrayResource byteArrayResource = new ByteArrayResource(data);
//            return ResponseEntity.ok().headers(headers).body(byteArrayResource);
//        } catch (JRException e) {
//            // Log JRException
//            log.error("JasperReports error: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        } catch (SQLException e) {
//            // Log SQLException
//            log.error("SQL error: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        } catch (Exception e) {
//            // Log other exceptions
//            log.error("Error occurred: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

}

