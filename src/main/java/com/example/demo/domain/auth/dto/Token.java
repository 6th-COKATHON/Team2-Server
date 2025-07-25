package com.example.demo.domain.auth.dto;

public record Token(
	String accessToken,
	String refreshToken
) {
}
