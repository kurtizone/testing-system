package com.testing.edu.entity;

import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@EqualsAndHashCode
@Entity
@Table(name = "groups", catalog = "testing_system")
public class Groups implements java.io.Serializable {

	private Long id;
	private String title;
	private Integer grade;
	private String degree;
	private String studyForm;
	private Set<Students> studentses = new HashSet<Students>(0);
	private Set<Subject> subjects = new HashSet<Subject>(0);

	public Groups() {
	}

	public Groups(String title, Integer grade, String degree, String studyForm, Set<Students> studentses,
			Set<Subject> subjects) {
		this.title = title;
		this.grade = grade;
		this.degree = degree;
		this.studyForm = studyForm;
		this.studentses = studentses;
		this.subjects = subjects;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_group", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id= id;
	}

	@Column(name = "title", length = 45)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "grade")
	public Integer getGrade() {
		return this.grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	@Column(name = "degree", length = 45)
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Column(name = "study_form", length = 45)
	public String getStudyForm() {
		return this.studyForm;
	}

	public void setStudyForm(String studyForm) {
		this.studyForm = studyForm;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "groups")
	public Set<Students> getStudentses() {
		return this.studentses;
	}

	public void setStudentses(Set<Students> studentses) {
		this.studentses = studentses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "subject_has_groups", catalog = "testing_system", joinColumns = { @JoinColumn(name = "groups_id_group", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "subject_id_subject", nullable = false, updatable = false) })
	public Set<Subject> getSubjects() {
		return this.subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

}
