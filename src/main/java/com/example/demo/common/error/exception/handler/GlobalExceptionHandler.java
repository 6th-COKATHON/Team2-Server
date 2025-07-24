package com.example.demo.common.error.exception.handler;

import java.util.List;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.common.error.ErrorCode;
import com.example.demo.common.error.exception.AppException;
import com.example.demo.common.response.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AppException.class)
	public ResponseEntity<ErrorResponse> handleAppException(AppException e) {
		log.error("AppException occurred: errorCode={}, message={}", e.getErrorCode().getCode(), e.getMessage());

		ErrorResponse errorResponse = ErrorResponse.from(e.getErrorCode());

		return ResponseEntity
			.status(e.getErrorCode().getStatus())
			.body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		log.error("Unhandled exception occurred: ", e);

		ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR);

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(errorResponse);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatusCode status,
		WebRequest request) {
		ServletWebRequest servletWebRequest = (ServletWebRequest)request;
		HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
		String requestURI = httpServletRequest.getRequestURI();

		List<String> messages = ex.getBindingResult().getFieldErrors().stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.toList();

		log.error("MethodArgumentNotValidException occurred: requestURI={}, error={}", requestURI, ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ErrorResponse.of(ErrorCode.USER_INPUT_EXCEPTION, messages));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolation(
		ConstraintViolationException ex, WebRequest request) {
		ServletWebRequest servletWebRequest = (ServletWebRequest)request;
		HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
		String requestURI = httpServletRequest.getRequestURI();

		List<String> messages = ex.getConstraintViolations().stream()
			.map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
			.toList();

		log.error("ConstraintViolationException occurred: requestURI={}, error={}", requestURI, ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ErrorResponse.of(ErrorCode.USER_INPUT_EXCEPTION, messages));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(
		MethodArgumentTypeMismatchException ex, WebRequest request) {
		ServletWebRequest servletWebRequest = (ServletWebRequest)request;
		HttpServletRequest httpServletRequest = servletWebRequest.getRequest();
		String requestURI = httpServletRequest.getRequestURI();

		String message = String.format("Invalid argument type. Path: %s, Invalid value: %s", ex.getName(),
			ex.getValue());

		log.error("MethodArgumentTypeMismatchException occurred: requestURI={}, error={}", requestURI, message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.from(ErrorCode.USER_INPUT_EXCEPTION));
	}
}