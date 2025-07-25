package com.example.demo.domain.article.dto.response;

import java.util.List;
import com.example.demo.domain.quiz.dto.response.QuizQuestionDto;

public record ArticleWithQuizResponseDto(
	ArticleDto article,
	List<QuizQuestionDto> quizList
) {
	public static ArticleWithQuizResponseDto of(ArticleDto article, List<QuizQuestionDto> quizList) {
		return new ArticleWithQuizResponseDto(article, quizList);
	}
} 