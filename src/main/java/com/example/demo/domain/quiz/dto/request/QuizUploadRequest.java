package com.example.demo.domain.quiz.dto.request;

import java.util.List;

public record QuizUploadRequest(
	Long articleId,
	List<QuizQuestionUploadDto> questions
) {
} 