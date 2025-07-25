package com.example.demo.domain.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.error.ErrorCode;
import com.example.demo.common.error.exception.AppException;
import com.example.demo.common.jwt.RefreshToken;
import com.example.demo.common.jwt.RefreshTokenRepository;
import com.example.demo.domain.auth.dto.Token;
import com.example.demo.domain.jwt.JwtTokenProvider;
import com.example.demo.domain.user.entity.UserEntity;
import com.example.demo.domain.user.repository.UserEntityRepository;
import com.example.demo.web.auth.dto.response.LoginResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final BCryptPasswordEncoder passwordEncoder;

	private final UserEntityRepository userEntityRepository;

	private final RefreshTokenRepository refreshTokenRepository;

	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public LoginResponse login(final String email, final String password) {
		UserEntity user = userEntityRepository.findByEmail(email)
			.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND_EXCEPTION));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new AppException(ErrorCode.USER_PASSWORD_MISMATCH_EXCEPTION);
		}

		Token token = jwtTokenProvider.createToken(user);

		// RefreshToken을 안전하게 저장하거나 업데이트
		refreshTokenRepository.saveOrUpdateByUserId(user.getId(), token.refreshToken());

		return LoginResponse.from(token);
	}
}
