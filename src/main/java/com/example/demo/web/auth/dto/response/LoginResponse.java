package com.example.demo.web.auth.dto.response;

import com.example.demo.domain.auth.dto.Token;

public record LoginResponse(
	String accessToken,
	String refreshToken
) {
	public static LoginResponse from(Token token) {
		return new LoginResponse(
			token.accessToken(),
			token.refreshToken()
		);
	}
}