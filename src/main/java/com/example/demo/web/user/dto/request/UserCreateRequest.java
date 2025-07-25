package com.example.demo.web.user.dto.request;

import com.example.demo.common.annotation.validator.Password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(
	@Email(message = "이메일 형식으로 요청해주세요.")
	@NotBlank(message = "이메일은 필수입니다.")
	String email,
	@Password(message = "비밀번호는 8자 이상 20자 이하, 영문, 숫자, 특수문자를 포함해야 합니다.")
	String password
) {
}