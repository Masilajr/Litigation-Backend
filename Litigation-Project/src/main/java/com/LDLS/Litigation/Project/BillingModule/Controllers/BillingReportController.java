package com.LDLS.Litigation.Project.BillingModule.Controllers;

import com.LDLS.Litigation.Project.BillingModule.Repositories.ExpenseTrackingRepository;
import com.LDLS.Litigation.Project.BillingModule.Repositories.InvoiceReportsRepository;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


@RestController
@RequestMapping("/api/v1/reports")
public class BillingReportController {

    @Autowired
    InvoiceReportsRepository invoiceReportsRepository;

    @Autowired
    ExpenseTrackingRepository expenseTrackingRepository;

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


    @GetMapping("/Invoices Report")
    public ResponseEntity<ByteArrayResource> InvoicesReports() throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        ClassLoader classLoader = getClass().getClassLoader();
        JasperReport compileReport = JasperCompileManager.compileReport(classLoader.getResourceAsStream("Templates/BillingInvoiceReport.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Invoices_Report.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }

    @GetMapping("/Expenses Report")
    public ResponseEntity<ByteArrayResource> ExpenseTrackingReport() throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        ClassLoader classLoader = getClass().getClassLoader();
        JasperReport compileReport = JasperCompileManager.compileReport(classLoader.getResourceAsStream("Templates/BillingExpense.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Expenses_Report.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }
//    @GetMapping("/recovered-amount")
//    public ResponseEntity<ByteArrayResource> RecoveredAmountReports() throws FileNotFoundException, JRException, SQLException {
//        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
//        ClassLoader classLoader = getClass().getClassLoader();
//        JasperReport compileReport = JasperCompileManager.compileReport(classLoader.getResourceAsStream("Templates/BillingInvoiceReport.jrxml"));
//        Map<String, Object> parameter = new HashMap<>();
//        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
//        byte[] data = JasperExportManager.exportReportToPdf(report);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=RecoveredAmount_Report.pdf");
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
//        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
//    }
//
//    @GetMapping("/rejected-invoices")
//    public ResponseEntity<ByteArrayResource> RejecetdInvoiceReports() throws FileNotFoundException, JRException, SQLException {
//        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
//        ClassLoader classLoader = getClass().getClassLoader();
//        JasperReport compileReport = JasperCompileManager.compileReport(classLoader.getResourceAsStream("Templates/BillingInvoiceReport.jrxml"));
//        Map<String, Object> parameter = new HashMap<>();
//        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
//        byte[] data = JasperExportManager.exportReportToPdf(report);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=RejectedInvoices_Report.pdf");
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
//        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
//    }
//
//    @GetMapping("/approved-invoices")
//    public ResponseEntity<ByteArrayResource> ApprovedInvoicesReports() throws FileNotFoundException, JRException, SQLException {
//        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
//        ClassLoader classLoader = getClass().getClassLoader();
//        JasperReport compileReport = JasperCompileManager.compileReport(classLoader.getResourceAsStream("Templates/BillingInvoiceReport.jrxml"));
//        Map<String, Object> parameter = new HashMap<>();
//        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
//        byte[] data = JasperExportManager.exportReportToPdf(report);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ApprovedInvoices_Report.pdf");
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
//        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
//    }
}