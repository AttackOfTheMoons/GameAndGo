package com.uncg.gameandgo.schemas.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class UserInfoResponse
{
	@Getter
	@Setter
	@NonNull
	private String id;
	@Getter
	@Setter
	@NonNull
	private String username;
	@Getter
	@Setter
	@NonNull
	private String email;
	@Getter
	@NonNull
	private List<String> roles;
}

