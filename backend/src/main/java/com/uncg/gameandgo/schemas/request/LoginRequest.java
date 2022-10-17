package com.uncg.gameandgo.schemas.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class LoginRequest
{
	@Getter
	@Setter
	@NotBlank
	private String username;

	@Getter
	@Setter
	@NotBlank
	private String password;
}

