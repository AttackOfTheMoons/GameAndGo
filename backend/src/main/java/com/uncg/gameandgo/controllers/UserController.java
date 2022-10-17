package com.uncg.gameandgo.controllers;

import com.uncg.gameandgo.repository.PurchaseRepository;
import com.uncg.gameandgo.schemas.database.Purchase;
import com.uncg.gameandgo.schemas.request.PurchaseRequest;
import com.uncg.gameandgo.schemas.response.MessageResponse;
import com.uncg.gameandgo.security.services.UserAuth;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/api/user")
public class UserController
{
	@Autowired
	PurchaseRepository purchaseRepository;

	@PostMapping("/buy")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> purchaseGame(@Valid @RequestBody PurchaseRequest purchaseRequest)
	{
		UserAuth user = (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (purchaseRepository.existsByUseridAndAppid(user.getId(), purchaseRequest.getAppid()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse(String.format("User already owns appid: %s", purchaseRequest.getAppid())));
		}
		purchaseRepository.save(new Purchase(user.getId(), purchaseRequest.getAppid()));
		return ResponseEntity.ok("Purchase successful!");
	}

	@GetMapping("/game/{appid}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Boolean checkForOwnership(@PathVariable String appid)
	{
		UserAuth user = (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return purchaseRepository.existsByUseridAndAppid(user.getId(), appid);
	}

	@DeleteMapping(("/game/{appid}"))
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> deletePurchase(@PathVariable String appid)
	{
		UserAuth user = (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!purchaseRepository.existsByUseridAndAppid(user.getId(), appid))
		{
			return ResponseEntity.badRequest().body(new MessageResponse(String.format("User doesn't owns appid: %s", appid)));
		}
		purchaseRepository.deleteByUseridAndAppid(user.getId(), appid);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("/games")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> ownedGames()
	{
		UserAuth user = (UserAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(purchaseRepository.findByUserid(user.getId()).stream().map(Purchase::getAppid));
	}

}
