package com.LDLS.Litigation.Project.Reports;
import com.LDLS.Litigation.Project.UserRegistration.UserRegistrationRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.repo.InputStreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
public class ReportController {
    @Autowired
    ReposrtsRepository reposrtsRepository;
    @Autowired
    UserRegistrationRepository userRegistrationRepository;

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
}

