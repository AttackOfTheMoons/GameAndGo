
package com.uncg.gameandgo.schema;

	import lombok.Getter;
	import lombok.Setter;
	import org.springframework.data.annotation.Id;

public class User
{
	@Id
	private String id;
	@Getter
	private String username;
	private String hashedPwd;
	private String passwordSalt;
	private boolean isAdmin;

	public User() {

	}


	public User(String username, String hashedPwd, String passwordSalt) {
		this.username = username;
		this.hashedPwd = hashedPwd;
		this.passwordSalt = passwordSalt;
		this.isAdmin = false;
	}

	@Override
	public String toString(){
		return "User(username = " + this.username + ", hashedPwd = " + this.hashedPwd + ", passwordSalt = " + passwordSalt + ", isAdmin = " + isAdmin + ")";
	}
}
