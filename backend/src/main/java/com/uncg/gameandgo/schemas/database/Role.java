package com.uncg.gameandgo.schemas.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "roles")
public class Role
{
	@Id
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	@NonNull
	private RoleTypes name;
}

