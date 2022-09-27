package mongodb;

import io.github.cdimascio.dotenv.Dotenv;

public class Config
{
	static Dotenv dotenv = Dotenv.load();
	public final static String URI_STRING = dotenv.get("MONGODB_URI");
}
