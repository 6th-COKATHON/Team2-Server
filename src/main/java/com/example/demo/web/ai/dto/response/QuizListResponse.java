package com.example.demo.web.ai.dto.response;

import java.util.List;

public record QuizListResponse(
	List<QuizResponse> qna
) {
}
