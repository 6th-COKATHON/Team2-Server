package com.example.demo.domain.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.quiz.entity.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
	
	@Query("SELECT q FROM QuizQuestion q WHERE q.article.id = :articleId")
	List<QuizQuestion> findByArticleId(@Param("articleId") Long articleId);
	
	@Query("SELECT q FROM QuizQuestion q WHERE q.article.articleId = :articleId")
	List<QuizQuestion> findByArticleArticleId(@Param("articleId") String articleId);
} 