package com.apiecommerce.apiecomerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin("*")
public class ApiecomerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiecomerceApplication.class, args);

		
	}

}
