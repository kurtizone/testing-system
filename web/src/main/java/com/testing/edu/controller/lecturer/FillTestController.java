package com.testing.edu.controller.lecturer;


import com.testing.edu.dto.admin.AnswerDTO;
import com.testing.edu.dto.admin.QuestionDTO;
import com.testing.edu.dto.admin.TestDTO;
import com.testing.edu.entity.Answers;
import com.testing.edu.entity.Questions;
import com.testing.edu.entity.Tests;
import com.testing.edu.entity.enumeration.QuestionType;
import com.testing.edu.service.QuestionsService;
import com.testing.edu.service.TestsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/lecturer/fill-tests/")
public class FillTestController {

    private final Logger logger = Logger.getLogger(FillTestController.class);

    private static Double gradeForQuest = 0d;

    @Autowired
    private TestsService testsService;

    @Autowired
    private QuestionsService questionsService;


    /**
     * Add question
     * @param questionDTO object with question data
     * @return a response body with http status {@literal OK} if question
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "add/question", method = RequestMethod.POST)
    public ResponseEntity addQuestion(@RequestBody QuestionDTO questionDTO) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        Tests test = testsService.findById(questionDTO.getTestId());
        gradeForQuest = (double) test.getMaxGrade() / (test.getQuestionses().size() + 1);
        try {
            questionsService.addQuestion(
                    questionDTO.getText(),
                    questionDTO.getQuestionType(),
                    toListWithoutIdFromAnswerDTO(questionDTO.getAnswerDTOList(), gradeForQuest),
                    test,
                    gradeForQuest
            );
        } catch (Exception e) {
            logger.error("Got exeption while add question ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Edit question
     * @param questionDTO object with question data
     * @return a response body with http status {@literal OK} if question
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "edit/question/{questionId}", method = RequestMethod.POST)
    public ResponseEntity editQuestion(@RequestBody QuestionDTO questionDTO,
                                   @PathVariable Long questionId) {
        HttpStatus httpStatus = HttpStatus.OK;
        Tests test = questionsService.findById(questionId).getTests();
        gradeForQuest = (double) test.getMaxGrade() / (test.getQuestionses().size());
        try {
            questionsService.editQuestion(
                    questionId,
                    questionDTO.getText(),
                    questionDTO.getQuestionType(),
                    toListWithIdFromAnswerDTO(questionDTO.getAnswerDTOList(), gradeForQuest),
                    gradeForQuest
            );
        } catch (Exception e) {
            logger.error("Got exeption while editing question ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Delete question
     * @param questionId Long id of question
     * @return a response body with http status {@literal OK} if question
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "delete/question/{questionId}", method = RequestMethod.DELETE)
    public ResponseEntity removeQuestion(@PathVariable Long questionId) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            questionsService.removeQuestion(questionId);
        } catch (Exception e) {
            logger.error("Got exeption while remove question ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Delete answer
     * @param answerId Long id of answer
     * @return a response body with http status {@literal OK} if answer
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "delete/answer/{answerId}", method = RequestMethod.DELETE)
    public ResponseEntity removeAnswer(@PathVariable Long answerId) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            questionsService.removeAnswer(answerId);
        } catch (Exception e) {
            logger.error("Got exeption while remove question ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    @RequestMapping(value = "get/question/{id}")
    public QuestionDTO getQuestion(@PathVariable("id") Long id) {
        Questions question = questionsService.findById(id);
        QuestionDTO questionDTO = new QuestionDTO(
                question.getId(),
                question.getText(),
                question.getQuestionType().name(),
                question.getAnswerses().stream()
                        .map(answers -> new AnswerDTO(answers.getId(), answers.getText(), answers.getGrade()))
                        .collect(Collectors.toList()),
                question.getTests().getId()
        );
        return questionDTO;

    }


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
                test.getTime(),
                test.getAvaible(),
                test.getSubject().getId(),
                listQuestAns);
        return testDTO;

    }

    public static List<Answers> toListWithIdFromAnswerDTO(List<AnswerDTO> list, Double gradeForQuestion){
        int correctAnswers = 0;
        for (AnswerDTO answersDTO : list) {
            if(answersDTO.getCorrect().equals(true)){
                correctAnswers++;
            }
        }
        Double grade = gradeForQuestion / correctAnswers;
        return list.stream()
                .map(answerDTO -> new Answers(!answerDTO.getId().equals(-1l) ? answerDTO.getId() : -1, answerDTO.getText(), (answerDTO.getCorrect() ? grade : new Double(0))))
                .collect(Collectors.toList());
    }

    public static List<Answers> toListWithoutIdFromAnswerDTO(List<AnswerDTO> list, Double gradeForQuestion){
        int correctAnswers = 0;
        for (AnswerDTO answersDTO : list) {
            if(answersDTO.getCorrect().equals(true)){
                correctAnswers++;
            }
        }
        Double grade = gradeForQuestion / correctAnswers;
        return list.stream()
                .map(answerDTO -> new Answers(answerDTO.getText(), (answerDTO.getCorrect() ? grade : new Double(0))))
                .collect(Collectors.toList());
    }



}
