package com.uncg.gameandgo.schemas.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class DeleteUserRequest
{
	@Getter
	@Setter
	@NotBlank
	private String id;
}
