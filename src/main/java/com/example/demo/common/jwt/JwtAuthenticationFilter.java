package com.example.demo.common.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.common.error.ErrorCode;
import com.example.demo.common.error.exception.AppException;
import com.example.demo.common.security.PermitUrlMatcher;
import com.example.demo.domain.jwt.JwtTokenProvider;
import com.example.demo.domain.user.entity.UserEntity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final PermitUrlMatcher permitUrlMatcher;

	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER = "Bearer ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws IOException, ServletException {

		final String authorizationHeader = request.getHeader(AUTHORIZATION);
		final String bearerToken = getBearerToken(authorizationHeader);

		jwtTokenProvider.validateToken(bearerToken);
		setAuthentication(bearerToken);

		filterChain.doFilter(request, response);
	}

	private void setAuthentication(String accessToken) {
		UserEntity userEntity = jwtTokenProvider.getAuthenticatedUser(accessToken);
		Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userEntity, "", List.of());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	private String getBearerToken(String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
			throw new AppException(ErrorCode.MALFORMED_TOKEN_EXCEPTION);
		}
		return authorizationHeader.replace(BEARER, "");
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return permitUrlMatcher.matches(request);
	}
}
