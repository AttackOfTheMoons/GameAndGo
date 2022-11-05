package com.uncg.gameandgo.schemas.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UserInfoResponse
{
	@Getter
	private String id;
	@Getter
	private String username;
	@Getter
	private String email;
	@Getter
	private List<String> roles;
}

