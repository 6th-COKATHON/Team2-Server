package com.example.demo.web.ai;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.response.AppResponse;
import com.example.demo.domain.ai.service.OpenAIService;
import com.example.demo.web.ai.dto.request.QuizRequest;
import com.example.demo.web.ai.dto.response.QuizListResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

	private final OpenAIService openAIService;

	@PostMapping("/quiz")
	public ResponseEntity<AppResponse<QuizListResponse>> generateQuiz(QuizRequest quizRequest) {
		QuizListResponse response = openAIService.generateQuiz(quizRequest.title(), quizRequest.content());
		return ResponseEntity.ok(AppResponse.created(response));
	}

}
