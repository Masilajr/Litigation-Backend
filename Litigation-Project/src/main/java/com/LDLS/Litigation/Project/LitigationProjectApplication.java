package com.LDLS.Litigation.Project;

import ch.qos.logback.core.net.SyslogOutputStream;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.load;

@SpringBootApplication
@Configuration
public class LitigationProjectApplication {

	public static void main(String[] args) throws JRException {
		SpringApplication.run(LitigationProjectApplication.class, args);
		System.out.println("Attention: This is a Monolithic Application!");
		//System.out.println("Report Generated Successfully");
	}
}
