package com.testing.edu.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "result", catalog = "testing_system")
public class Result implements java.io.Serializable {

	private Long id;
	private Students students;
	private Tests tests;
	private String testTitle;
	private Double mark;
	private Integer maxGrade;

	public Result() {
	}

	public Result(Students students, Tests tests) {
		this.students = students;
		this.tests = tests;
	}

	public Result(Students students, Tests tests, String testTitle, Double mark, Integer maxGrade) {
		this.students = students;
		this.tests = tests;
		this.testTitle = testTitle;
		this.mark = mark;
		this.maxGrade = maxGrade;
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
	@JoinColumn(name = "students_id_student", nullable = false)
	public Students getStudents() {
		return this.students;
	}

	public void setStudents(Students students) {
		this.students = students;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tests_id", nullable = true)
	public Tests getTests() {
		return this.tests;
	}

	public void setTests(Tests tests) {
		this.tests = tests;
	}

	@Column(name = "test_title")
	public String getTestTitle() {
		return this.testTitle;
	}

	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}

	@Column(name = "mark")
	public Double getMark() {
		return this.mark;
	}

	public void setMark(Double mark) {
		this.mark = mark;
	}

	@Column(name = "max_grade")
	public Integer getMaxGrade() {
		return maxGrade;
	}

	public void setMaxGrade(Integer maxGrade) {
		this.maxGrade = maxGrade;
	}
}
