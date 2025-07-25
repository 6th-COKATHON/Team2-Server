package com.example.demo.domain.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.error.ErrorCode;
import com.example.demo.common.error.exception.AppException;
import com.example.demo.domain.user.dto.response.UserCreateResponse;
import com.example.demo.domain.user.entity.UserEntity;
import com.example.demo.domain.user.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserEntityRepository userEntityRepository;

	private final BCryptPasswordEncoder passwordEncoder;
	// private final AuthService authService;

	// 회원가입
	@Transactional
	public UserCreateResponse createUser(
		final String email,
		final String password
	) {
		checkDuplicateEmail(email);
		final String encodedPassword = passwordEncoder.encode(password);

		final UserEntity user = UserEntity.builder()
			.email(email)
			.password(encodedPassword)
			.build();
		userEntityRepository.save(user);

		return UserCreateResponse.from(user);
	}

	// 이메일 중복 검사
	private void checkDuplicateEmail(final String email) {
		if (userEntityRepository.existsByEmail(email)) {
			throw new AppException(ErrorCode.USER_EMAIL_DUPLICATED_EXCEPTION);
		}
	}

	// 회원 정보 조회
	// public UserInfoResponse getUserInfo(UserEntity user) {
	// 	return UserInfoResponse.from(user);
	// }
}