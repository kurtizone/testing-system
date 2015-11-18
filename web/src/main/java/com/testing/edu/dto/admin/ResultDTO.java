package com.testing.edu.dto.admin;

import com.testing.edu.entity.Result;
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
    private String testTitle;
    private String testType;
    private Double mark;
    private Integer maxGrade;

    public ResultDTO(){}

    public ResultDTO(String studentLastname, String studentFirstname, String studentMiddlename, Double mark, Integer maxGrade, String testTitle){
        this.studentLastname = studentLastname;
        this.studentFirstname = studentFirstname;
        this.studentMiddlename = studentMiddlename;
        this.mark = mark;
        this.maxGrade = maxGrade;
        this.testTitle = testTitle;
    }

    public ResultDTO(String studentLastname, String studentFirstname, String studentMiddlename,
                     String groupTitle, String subjectTitle, String testTitle, String testType, Double mark, Integer maxGrade) {
        this.studentLastname = studentLastname;
        this.studentFirstname = studentFirstname;
        this.studentMiddlename = studentMiddlename;
        this.groupTitle = groupTitle;
        this.subjectTitle = subjectTitle;
        this.testTitle = testTitle;
        this.testType = testType;
        this.mark = mark;
        this.maxGrade = maxGrade;
    }

    public ResultDTO(Long id, String studentLastname, String studentFirstname, String studentMiddlename,
                     String groupTitle, String subjectTitle, String testTitle, String testType, Double mark, Integer maxGrade) {
        this(studentLastname, studentFirstname, studentMiddlename, groupTitle, subjectTitle, testTitle, testType, mark, maxGrade);
        this.id = id;
    }
}
