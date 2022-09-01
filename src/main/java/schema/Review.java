package schema;

public class Review
{
	User user;
	Rating rating;
	String description;

	Review(User user, Rating rating, String description) {
		this.user = user;
		this.rating = rating;
		this.description = description;
	}

	enum Rating {
		PERFECT(5),
		FUN(4),
		OKAY(3),
		MEH(2),
		BAD(1),
		TERRIBLE(0);

		final int rating;
		Rating(int rating) {
			this.rating = rating;
		}
	}
}
