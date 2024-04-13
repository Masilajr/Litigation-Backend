package com.LDLS.Litigation.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class LitigationProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LitigationProjectApplication.class, args);
        System.out.println("Attention: This is a Monolithic Application!");
	}

}
