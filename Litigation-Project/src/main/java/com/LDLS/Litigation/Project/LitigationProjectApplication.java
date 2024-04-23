package com.LDLS.Litigation.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.apache.logging.log4j.util.Timer.Status.Started;

@SpringBootApplication
@EnableJpaRepositories
=======
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
>>>>>>> main
public class LitigationProjectApplication {

	public static void main(String[] args) {

		SpringApplication.run(LitigationProjectApplication.class, args);
<<<<<<< HEAD
		System.out.println(Started);
=======
        System.out.println("Attention: This is a Monolithic Application!");
>>>>>>> main
	}

}
