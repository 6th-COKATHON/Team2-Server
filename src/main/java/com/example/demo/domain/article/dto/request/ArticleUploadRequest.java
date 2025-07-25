package com.example.demo.domain.article.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleUploadRequest {
	
	private String articleId;
	private String categoryId;
	private String imageUrl;
	private String title;
	private String description;
	private String source;
	private String date;
} 