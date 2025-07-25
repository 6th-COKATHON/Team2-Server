package com.example.demo.domain.quiz.dto.request;

public record QuizQuestionUploadDto(
	Long id,
	String question,
	Boolean correctAnswer
) {
} 