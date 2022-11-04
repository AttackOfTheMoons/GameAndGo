package com.uncg.gameandgo.schemas.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class GameQueryResponse
{
	@Getter
	private String appID;
	@Getter
	private String matchName;
	@Getter
	private String imageURL;
}
