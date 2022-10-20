package com.uncg.gameandgo.controllers;

import com.uncg.gameandgo.repository.GameRepository;
import com.uncg.gameandgo.repository.UserRepository;
import com.uncg.gameandgo.schemas.database.Game;
import com.uncg.gameandgo.schemas.request.DeleteUserRequest;
import com.uncg.gameandgo.schemas.request.PriceUpdateRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController
{
	@Autowired
	UserRepository userRepository;

	@Autowired
	GameRepository gameRepository;

	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> adminAccess()
	{
		return ResponseEntity.ok(userRepository.findAll());
	}

	@DeleteMapping("/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteUserRequest deleteUserRequest)
	{
		userRepository.deleteById(deleteUserRequest.getId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/game/{appid}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updatePrice(@PathVariable String appid, @Valid @RequestBody PriceUpdateRequest priceUpdateRequest)
	{
		Game game = gameRepository.findByAppid(appid).orElse(new Game(appid, priceUpdateRequest.getNewPrice()));
		game.setPrice(priceUpdateRequest.getNewPrice());
		gameRepository.save(game);
		return ResponseEntity.ok(game);
	}
}
