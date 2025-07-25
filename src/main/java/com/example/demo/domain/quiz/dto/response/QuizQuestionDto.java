package com.example.demo.domain.quiz.dto.response;

import com.example.demo.domain.quiz.entity.QuizQuestion;

public record QuizQuestionDto(
	Long id,
	String question
) {
	public static QuizQuestionDto from(QuizQuestion quizQuestion) {
		return new QuizQuestionDto(
			quizQuestion.getId(),
			quizQuestion.getQuestion()
		);
	}
} 