package com.testing.edu.dto.admin;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestDTO {
    private Long id;
    private String title;
    private String testType;
    private Integer maxGrade;
    private Boolean avaible;

    public TestDTO() {

    }

    public TestDTO(Long id, String title, String testType, Integer maxGrade, Boolean avaible) {
        this.id = id;
        this.title = title;
        this.testType = testType;
        this.maxGrade = maxGrade;
        this.avaible = avaible;
    }
}
