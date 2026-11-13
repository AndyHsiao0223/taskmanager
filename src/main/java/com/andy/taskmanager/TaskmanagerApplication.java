package com.andy.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
// @SpringBootApplication equal to the three below
// @Configuration
// @EnableAutoConfiguration
// @ComponentScan -- like controller component ex: @RestController
@ConfigurationPropertiesScan
// Auto-scans and registers @ConfigurationProperties classes as beans.
public class TaskmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
	}

}
