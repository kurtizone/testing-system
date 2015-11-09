package com.testing.edu.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDTO {

    private Long id;
    private String text;
    private Double grade;

    public AnswerDTO(){}

    public AnswerDTO(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public AnswerDTO(Long id, String text, Double grade) {
        this(id, text);
        this.grade = grade;
    }
}
