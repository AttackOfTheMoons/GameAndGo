package com.uncg.gameandgo;

import java.util.Arrays;

import crud.UserService;
import mongodb.Config;
import mongodb.Database;
import org.bson.json.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static mongodb.Config.URI_STRING;

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

		SpringApplication.run(GameAndGoService.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@ResponseBody
	@RequestMapping("/user")
	public boolean create(@RequestBody String body) throws ParseException
	{
		boolean response;
		try {
			JSONObject data = (JSONObject) new JSONParser().parse(body);
			UserService userService = new UserService(new Database(URI_STRING, "users"));
			response = userService.create((String) data.get("username"), (String) data.get("password"));
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return response;
	}
}