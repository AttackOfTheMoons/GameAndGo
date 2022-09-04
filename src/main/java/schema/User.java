package schema;

import lombok.Getter;

public class User
{
	@Getter
	String username;
	@Getter
	String passwordHash;
	@Getter
	boolean isAdmin;

	public User() {
		this(null,null, false);
	}


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
