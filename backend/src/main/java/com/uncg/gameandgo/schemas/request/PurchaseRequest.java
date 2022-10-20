package com.uncg.gameandgo.schemas.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class PurchaseRequest
{
	@Getter
	@Setter
	@NotBlank
	private String appid;
}
