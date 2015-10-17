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
@Table(name = "students", catalog = "testing_system")
public class Students implements java.io.Serializable {

	private Integer idStudent;
	private Groups groups;
	private String lastName;
	private String firstName;
	private String numberGradebook;
	private Set<Result> results = new HashSet<Result>(0);

	public Students() {
	}

	public Students(Groups groups) {
		this.groups = groups;
	}

	public Students(Groups groups, String lastName, String firstName, String numberGradebook, Set<Result> results) {
		this.groups = groups;
		this.lastName = lastName;
		this.firstName = firstName;
		this.numberGradebook = numberGradebook;
		this.results = results;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_student", unique = true, nullable = false)
	public Integer getIdStudent() {
		return this.idStudent;
	}

	public void setIdStudent(Integer idStudent) {
		this.idStudent = idStudent;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groups_id_group", nullable = false)
	public Groups getGroups() {
		return this.groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	@Column(name = "last_name", length = 45)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "first_name", length = 45)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "number_gradebook", length = 45)
	public String getNumberGradebook() {
		return this.numberGradebook;
	}

	public void setNumberGradebook(String numberGradebook) {
		this.numberGradebook = numberGradebook;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "students")
	public Set<Result> getResults() {
		return this.results;
	}

	public void setResults(Set<Result> results) {
		this.results = results;
	}

}
