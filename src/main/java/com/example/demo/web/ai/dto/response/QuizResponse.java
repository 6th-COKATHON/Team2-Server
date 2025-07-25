package com.example.demo.web.ai.dto.response;

import java.util.List;

public record QuizResponse(
	String question,      // 질문
	QuizType quizType,    // 퀴즈 유형 (객관식 또는 OX)
	List<String> options, // 객관식 보기 목록 (OX 퀴즈의 경우 비어있을 수 있음)
	String answer         // 정답
) {
}
