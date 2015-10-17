package com.testing.edu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "result", catalog = "testing_system")
public class Result implements java.io.Serializable {

	private Integer id;
	private Students students;
	private Tests tests;
	private String testTitle;
	private Integer mark;

	public Result() {
	}

	public Result(Students students, Tests tests) {
		this.students = students;
		this.tests = tests;
	}

	public Result(Students students, Tests tests, String testTitle, Integer mark) {
		this.students = students;
		this.tests = tests;
		this.testTitle = testTitle;
		this.mark = mark;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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
	@JoinColumn(name = "tests_id", nullable = false)
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
	public Integer getMark() {
		return this.mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

}
