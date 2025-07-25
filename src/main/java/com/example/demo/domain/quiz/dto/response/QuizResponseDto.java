package com.example.demo.domain.quiz.dto.response;

import java.util.List;

public record QuizResponseDto(
	Long articleId,
	List<QuizQuestionDto> questions
) {
	public static QuizResponseDto of(Long articleId, List<QuizQuestionDto> questions) {
		return new QuizResponseDto(articleId, questions);
	}
} 