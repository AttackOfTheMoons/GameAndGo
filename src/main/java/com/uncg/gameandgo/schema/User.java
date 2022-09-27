package com.uncg.gameandgo.schema;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

public class User
{
	@Id
	@Getter
	@Setter
	private String username;
	@Getter
	@Setter
	private String passwordHash;
	@Getter
	private boolean isAdmin;

	public User(String username, String passwordHash) {
		this(username, passwordHash, false);
	}
	public User(String username, String passwordHash, boolean isAdmin) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString(){
		return "User(Username = " + this.username + ", Password Hash = " + this.passwordHash + ")";
	}
}
