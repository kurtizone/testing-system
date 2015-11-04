package com.testing.edu.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDTO {

    private Long id;
    private String title;
    private String grade;
    private String degree;
    private String studyForm;

    GroupDTO() {

    }

    GroupDTO(String grade, String degree, String studyForm) {
        this.grade = grade;
        this.degree = degree;
        this.studyForm = studyForm;
    }
}
