package com.uncg.gameandgo.security.jwt;

import com.uncg.gameandgo.security.services.UserAuth;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class JwtUtils
{
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	private final String cookieName = "gameandgo";
	@Value("${uncg.gameandgo.secret}")
	private String jwtSecret;

	public String getJwtFromCookies(HttpServletRequest request)
	{
		Cookie cookie = WebUtils.getCookie(request, cookieName);
		if (cookie != null)
		{
			return cookie.getValue();
		}
		else
		{
			return null;
		}
	}

	public ResponseCookie generateJwtCookie(UserAuth userPrincipal)
	{
		String jwt = generateTokenFromUsername(userPrincipal.getUsername());
		return ResponseCookie.from(cookieName, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
	}

	public ResponseCookie getCleanJwtCookie()
	{
		return ResponseCookie.from(cookieName, null).path("/api").build();
	}

	public String getUserNameFromJwtToken(String token)
	{
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken)
	{
		try
		{
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		}
		catch (SignatureException e)
		{
			logger.error("Invalid JWT signature: {}", e.getMessage());
		}
		catch (MalformedJwtException e)
		{
			logger.error("Invalid JWT token: {}", e.getMessage());
		}
		catch (ExpiredJwtException e)
		{
			logger.error("JWT token is expired: {}", e.getMessage());
		}
		catch (UnsupportedJwtException e)
		{
			logger.error("JWT token is unsupported: {}", e.getMessage());
		}
		catch (IllegalArgumentException e)
		{
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	public String generateTokenFromUsername(String username)
	{
		int expirationMs = 86400000;
		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(new Date())
			.setExpiration(new Date((new Date()).getTime() + expirationMs))
			.signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	}
}
