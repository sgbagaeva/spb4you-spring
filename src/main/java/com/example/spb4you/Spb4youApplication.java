package com.example.spb4you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class Spb4youApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spb4youApplication.class, args);
	}

}
