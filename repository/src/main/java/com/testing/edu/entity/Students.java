package com.testing.edu.entity;

// Generated Oct 24, 2015 11:34:14 PM by Hibernate Tools 4.3.1

import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Students generated by hbm2java
 */
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "students", catalog = "testing_system")
public class Students implements java.io.Serializable {

	private Long id;
	private Groups groups;
	private User user;
	private String lastName;
	private String firstName;
	private String middleName;
	private String numberGradebook;
	private Set<Result> results = new HashSet<Result>(0);

	public Students() {
	}

	public Students(Groups groups, User user) {
		this.groups = groups;
		this.user = user;
	}

	public Students(Groups groups, User user, String lastName, String firstName, String middleName, String numberGradebook,
			Set<Result> results) {
		this.groups = groups;
		this.user = user;
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.numberGradebook = numberGradebook;
		this.results = results;
	}

	public Students(String lastname, String firstname, String middleName,
				   String numberGradebook) {
		this.lastName = lastname;
		this.firstName = firstname;
		this.middleName = middleName;
		this.numberGradebook = numberGradebook;
	}

	public Students(String lastname, String firstname, String middleName,
					String numberGradebook, Groups groups) {
		this(lastname, firstname, middleName, numberGradebook);
		this.groups = groups;
	}

	public Students(String lastname, String firstname, String middleName,
					String numberGradebook, Groups groups, User user) {
		this(lastname, firstname, middleName, numberGradebook, groups);
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_student", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groups_id_group", nullable = true)
	public Groups getGroups() {
		return this.groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "last_name", length = 255)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "first_name", length = 255)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "middle_name", length = 255)
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
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
