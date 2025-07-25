package com.example.demo.web.article;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.response.AppResponse;
import com.example.demo.domain.article.dto.request.ArticleUploadRequest;
import com.example.demo.domain.article.dto.response.ArticleDto;
import com.example.demo.domain.article.dto.response.ArticleWithQuizResponseDto;
import com.example.demo.domain.article.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	@PostMapping("/upload")
	public ResponseEntity<AppResponse<List<String>>> uploadArticles(
		@RequestBody List<ArticleUploadRequest> requests) {
		
		List<String> savedArticleIds = articleService.uploadArticles(requests);
		
		return ResponseEntity.ok(AppResponse.ok(savedArticleIds));
	}

	@GetMapping
	public ResponseEntity<AppResponse<List<ArticleDto>>> getAllArticles() {
		List<ArticleDto> articles = articleService.getAllArticles();
		
		return ResponseEntity.ok(AppResponse.ok(articles));
	}

	@GetMapping("/{articleId}/with-quiz")
	public ResponseEntity<AppResponse<ArticleWithQuizResponseDto>> getArticleWithQuiz(@PathVariable String articleId) {
		ArticleWithQuizResponseDto response = articleService.getArticleWithQuizByArticleId(articleId);
		return ResponseEntity.ok(AppResponse.ok(response));
	}
} 