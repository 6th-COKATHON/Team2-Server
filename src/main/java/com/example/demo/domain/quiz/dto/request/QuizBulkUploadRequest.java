package com.example.demo.domain.quiz.dto.request;

import java.util.List;

public record QuizBulkUploadRequest(
	List<QuizUploadRequest> quizzes
) {
} 