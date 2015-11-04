package com.testing.edu.entity;

import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "subject", catalog = "testing_system")
public class Subject implements java.io.Serializable {

	private Long id;
	private String title;
	private Double multiplier;
	private Integer hours;
	private Set<Lecturers> lecturerses = new HashSet<Lecturers>(0);
	private Set<Tests> testses = new HashSet<Tests>(0);
	private Set<Groups> groupses = new HashSet<Groups>(0);

	public Subject() {
	}

	public Subject(String title, Double multiplier, Integer hours) {
		this.title = title;
		this.multiplier = multiplier;
		this.hours = hours;
	}

	public Subject(String title, Double multiplier, Integer hours, Set<Lecturers> lecturerses, Set<Tests> testses,
			Set<Groups> groupses) {
		this.title = title;
		this.multiplier = multiplier;
		this.hours = hours;
		this.lecturerses = lecturerses;
		this.testses = testses;
		this.groupses = groupses;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_subject", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "multiplier")
	public Double getMultiplier() {
		return this.multiplier;
	}

	public void setMultiplier(Double multiplier) {
		this.multiplier = multiplier;
	}

	@Column(name = "hours")
	public Integer getHours() {
		return this.hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "subject_has_lecturers", catalog = "testing_system", joinColumns = { @JoinColumn(name = "subject_id_subject", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "lecturers_id_lecturers", nullable = false, updatable = false) })
	public Set<Lecturers> getLecturerses() {
		return this.lecturerses;
	}

	public void setLecturerses(Set<Lecturers> lecturerses) {
		this.lecturerses = lecturerses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
	public Set<Tests> getTestses() {
		return this.testses;
	}

	public void setTestses(Set<Tests> testses) {
		this.testses = testses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "subject_has_groups", catalog = "testing_system", joinColumns = { @JoinColumn(name = "subject_id_subject", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "groups_id_group", nullable = false, updatable = false) })
	public Set<Groups> getGroupses() {
		return this.groupses;
	}

	public void setGroupses(Set<Groups> groupses) {
		this.groupses = groupses;
	}

}
