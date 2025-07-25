package com.example.demo.web.quiz;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.response.AppResponse;
import com.example.demo.domain.quiz.dto.request.QuizGradingRequest;
import com.example.demo.domain.quiz.dto.response.QuizGradingResponse;
import com.example.demo.domain.quiz.dto.response.QuizResponseDto;
import com.example.demo.domain.quiz.service.QuizService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
@Tag(name = "Quiz", description = "퀴즈 관련 API")
public class QuizController {
	
	private final QuizService quizService;
	
	@GetMapping("/article/{articleId}")
	@Operation(summary = "기사 퀴즈 조회", description = "특정 기사의 퀴즈 문제들을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "퀴즈 조회 성공"),
		@ApiResponse(responseCode = "404", description = "해당 기사의 퀴즈를 찾을 수 없음")
	})
	public ResponseEntity<AppResponse<QuizResponseDto>> getQuizByArticleId(
		@Parameter(description = "기사 ID", required = true)
		@PathVariable Long articleId) {
		
		QuizResponseDto response = quizService.getQuizByArticleId(articleId);
		return ResponseEntity.ok(AppResponse.ok(response));
	}
	
	@PostMapping("/grade")
	@Operation(summary = "퀴즈 채점", description = "사용자의 퀴즈 답안을 채점합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "채점 완료"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 (존재하지 않는 문제 ID 등)"),
		@ApiResponse(responseCode = "404", description = "해당 기사의 퀴즈를 찾을 수 없음")
	})
	public ResponseEntity<AppResponse<QuizGradingResponse>> gradeQuiz(
		@RequestBody QuizGradingRequest request) {
		
		QuizGradingResponse response = quizService.gradeQuiz(request);
		return ResponseEntity.ok(AppResponse.ok(response));
	}
} 