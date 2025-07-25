package com.example.demo.domain.quiz.dto.request;

import java.util.List;

public record QuizGradingRequest(
	Long articleId,
	List<QuizAnswerDto> answers
) {
} 