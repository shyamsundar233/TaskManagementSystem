package com.taskswift.main;

import com.taskswift.main.service.TransactionalService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin("http://localhost:3000/")
public class TaskswiftApplication {

	@Autowired
	private TransactionalService transactionalService;

	public static void main(String[] args) {
		SpringApplication.run(TaskswiftApplication.class, args);
	}

	@PostConstruct
	public void init() {
		transactionalService.generateAndInsertRanges();
		transactionalService.populateRoles();
	}

}
