package com.uncg.gameandgo.schemas.database;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "users")
public class User
{
	@Getter
	@Setter
	@Id
	private String id;

	@Getter
	@Setter
	@NonNull
	@NotBlank
	@Size(max = 20)
	private String username;

	@Getter
	@Setter
	@NonNull
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@Getter
	@Setter
	@NonNull
	@NotBlank
	@Size(max = 120)
	private String password;

	@Getter
	@Setter
	@DBRef
	private Set<Role> roles = new HashSet<>();

}
