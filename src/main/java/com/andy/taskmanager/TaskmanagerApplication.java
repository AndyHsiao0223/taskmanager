package com.andy.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

// @SpringBootApplication is equivalent to the three below
// @Configuration
// @EnableAutoConfiguration
// @ComponentScan -- like controller component ex: @RestController
@SpringBootApplication

// Auto-scans and registers @ConfigurationProperties classes as beans.
@ConfigurationPropertiesScan
public class TaskmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
	}

}
