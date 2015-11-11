package com.testing.edu.entity;

import lombok.EqualsAndHashCode;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.*;

@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "answers", catalog = "testing_system")
public class Answers implements java.io.Serializable {

	private Long id;
	private Questions questions;
	private String text;
	private Double grade;

	public Answers() {
	}

	public Answers(Questions questions) {
		this.questions = questions;
	}

	public Answers(String text, Double grade) {
		this.text = text;
		this.grade = grade;
	}

	public Answers(Long id, String text, Double grade) {
		this(text, grade);
		this.id = id;
	}

	public Answers(Questions questions, String text, Double grade) {
		this(text, grade);
		this.questions = questions;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "questions_id_questions", nullable = false)
	public Questions getQuestions() {
		return this.questions;
	}

	public void setQuestions(Questions questions) {
		this.questions = questions;
	}

	@Column(name = "text", length = 500)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "grade")
	public Double getGrade() {
		return this.grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

}
