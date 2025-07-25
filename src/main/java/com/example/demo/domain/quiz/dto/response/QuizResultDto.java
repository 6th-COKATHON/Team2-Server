package com.example.demo.domain.quiz.dto.response;

public record QuizResultDto(
	Long id,
	Boolean correctAnswer
) {
	public static QuizResultDto of(Long id, Boolean correctAnswer) {
		return new QuizResultDto(id, correctAnswer);
	}
} 