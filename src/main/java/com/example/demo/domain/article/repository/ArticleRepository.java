package com.example.demo.domain.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.article.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	
	boolean existsByArticleId(String articleId);
	
} 