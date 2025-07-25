package com.example.demo.domain.article.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.article.dto.request.ArticleUploadRequest;
import com.example.demo.domain.article.dto.response.ArticleDto;
import com.example.demo.domain.article.entity.Article;
import com.example.demo.domain.article.repository.ArticleRepository;
import com.example.demo.domain.quiz.dto.response.QuizQuestionDto;
import com.example.demo.domain.quiz.repository.QuizQuestionRepository;
import com.example.demo.domain.article.dto.response.ArticleWithQuizResponseDto;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final QuizQuestionRepository quizQuestionRepository;

	@Transactional
	public List<String> uploadArticles(List<ArticleUploadRequest> requests) {
		List<String> savedArticleIds = new ArrayList<>();
		
		for (ArticleUploadRequest request : requests) {
			// 중복 체크 (선택사항)
			if (articleRepository.existsByArticleId(request.getArticleId())) {
				continue; // 이미 존재하는 기사는 건너뜀
			}
			
			Article article = Article.builder()
				.articleId(request.getArticleId())
				.categoryId(request.getCategoryId())
				.imageUrl(request.getImageUrl())
				.title(request.getTitle())
				.description(request.getDescription())
				.source(request.getSource())
				.date(request.getDate())
				.build();
			
			Article savedArticle = articleRepository.save(article);
			savedArticleIds.add(savedArticle.getArticleId());
		}
		
		return savedArticleIds;
	}
	
	@Transactional(readOnly = true)
	public List<ArticleDto> getAllArticles() {
		List<Article> articles = articleRepository.findAll();
		
		return articles.stream()
			.map(ArticleDto::from)
			.collect(Collectors.toList());
	}

	public ArticleWithQuizResponseDto getArticleWithQuizByArticleId(String articleId) {
		Article article = articleRepository.findByArticleId(articleId)
			.orElseThrow(() -> new RuntimeException("해당 articleId의 기사가 존재하지 않습니다."));
		List<QuizQuestionDto> quizList = quizQuestionRepository.findByArticleArticleId(articleId)
			.stream().map(QuizQuestionDto::from).collect(Collectors.toList());
		return ArticleWithQuizResponseDto.of(ArticleDto.from(article), quizList);
	}
} 