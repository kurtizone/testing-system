package com.testing.edu.entity;

import com.testing.edu.entity.enumeration.TestType;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "tests", catalog = "testing_system")
public class Tests implements java.io.Serializable {

	private Long id;
	private Subject subject;
	private String title;
	private TestType type;
	private Integer maxGrade;
	private Boolean avaible;
	private Set<Result> results = new HashSet<Result>(0);
	private Set<Questions> questionses = new HashSet<Questions>(0);

	public Tests() {
	}

	public Tests(Subject subject) {
		this.subject = subject;
	}

	public Tests(Subject subject, String title, TestType type, Integer maxGrade, Boolean avaible, Set<Result> results,
			Set<Questions> questionses) {
		this.subject = subject;
		this.title = title;
		this.type = type;
		this.maxGrade = maxGrade;
		this.avaible = avaible;
		this.results = results;
		this.questionses = questionses;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subject_id_subject", nullable = false)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Column(name = "title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	public TestType getType() {
		return this.type;
	}

	public void setType(TestType type) {
		this.type = type;
	}

	@Column(name = "max_grade")
	public Integer getMaxGrade() {
		return this.maxGrade;
	}

	public void setMaxGrade(Integer maxGrade) {
		this.maxGrade = maxGrade;
	}

	@Column(name = "avaible")
	public Boolean getAvaible() {
		return this.avaible;
	}

	public void setAvaible(Boolean avaible) {
		this.avaible = avaible;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tests")
	public Set<Result> getResults() {
		return this.results;
	}

	public void setResults(Set<Result> results) {
		this.results = results;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tests")
	public Set<Questions> getQuestionses() {
		return this.questionses;
	}

	public void setQuestionses(Set<Questions> questionses) {
		this.questionses = questionses;
	}

}
