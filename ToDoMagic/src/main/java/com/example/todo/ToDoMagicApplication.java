package com.example.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ToDoMagicApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoMagicApplication.class, args);
	}

}
