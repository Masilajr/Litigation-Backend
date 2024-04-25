package com.LDLS.Litigation.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Configuration;
import static org.apache.logging.log4j.util.Timer.Status.Started;

@SpringBootApplication
@EnableJpaRepositories
@Configuration
public class LitigationProjectApplication {

	public static void main(String[] args) {

		SpringApplication.run(LitigationProjectApplication.class, args);

		System.out.println(Started);


		System.out.println(Started);

        System.out.println("Attention: This is a Monolithic Application!");


	}

}
