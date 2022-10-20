package com.uncg.gameandgo.controllers;

import com.uncg.gameandgo.repository.GameRepository;
import com.uncg.gameandgo.schemas.database.Game;
import com.uncg.gameandgo.schemas.database.RoleTypes;
import com.uncg.gameandgo.schemas.response.PriceResponse;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/game")
public class GameController
{
	@Autowired
	GameRepository gameRepository;

	@GetMapping("/{appid}")
	public ResponseEntity<?> gameInfo(@PathVariable String appid) throws IOException, InterruptedException
	{
		return SteamRequests.getRequest("https://store.steampowered.com/api/appdetails", Map.of("appids", appid));
	}

	@GetMapping("/price/{appid}")
	public ResponseEntity<?> getPrice(@PathVariable String appid)
	{
		boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(e -> e.getAuthority().equals(RoleTypes.ROLE_ADMIN.name()));
		Float price = gameRepository.findByAppid(appid).map(Game::getPrice).orElse(null);
		PriceResponse priceResponse = new PriceResponse(appid, price, isAdmin);
		return ResponseEntity.ok(priceResponse);
	}
}
