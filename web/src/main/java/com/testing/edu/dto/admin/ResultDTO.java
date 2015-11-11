package com.testing.edu.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDTO {

    private Long id;
    private String studentLastname;
    private String studentFirstname;
    private String studentMiddlename;
    private String groupTitle;
    private String subjectTitle;
    private Double mark;
    private Integer maxGrade;

    public ResultDTO(){}

    public ResultDTO(String studentLastname, String studentFirstname, String studentMiddlename,
                     String groupTitle, String subjectTitle, Double mark, Integer maxGrade) {
        this.studentLastname = studentLastname;
        this.studentFirstname = studentFirstname;
        this.studentMiddlename = studentMiddlename;
        this.groupTitle = groupTitle;
        this.subjectTitle = subjectTitle;
        this.mark = mark;
        this.maxGrade = maxGrade;
    }

    public ResultDTO(Long id, String studentLastname, String studentFirstname, String studentMiddlename,
                     String groupTitle, String subjectTitle, Double mark, Integer maxGrade) {
        this(studentLastname, studentFirstname, studentMiddlename, groupTitle, subjectTitle, mark, maxGrade);
        this.id = id;
    }
}
