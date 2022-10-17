package com.uncg.gameandgo.schemas.database;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "games")
public class Game
{
	@Getter
	@Setter
	@Id
	private String id;

	@Getter
	@Setter
	@NonNull
	@NotBlank
	private String appid;

	@Getter
	@Setter
	@NotBlank
	@NonNull
	private Float price;

}
