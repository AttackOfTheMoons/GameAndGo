package com.uncg.gameandgo.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uncg.gameandgo.schemas.database.User;
import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
public class UserAuth implements UserDetails
{
	@Serial
	private static final long serialVersionUID = 1L;

	@Getter
	private final String id;

	@Getter(onMethod = @__(@Override))
	private final String username;

	@Getter
	private final String email;

	@Getter(onMethod = @__(@Override))
	@JsonIgnore
	private final String password;

	@Getter(onMethod = @__(@Override))
	private final Collection<? extends GrantedAuthority> authorities;

	public static UserAuth build(User user)
	{
		List<GrantedAuthority> authorities = user.getRoles().stream()
			.map(role -> new SimpleGrantedAuthority(role.getName().name()))
			.collect(Collectors.toList());

		return new UserAuth(
			user.getId(),
			user.getUsername(),
			user.getEmail(),
			user.getPassword(),
			authorities);
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		UserAuth user = (UserAuth) o;
		return Objects.equals(id, user.id);
	}
}
