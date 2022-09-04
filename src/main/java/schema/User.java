package schema;

public class User
{
	String username;
	String passwordHash;
	boolean isAdmin;
	User(String username, String passwordHash, boolean isAdmin) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.isAdmin = isAdmin;
	}
}
