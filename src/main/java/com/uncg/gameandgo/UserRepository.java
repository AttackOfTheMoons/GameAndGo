package com.uncg.gameandgo;

import com.uncg.gameandgo.schema.NewUserRequest;
import com.uncg.gameandgo.schema.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

public interface UserRepository extends MongoRepository<User, String>
{
	User findByUsername(String username);

	default User create(NewUserRequest request) {
		if (findByUsername(request.getUsername()) != null) {
			return null;
		}
		String salt = BCrypt.gensalt();
		String hashedPwd = BCrypt.hashpw(request.getPassword(), salt);
		User newUser = new User(request.getUsername(), hashedPwd, salt);
		return save(newUser);
	}
}
