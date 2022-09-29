package com.uncg.gameandgo.schema;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

public class NewUserRequest
{
	@Setter
	@Getter
	private String username;
	@Getter
	@Setter
	private String password;


	public NewUserRequest() {

	}


	@Override
	public String toString(){
		return "NewUserRequest(username = " + this.username + ", password = " + this.password + ")";
	}
}