package com.example.demo.common.security.filter;

import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.common.error.ErrorCode;
import com.example.demo.common.error.exception.AppException;
import com.example.demo.common.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityExceptionFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (AppException e) {
			handleAppException(response, e);
		} catch (BadCredentialsException | JwtException e) {
			handleSecurityException(response, ErrorCode.JWT_AUTH_EXCEPTION, e);
		} catch (AccessDeniedException e) {
			handleSecurityException(response, ErrorCode.USER_ROLE_EXCEPTION, e);
		} catch (Exception e) {
			handleUnknownException(response, e);
		}
	}

	private void handleAppException(HttpServletResponse response, AppException e) throws IOException {
		log.error("AppException occurred in Security Filter: errorCode={}, message={}",
			e.getErrorCode().getCode(), e.getMessage());

		ErrorResponse errorResponse = ErrorResponse.from(e.getErrorCode());
		writeErrorResponse(response, e.getErrorCode().getStatus().value(), errorResponse);
	}

	private void handleSecurityException(HttpServletResponse response, ErrorCode errorCode, Exception e)
		throws IOException {
		log.error("Security Exception occurred in Security Filter: errorCode={}, message={}", errorCode.getCode(), e.getMessage());

		ErrorResponse errorResponse = ErrorResponse.from(errorCode);
		writeErrorResponse(response, errorCode.getStatus().value(), errorResponse);
	}

	private void handleUnknownException(HttpServletResponse response, Exception e) throws IOException {
		log.error("Unknown exception occurred in Security Filter: ", e);

		ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR);
		writeErrorResponse(response, ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value(), errorResponse);
	}

	private void writeErrorResponse(HttpServletResponse response, int status, ErrorResponse errorResponse)
		throws IOException {
		response.setStatus(status);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}
}