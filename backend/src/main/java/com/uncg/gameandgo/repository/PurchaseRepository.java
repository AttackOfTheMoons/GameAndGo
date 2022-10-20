package com.uncg.gameandgo.repository;

import com.uncg.gameandgo.schemas.database.Purchase;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseRepository extends MongoRepository<Purchase, String>
{
	Boolean existsByUseridAndAppid(String userid, String appid);

	void deleteByUseridAndAppid(String userid, String appid);

	List<Purchase> findByUserid(String userid);

}
