package com.example.demo.domain.quiz.entity;

import com.example.demo.common.base.BaseEntity;
import com.example.demo.domain.article.entity.Article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class QuizQuestion extends BaseEntity {

	@Id
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;

	@Column(name = "question", nullable = false, columnDefinition = "TEXT")
	private String question;

	@Column(name = "correct_answer", nullable = false)
	private Boolean correctAnswer;

	public static QuizQuestion of(Long id, Article article, String question, Boolean correctAnswer) {
		return QuizQuestion.builder()
			.id(id)
			.article(article)
			.question(question)
			.correctAnswer(correctAnswer)
			.build();
	}
} 