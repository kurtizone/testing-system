package com.testing.edu.dto.admin;


import com.testing.edu.entity.Answers;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TestDTO {
    private Long id;
    private String title;
    private String type;
    private String subject;
    private Integer maxGrade;
    private Boolean avaible;
    private Long subjectId;
    private List<QuestionDTO> listQuestAns;
    private Boolean hasResult;
    private Integer time;

    public TestDTO() {

    }

    public TestDTO(Long id, String title, String type, String subject, Integer maxGrade, Boolean avaible) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.subject = subject;
        this.maxGrade = maxGrade;
        this.avaible = avaible;
    }

    public TestDTO(Long id, String title, String type, String subject, Integer maxGrade, Integer time, Boolean avaible) {
        this(id, title, type, subject, maxGrade, avaible);
        this.time = time;
    }

    public TestDTO(Long id, String title, String type, String subject, Integer maxGrade, Integer time, Boolean avaible, Boolean hasResult) {
        this(id, title, type, subject, maxGrade, time, avaible);
        this.hasResult = hasResult;
    }

    public TestDTO(Long id, String title, String type, String subject, Integer maxGrade, Boolean avaible, Long subjectId) {
        this(id, title, type, subject, maxGrade, avaible);
        this.subjectId = subjectId;
    }

    public TestDTO(Long id, String title, String type, String subject, Integer maxGrade, Integer time, Boolean avaible, Long subjectId) {
        this(id, title, type, subject, maxGrade, time, avaible);
        this.subjectId = subjectId;
    }

    public TestDTO(Long id, String title, String type, String subject, Integer maxGrade, Integer time, Boolean avaible, Long subjectId,
                   List<QuestionDTO> listQuestAns) {
        this(id, title, type, subject, maxGrade, time, avaible, subjectId);
        this.listQuestAns = listQuestAns;
    }

    public TestDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
