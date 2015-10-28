package com.testing.edu.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "questions", catalog = "testing_system")
public class Questions implements java.io.Serializable {

	private Long id;
	private Tests tests;
	private String text;
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

	@Id
	@GeneratedValue(strategy = IDENTITY)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "questions")
	public Set<Answers> getAnswerses() {
		return this.answerses;
	}

	public void setAnswerses(Set<Answers> answerses) {
		this.answerses = answerses;
	}

}
