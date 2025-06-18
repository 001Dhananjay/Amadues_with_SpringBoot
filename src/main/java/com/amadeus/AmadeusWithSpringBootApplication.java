package com.amadeus;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AmadeusWithSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmadeusWithSpringBootApplication.class, args);
	}

}



















/*@OpenAPIDefinition(
		info = @Info(
				title = "Amadeus Flight API",
				description = "Flight search using Amadeus API"
		)
)*/