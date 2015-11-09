package com.testing.edu.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDTO {

    private Long id;
    private String text;
    private String questionType;

    public QuestionDTO(){}

    public QuestionDTO(Long id, String text, String questionType) {
        this.id = id;
        this.text = text;
        this.questionType = questionType;
    }
}
