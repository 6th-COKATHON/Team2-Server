package com.example.demo.domain.article.dto.response;

import com.example.demo.domain.article.entity.Article;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleDto {
	
	private Long id;
	private String articleId;
	private String categoryId;
	private String imageUrl;
	private String title;
	private String description;
	private String source;
	private String date;
	
	public static ArticleDto from(Article article) {
		return ArticleDto.builder()
			.id(article.getId())
			.articleId(article.getArticleId())
			.categoryId(article.getCategoryId())
			.imageUrl(article.getImageUrl())
			.title(article.getTitle())
			.description(article.getDescription())
			.source(article.getSource())
			.date(article.getDate())
			.build();
	}
} 