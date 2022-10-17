package com.uncg.gameandgo.schemas.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class PriceResponse
{
	@Getter
	@Setter
	private String appid;

	@Getter
	@Setter
	private Float price;

	@Getter
	@Setter
	private boolean isAdmin;
}
