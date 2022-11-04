package com.uncg.gameandgo.controllers;

import com.uncg.gameandgo.schemas.response.GameQueryResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/all")
public class PublicController
{
	@Value("${uncg.gameandgo.steam}")
	private String steamKey;

	@GetMapping("/")
	public ResponseEntity<?> allAccess(@RequestParam(name = "lastAppID", required = false) Integer lastAppID) throws IOException, InterruptedException
	{
		return SteamRequests.getRequest("https://api.steampowered.com/IStoreService/GetAppList/v1/",
			Map.of("key", steamKey, "max_results", 32, "last_appid", lastAppID == null ? "null" : lastAppID));
	}

	//term=c&f=games&cc=US&realm=1&l=english&use_store_query=1&use_search_spellcheck=1
	@GetMapping("/find")
	public ResponseEntity<?> findByName(@RequestParam(name = "term") String term) throws IOException, InterruptedException
	{
		ResponseEntity<String> query = SteamRequests.getRequest("https://store.steampowered.com/search/suggest",
			Map.of("term", term, "f", "games", "cc", "US", "use_search_spellcheck", 1));
		if (!query.getStatusCode().is2xxSuccessful())
			return query;
		if (query.getBody() == null)
			return ResponseEntity.internalServerError().body("Response from Steam's API was null");
		Document response = Jsoup.parse(query.getBody());
		List<GameQueryResponse> responseArrayList = new ArrayList<>();
		for (Element element : response.getElementsByTag("a")) {
			String imageURL = null;
			String name = null;
			for (Element div : element.getElementsByTag("div")) {
				if (div.hasClass("match_name")) {
					name = div.text();
				}
				else if (div.hasClass("match_img")) {
					imageURL = Objects.requireNonNull(div.getElementsByTag("img").first()).attr("src");
				}
			}
			responseArrayList.add(new GameQueryResponse(element.attr("data-ds-appid"), name, imageURL));
		}
		return ResponseEntity.ok(responseArrayList);
	}
}
