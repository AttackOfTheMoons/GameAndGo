package com.uncg.gameandgo.repository;

import com.uncg.gameandgo.schemas.database.Role;
import com.uncg.gameandgo.schemas.database.RoleTypes;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String>
{
	Optional<Role> findByName(RoleTypes name);
}
