package com.uncg.gameandgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 mvnw spring-boot:run to start the webserver at localhost:8080

 */

@SpringBootApplication
@RestController
public class GameAndGoService
{

	public static void main(String[] args) {
		SpringApplication.run(GameAndGoService.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
}