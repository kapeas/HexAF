package com.gftinditex.process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.gftinditex.process"})
//@EnableJpaRepositories("com.gftinditex.process.repositories")

/*
@SpringBootApplication(scanBasePackages={
"com.example.something", "com.example.application"})

@SpringBootApplication
@ComponentScan({"com.delivery.request"})

@EnableJpaRepositories("com.delivery.repository")
* */
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
