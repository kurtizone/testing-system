package com.testing.edu.dto.admin;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {

    private Long id;
    private String text;
    private Double grade;
    private Boolean correct;

    public AnswerDTO(){}

    public AnswerDTO(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public AnswerDTO(Long id, String text, Double grade) {
        this(id, text);
        this.grade = grade;
    }

    public AnswerDTO(String text, Boolean correct) {
        this.text = text;
        this.correct = correct;
    }
}
