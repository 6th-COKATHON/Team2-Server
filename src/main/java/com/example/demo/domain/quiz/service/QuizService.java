package com.example.demo.domain.quiz.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.error.ErrorCode;
import com.example.demo.common.error.exception.AppException;
import com.example.demo.domain.article.entity.Article;
import com.example.demo.domain.article.repository.ArticleRepository;
import com.example.demo.domain.quiz.dto.request.QuizAnswerDto;
import com.example.demo.domain.quiz.dto.request.QuizBulkUploadRequest;
import com.example.demo.domain.quiz.dto.request.QuizGradingRequest;
import com.example.demo.domain.quiz.dto.request.QuizQuestionUploadDto;
import com.example.demo.domain.quiz.dto.request.QuizUploadRequest;
import com.example.demo.domain.quiz.dto.response.QuizGradingResponse;
import com.example.demo.domain.quiz.dto.response.QuizQuestionDto;
import com.example.demo.domain.quiz.dto.response.QuizResponseDto;
import com.example.demo.domain.quiz.dto.response.QuizResultDto;
import com.example.demo.domain.quiz.entity.QuizQuestion;
import com.example.demo.domain.quiz.repository.QuizQuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {
	
	private final QuizQuestionRepository quizQuestionRepository;
	private final ArticleRepository articleRepository;
	
	/**
	 * 여러 기사의 퀴즈 문제들을 한 번에 업로드합니다.
	 */
	@Transactional
	public void bulkUploadQuiz(QuizBulkUploadRequest request) {
		// 모든 기사의 퀴즈를 순차적으로 업로드
		request.quizzes().forEach(this::uploadQuiz);
	}
	
	/**
	 * 퀴즈 문제들을 업로드합니다.
	 */
	@Transactional
	public void uploadQuiz(QuizUploadRequest request) {
		// Article 존재 여부 확인
		Article article = articleRepository.findById(request.articleId())
			.orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_EXCEPTION));
		
		// 기존 퀴즈 문제들 삭제 (중복 방지)
		List<QuizQuestion> existingQuestions = quizQuestionRepository.findByArticleId(request.articleId());
		if (!existingQuestions.isEmpty()) {
			quizQuestionRepository.deleteAll(existingQuestions);
		}
		
		// 새로운 퀴즈 문제들 저장
		List<QuizQuestion> quizQuestions = request.questions().stream()
			.map(dto -> QuizQuestion.of(article, dto.question(), dto.correctAnswer()))
			.collect(Collectors.toList());
		
		quizQuestionRepository.saveAll(quizQuestions);
	}
	
	/**
	 * 특정 기사의 퀴즈 문제들을 조회합니다.
	 */
	public QuizResponseDto getQuizByArticleId(Long articleId) {
		List<QuizQuestion> quizQuestions = quizQuestionRepository.findByArticleId(articleId);
		
		if (quizQuestions.isEmpty()) {
			throw new AppException(ErrorCode.NOT_FOUND_EXCEPTION);
		}
		
		List<QuizQuestionDto> questionDtos = quizQuestions.stream()
			.map(QuizQuestionDto::from)
			.collect(Collectors.toList());
		
		return QuizResponseDto.of(articleId, questionDtos);
	}
	
	/**
	 * 퀴즈 답안을 채점합니다.
	 */
	public QuizGradingResponse gradeQuiz(QuizGradingRequest request) {
		List<QuizQuestion> quizQuestions = quizQuestionRepository.findByArticleId(request.articleId());
		
		if (quizQuestions.isEmpty()) {
			throw new AppException(ErrorCode.NOT_FOUND_EXCEPTION);
		}
		
		// 퀴즈 문제 ID와 정답 매핑
		Map<Long, Boolean> correctAnswers = quizQuestions.stream()
			.collect(Collectors.toMap(
				QuizQuestion::getId,
				QuizQuestion::getCorrectAnswer
			));
		
		// 사용자 답안 채점
		List<QuizResultDto> results = request.answers().stream()
			.map(answer -> gradeAnswer(answer, correctAnswers))
			.collect(Collectors.toList());
		
		return QuizGradingResponse.of(request.articleId(), results);
	}
	
	/**
	 * 특정 기사의 퀴즈 실제 정답 데이터를 반환합니다.
	 */
	public QuizGradingResponse getQuizAnswers(Long articleId) {
		List<QuizQuestion> quizQuestions = quizQuestionRepository.findByArticleId(articleId);
		if (quizQuestions.isEmpty()) {
			throw new AppException(ErrorCode.NOT_FOUND_EXCEPTION);
		}
		List<QuizResultDto> results = quizQuestions.stream()
			.map(q -> QuizResultDto.of(q.getId(), q.getCorrectAnswer()))
			.collect(Collectors.toList());
		return QuizGradingResponse.of(articleId, results);
	}
	
	private QuizResultDto gradeAnswer(QuizAnswerDto answer, Map<Long, Boolean> correctAnswers) {
		Boolean correctAnswer = correctAnswers.get(answer.id());
		
		if (correctAnswer == null) {
			throw new AppException(ErrorCode.BAD_REQUEST_EXCEPTION);
		}
		
		boolean isCorrect = correctAnswer.equals(answer.answer());
		return QuizResultDto.of(answer.id(), isCorrect);
	}
} 