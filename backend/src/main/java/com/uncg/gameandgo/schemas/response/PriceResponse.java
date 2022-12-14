package com.uncg.gameandgo.schemas.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class PriceResponse
{
	@Getter
	private String appid;

	@Getter
	private Float price;

	@Getter
	private boolean isAdmin;
}
