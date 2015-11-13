package com.testing.edu.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDTO {

    private Long id;
    private String title;
    private Integer grade;
    private String degree;
    private String studyForm;

    public GroupDTO() {

    }

    public GroupDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public GroupDTO(Long id, String title, Integer grade, String degree, String studyForm) {
        this.id = id;
        this.title = title;
        this.grade = grade;
        this.degree = degree;
        this.studyForm = studyForm;
    }
}
