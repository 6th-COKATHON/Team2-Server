package com.example.demo.domain.article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "article_id", unique = true, nullable = false)
	private String articleId;

	@Column(name = "category_id", nullable = false, length = 50)
	private String categoryId;

	@Column(name = "image_url", length = 1000)
	private String imageUrl;

	@Column(name = "title", nullable = false, length = 500)
	private String title;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "source", length = 100)
	private String source;

	@Column(name = "date", length = 20)
	private String date;
}
