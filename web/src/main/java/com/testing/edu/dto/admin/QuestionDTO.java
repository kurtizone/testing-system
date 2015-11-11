package com.testing.edu.dto.admin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionDTO {

    private Long id;
    private String text;
    private String questionType;
    private List<AnswerDTO> answerDTOList;
    private Long testId;

    public QuestionDTO(){}

    public QuestionDTO(Long id, String text, String questionType, List<AnswerDTO> answerDTOList) {
        this.id = id;
        this.text = text;
        this.questionType = questionType;
        this.answerDTOList = answerDTOList;
    }

    public QuestionDTO(Long id, String text, String questionType, List<AnswerDTO> answerDTOList, Long testId) {
        this(id, text, questionType, answerDTOList);
        this.testId = testId;
    }
}
