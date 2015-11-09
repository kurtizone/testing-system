package com.testing.edu.controller.lecturer;


import com.testing.edu.dto.admin.AnswerDTO;
import com.testing.edu.dto.admin.QuestionDTO;
import com.testing.edu.dto.admin.TestDTO;
import com.testing.edu.entity.Answers;
import com.testing.edu.entity.Questions;
import com.testing.edu.entity.Tests;
import com.testing.edu.entity.enumeration.QuestionType;
import com.testing.edu.service.TestsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/lecturer/fill-tests/")
public class FillTestController {

    private final Logger logger = Logger.getLogger(FillTestController.class);

    @Autowired
    private TestsService testsService;

    @RequestMapping(value = "get/{id}")
    public TestDTO getTest(@PathVariable("id") Long id) {
        Tests test = testsService.findById(id);
        List<QuestionDTO> listQuestAns = new ArrayList<>();
        for (Questions questions : test.getQuestionses()){
            listQuestAns.add(new QuestionDTO(
                    questions.getId(),
                    questions.getText(),
                    questions.getQuestionType().name(),
                    questions.getAnswerses().stream()
                    .map(answers -> new AnswerDTO(answers.getId(), answers.getText(), answers.getGrade()))
                    .collect(Collectors.toList())
            ));
        }
        TestDTO testDTO = new TestDTO(
                test.getId(),
                test.getTitle(),
                test.getType().name(),
                test.getSubject().getTitle(),
                test.getMaxGrade(),
                test.getAvaible(),
                test.getSubject().getId(),
                listQuestAns);
        return testDTO;

    }



}
