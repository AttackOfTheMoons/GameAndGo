package com.uncg.gameandgo;

import com.uncg.gameandgo.schema.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>
{

}
