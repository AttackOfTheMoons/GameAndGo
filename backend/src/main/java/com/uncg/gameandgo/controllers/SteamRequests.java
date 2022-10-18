package com.uncg.gameandgo.controllers;

import com.uncg.gameandgo.security.jwt.JwtUtils;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public class SteamRequests
{
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	public static ResponseEntity<?> getRequest(String baseUri) throws IOException, InterruptedException
	{
		return getRequest(baseUri, Map.of());
	}

	public static ResponseEntity<?> getRequest(String baseUri, Map<String, Object> params) throws IOException, InterruptedException
	{
		HttpClient client = HttpClient.newHttpClient();
		StringBuilder stringBuilder = new StringBuilder(baseUri);
		if (!params.isEmpty())
		{
			stringBuilder.append('?');
			params.forEach((key, value) -> {
				stringBuilder.append(key);
				stringBuilder.append('=');
				stringBuilder.append(value);
				stringBuilder.append('&');
			});
		}
		HttpRequest request = HttpRequest.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.uri(URI.create(stringBuilder.toString()))
			.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() == 200)
		{
			return ResponseEntity.ok(response.body());
		}
		else
		{
			logger.error(String.format("Error in GET request to %s with params %s", baseUri, params));
			return ResponseEntity.internalServerError()
				.body("Could not reach steam api");
		}
	}
}
