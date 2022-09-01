package com.uncg.gameandgo;

import java.util.Arrays;
import mongodb.Config;
// import mongodb.Database;
import mongodb.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * to start the webserver at localhost:8080
 * mvnw spring-boot:run
 */

@SpringBootApplication(exclude = {
	MongoAutoConfiguration.class,
	MongoDataAutoConfiguration.class
})
@RestController
public class GameAndGoService
{

	public static void main(String[] args) {
		System.out.println(Arrays.toString(args));
		Database database = new Database(Config.URI_STRING, "test");
		SpringApplication.run(GameAndGoService.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
}