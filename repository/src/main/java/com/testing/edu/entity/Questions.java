package com.testing.edu.entity;

import com.testing.edu.entity.enumeration.QuestionType;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "questions", catalog = "testing_system")
public class Questions implements java.io.Serializable {

	private Long id;
	private Tests tests;
	private String text;
	private QuestionType questionType;
	private Set<Answers> answerses = new HashSet<Answers>(0);

	public Questions() {
	}

	public Questions(Tests tests) {
		this.tests = tests;
	}

	public Questions(Tests tests, String text, Set<Answers> answerses) {
		this.tests = tests;
		this.text = text;
		this.answerses = answerses;
	}

	public Questions(String text, QuestionType questionType, Tests tests){
		this.text = text;
		this.questionType = questionType;
		this.tests = tests;
	}
	public Questions(String text, QuestionType questionType, Set<Answers> answerses, Tests tests) {
		this(tests, text, answerses);
		this.questionType = questionType;
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
	@JoinColumn(name = "tests_id_test", nullable = false)
	public Tests getTests() {
		return this.tests;
	}

	public void setTests(Tests tests) {
		this.tests = tests;
	}

	@Column(name = "text", length = 500)
	public String getText() {
		return this.text;
	}


	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "question_type")
	@Enumerated(EnumType.STRING)
	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "questions", orphanRemoval = true)
	public Set<Answers> getAnswerses() {
		return this.answerses;
	}

	public void setAnswerses(Set<Answers> answerses) {
		this.answerses = answerses;
	}

}
