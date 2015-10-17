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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "lecturers", catalog = "testing_system")
public class Lecturers implements java.io.Serializable {

	private Integer idLecturers;
	private String lastName;
	private String firstName;
	private String academicStatus;
	private String degree;
	private Set<Subject> subjects = new HashSet<Subject>(0);

	public Lecturers() {
	}

	public Lecturers(String lastName, String firstName, String academicStatus, String degree, Set<Subject> subjects) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.academicStatus = academicStatus;
		this.degree = degree;
		this.subjects = subjects;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_lecturers", unique = true, nullable = false)
	public Integer getIdLecturers() {
		return this.idLecturers;
	}

	public void setIdLecturers(Integer idLecturers) {
		this.idLecturers = idLecturers;
	}

	@Column(name = "last_name")
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "first_name")
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "academic status")
	public String getAcademicStatus() {
		return this.academicStatus;
	}

	public void setAcademicStatus(String academicStatus) {
		this.academicStatus = academicStatus;
	}

	@Column(name = "degree")
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "subject_has_lecturers", catalog = "testing_system", joinColumns = { @JoinColumn(name = "lecturers_id_lecturers", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "subject_id_subject", nullable = false, updatable = false) })
	public Set<Subject> getSubjects() {
		return this.subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

}
