package com.example.demo.domain.quiz.dto.response;

import java.util.List;

public record QuizGradingResponse(
	Long articleId,
	List<QuizResultDto> results
) {
	public static QuizGradingResponse of(Long articleId, List<QuizResultDto> results) {
		return new QuizGradingResponse(articleId, results);
	}
} 