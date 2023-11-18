package com.org.core.cruds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.org.core.cruds.model")
public class CrudcsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudcsApplication.class, args);
	}

}
