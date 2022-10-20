package com.uncg.gameandgo.schemas.request;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class NewUserRequest
{
	@Getter
	@Setter
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	@Getter
	@Setter
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@Getter
	@Setter
	private Set<String> roles;

	@Getter
	@Setter
	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
}
