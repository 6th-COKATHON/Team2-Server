package com.example.demo.common.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// Common
	USER_INPUT_EXCEPTION(HttpStatus.BAD_REQUEST, "C-001", "User input error"),
	USER_ROLE_EXCEPTION(HttpStatus.FORBIDDEN, "C-002", "User permission error"),
	AUTHENTICATION_EXCEPTION(HttpStatus.UNAUTHORIZED, "C-003", "Common authentication error (filter)"),
	JWT_AUTH_EXCEPTION(HttpStatus.UNAUTHORIZED, "C-004", "JWT authentication error"),
	TOKEN_BLACKLISTED_EXCEPTION(HttpStatus.BAD_REQUEST, "C-005", "Token is blacklisted."),
	REFRESH_TOKEN_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "C-006", "No refresh token found for this user."),
	REFRESH_TOKEN_MISMATCH_EXCEPTION(HttpStatus.UNAUTHORIZED, "C-007", "Refresh token does not match."),
	USER_NOT_AUTHENTICATED_EXCEPTION(HttpStatus.UNAUTHORIZED, "C-008", "User is not authenticated."),

	// When principal type is invalid in ContextHolder
	INVALID_PRINCIPAL_TYPE_EXCEPTION(HttpStatus.UNAUTHORIZED, "C-009", "Invalid principal type."),

	// When user type parsed from token is invalid
	INVALID_USER_TYPE_EXCEPTION(HttpStatus.UNAUTHORIZED, "C-010", "Invalid user type."),
	INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "C-011", "Invalid token type."),
	MALFORMED_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "C-012", "Malformed token format."),

	// Server
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S-001", "Internal server error occurred."),
	S3_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S-002", "S3 server error occurred."),

	// User
	USER_EMAIL_DUPLICATED_EXCEPTION(HttpStatus.CONFLICT, "U-001", "이미 사용중인 이메일입니다."),
	USER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "U-002", "해당 유저를 찾을 수 없습니다."),
	USER_PASSWORD_MISMATCH_EXCEPTION(HttpStatus.UNAUTHORIZED, "U-003", "비밀번호가 일치하지 않습니다."),


	NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND,"N-000", "해당 리소스를 찾을 수 없습니다."),
	BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "N-001", "잘못된 요청입니다."),;
	private final HttpStatus status;
	private final String code;
	private final String message;
}
