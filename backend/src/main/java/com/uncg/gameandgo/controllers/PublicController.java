package com.uncg.gameandgo.controllers;

import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/all")
public class PublicController
{
	@Value("${uncg.gameandgo.steam}")
	private String steamKey;

	@GetMapping("/")
	public ResponseEntity<?> allAccess(@RequestParam(name = "lastAppID", required = false) Integer lastAppID) throws IOException, InterruptedException
	{
		return SteamRequests.getRequest("https://api.steampowered.com/IStoreService/GetAppList/v1/",
			Map.of("key", steamKey, "max_results", 32, "last_appid", lastAppID == null ? "null" : lastAppID));
	}
}
