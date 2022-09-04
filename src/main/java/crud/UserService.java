package crud;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import mongodb.Database;

/**
 * class User
 * String username;
 * String passwordHash;
 * boolean isAdmin;
 */

public class UserService implements Service
{
	private MongoCollection<schema.User> users;
	private Database database;

	public UserService(Database database)
	{
		this.database = database;
		this.users = this.database.getCollection("users", schema.User.class);
	}

	public boolean create(String username, String password)
	{
		schema.User user = new schema.User(username, password + "123");
		return create(user);
	}

	boolean create(schema.User user) {
		try
		{
			this.users.insertOne(user);
			close();
		} catch (Exception e)
		{
			System.out.println("error in UserService.java");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void close()
	{
		this.database.close();
	}
}
