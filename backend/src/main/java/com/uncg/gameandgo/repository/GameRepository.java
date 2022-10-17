package com.uncg.gameandgo.repository;

import com.uncg.gameandgo.schemas.database.Game;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String>
{
	Optional<Game> findByAppid(String appId);
}
