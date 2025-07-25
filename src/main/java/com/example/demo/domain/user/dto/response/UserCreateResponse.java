package com.example.demo.domain.user.dto.response;

import com.example.demo.domain.user.entity.UserEntity;

public record UserCreateResponse(
	Long userId
) {
	public static UserCreateResponse from(UserEntity user) {
		return new UserCreateResponse(user.getId());
	}
}
