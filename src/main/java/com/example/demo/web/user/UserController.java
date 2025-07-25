package com.example.demo.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.response.AppResponse;
import com.example.demo.domain.user.dto.response.UserCreateResponse;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.web.user.dto.request.UserCreateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	@Operation(
		summary = "회원가입 API",
		description = "이메일, 비밀번호, 이름을 입력하여 회원가입을 진행합니다.",
		responses = {
			@ApiResponse(responseCode = "400", description = "사용자 입력 오류[C-001]"),
			@ApiResponse(responseCode = "409", description = "이미 사용중인 이메일입니다.[U-001]"),
		}
	)
	public ResponseEntity<AppResponse<UserCreateResponse>> createUser(@RequestBody @Valid UserCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(AppResponse.created(userService.createUser(
				request.email(),
				request.password()
			)));
	}
}
