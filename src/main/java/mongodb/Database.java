package mongodb;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class Database
{
	private final MongoClient client;
	MongoDatabase database;
	Database(String mongodbURI, String databaseName)
	{
		final CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
			fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		final MongoClient client = MongoClients.create(mongodbURI);
		this.client = client;

		MongoDatabase mongoDatabase = client.getDatabase(databaseName);
		mongoDatabase = mongoDatabase.withCodecRegistry(pojoCodecRegistry);
		this.database = mongoDatabase;
	}

	void close() {
		client.close();
	}
}