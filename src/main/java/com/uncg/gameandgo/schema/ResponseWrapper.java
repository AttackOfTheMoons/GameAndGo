package com.uncg.gameandgo.schema;

import lombok.Getter;
import lombok.Setter;

public class ResponseWrapper
{
	@Getter
	@Setter
	private String message;

	ResponseWrapper() {

	}

	public ResponseWrapper(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ResponseWrapper(message = \"" + message + "\")";
	}

}
