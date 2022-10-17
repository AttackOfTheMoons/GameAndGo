package com.uncg.gameandgo.controllers;

import com.uncg.gameandgo.repository.RoleRepository;
import com.uncg.gameandgo.repository.UserRepository;
import com.uncg.gameandgo.schemas.database.Role;
import com.uncg.gameandgo.schemas.database.RoleTypes;
import com.uncg.gameandgo.schemas.database.User;
import com.uncg.gameandgo.schemas.request.LoginRequest;
import com.uncg.gameandgo.schemas.request.NewUserRequest;
import com.uncg.gameandgo.schemas.response.MessageResponse;
import com.uncg.gameandgo.schemas.response.UserInfoResponse;
import com.uncg.gameandgo.security.jwt.JwtUtils;
import com.uncg.gameandgo.security.services.UserAuth;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController
{
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
	{

		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserAuth userDetails = (UserAuth) authentication.getPrincipal();

		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		List<String> roles = userDetails.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
			.body(new UserInfoResponse(userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody NewUserRequest signUpRequest)
	{
		if (userRepository.existsByUsername(signUpRequest.getUsername()))
		{
			return ResponseEntity
				.badRequest()
				.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail()))
		{
			return ResponseEntity
				.badRequest()
				.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
			signUpRequest.getEmail(),
			encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null)
		{
			Role userRole = roleRepository.findByName(RoleTypes.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		}
		else
		{
			strRoles.forEach(role -> {
				if ("admin".equals(role))
				{
					Role adminRole = roleRepository.findByName(RoleTypes.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
				}
				else
				{
					Role userRole = roleRepository.findByName(RoleTypes.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser()
	{
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(new MessageResponse("You've been signed out!"));
	}
}
