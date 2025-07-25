package com.example.demo.web.quiz;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.response.AppResponse;
import com.example.demo.domain.quiz.dto.request.QuizBulkUploadRequest;
import com.example.demo.domain.quiz.dto.request.QuizGradingRequest;
import com.example.demo.domain.quiz.dto.request.QuizUploadRequest;
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
	
	@PostMapping("/bulk-upload")
	@Operation(summary = "퀴즈 일괄 업로드", description = "여러 기사의 퀴즈 문제들을 한 번에 업로드합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "퀴즈 일괄 업로드 성공"),
		@ApiResponse(responseCode = "404", description = "해당 기사를 찾을 수 없음")
	})
	public ResponseEntity<AppResponse<String>> bulkUploadQuiz(
		@RequestBody QuizBulkUploadRequest request) {
		
		quizService.bulkUploadQuiz(request);
		return ResponseEntity.ok(AppResponse.created("모든 퀴즈가 성공적으로 업로드되었습니다."));
	}
	
	@PostMapping("/upload")
	@Operation(summary = "퀴즈 업로드", description = "기사에 대한 퀴즈 문제들을 업로드합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "퀴즈 업로드 성공"),
		@ApiResponse(responseCode = "404", description = "해당 기사를 찾을 수 없음")
	})
	public ResponseEntity<AppResponse<String>> uploadQuiz(
		@RequestBody QuizUploadRequest request) {
		
		quizService.uploadQuiz(request);
		return ResponseEntity.ok(AppResponse.created("퀴즈가 성공적으로 업로드되었습니다."));
	}
	
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
	@Operation(summary = "퀴즈 정답 조회", description = "특정 기사 퀴즈의 실제 정답 데이터를 내려줍니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "정답 데이터 반환 완료"),
		@ApiResponse(responseCode = "404", description = "해당 기사의 퀴즈를 찾을 수 없음")
	})
	public ResponseEntity<AppResponse<QuizGradingResponse>> getQuizAnswers(
		@RequestBody QuizGradingRequest request) {
		// 실제 정답 데이터 반환
		QuizGradingResponse response = quizService.getQuizAnswers(request.articleId());
		return ResponseEntity.ok(AppResponse.ok(response));
	}
} 