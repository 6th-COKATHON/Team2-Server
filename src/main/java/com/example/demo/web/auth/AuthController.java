package com.example.demo.web.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.response.AppResponse;
import com.example.demo.domain.auth.service.AuthService;
import com.example.demo.domain.auth.util.CookieUtil;
import com.example.demo.web.auth.dto.response.LoginResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	private static final String AUTH_HEADER = "Authorization";

	private static final String BEARER_TOKEN = "Bearer ";

	@PostMapping("/login")
	@Operation(
		summary = "로그인 API",
		description = "이메일과 비밀번호로 로그인합니다. 성공 시 액세스 토큰과 리프레시 토큰을 반환합니다.",
		responses = {
			@ApiResponse(responseCode = "400", description = "해당 유저를 찾을 수 없습니다.[U-002]"),
			@ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않습니다.[U-003]")
		}
	)
	public ResponseEntity<AppResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request,
		HttpServletResponse response) {
		LoginResponse loginResponse = authService.login(request.email(), request.password());

		response.addHeader(AUTH_HEADER, BEARER_TOKEN + loginResponse.accessToken());
		Cookie refreshTokenCookie = CookieUtil.createRefreshCookie(loginResponse.refreshToken());
		response.addCookie(refreshTokenCookie);

		return ResponseEntity.ok(AppResponse.ok(loginResponse));
	}
}
