package com.testing.edu.controller.student;


import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.*;
import com.testing.edu.entity.*;
import com.testing.edu.entity.enumeration.QuestionType;
import com.testing.edu.service.*;
import com.testing.edu.service.security.SecurityUserDetailsService;
import com.testing.edu.service.utils.ListToPageTransformer;
import com.testing.edu.service.utils.TypeConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/student/tests/")
public class StudentTestsController {

    private static final Logger logger = Logger.getLogger(StudentResultsController.class);

    private static Double totalMark;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private TestsService testsService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private QuestionsService questionsService;


    /**
     * save result of test
     * @param testDTO object with student data
     * @return a response body with http status {@literal OK} if student
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "save/result", method = RequestMethod.POST)
    public ResponseEntity addStudent(@RequestBody TestDTO testDTO, @AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        totalMark = 0d;
        User userous = statisticService.employeeExist(user.getUsername());
        Tests test = testsService.findById(testDTO.getId());
        Result result = new Result(
                studentsService.findByUser(userous),
                test,
                testDTO.getTitle(),
                countGradeTest(testDTO.getListQuestAns()),
                testDTO.getMaxGrade()
        );
        try {

            resultService.saveResult(result);

        } catch (Exception e) {
            logger.error("Got exeption while add result ", e);
            httpStatus = HttpStatus.CONFLICT;
        }
        /*return new ResponseEntity(new ResultDTO(result.getStudents().getLastName(), result.getStudents().getFirstName(),
                result.getStudents().getMiddleName(), result.getMark(), result.getMaxGrade()),
                httpStatus);*/
        return new ResponseEntity(httpStatus);
    }


    /**
     * Get test with id
     * @param id Integer id of test
     * @return testDTO
     */
    @RequestMapping(value = "get/{id}")
    public TestDTO getTest(@PathVariable("id") Long id) {
        Tests test = testsService.findById(id);
        TestDTO testDTO = new TestDTO(
                test.getId(),
                test.getTitle(),
                test.getType().name(),
                test.getSubject().getTitle(),
                test.getMaxGrade(),
                test.getAvaible(),
                test.getSubject().getId()
        );
        return testDTO;
    }

    @RequestMapping(value = "get/questions/{id}")
    public TestDTO getTestWithQuestions(@PathVariable("id") Long id) {
        Tests test = testsService.findById(id);
        List<QuestionDTO> listQuestAns = new ArrayList<>();
        for (Questions questions : test.getQuestionses()){
            listQuestAns.add(new QuestionDTO(
                    questions.getId(),
                    questions.getText(),
                    questions.getQuestionType().name(),
                    questions.getAnswerses().stream()
                            .map(answers -> new AnswerDTO(answers.getId(), answers.getText()))
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


    @RequestMapping(value = "{pageNumber}/{itemsPerPage}/{sortCriteria}/{sortOrder}", method = RequestMethod.GET)
    public PageDTO<TestDTO> pageTestByStudentWithSearch(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                         @PathVariable String sortCriteria, @PathVariable String sortOrder,
                                                         TestDTO searchData,
                                                         @AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        Map<String, String> searchDataMap = TypeConverter.ObjectToMap(searchData);
        User userous = statisticService.employeeExist(user.getUsername());
        Students student = studentsService.findByUser(userous);
        searchDataMap.put("group", student.getGroups().getId().toString());
        ListToPageTransformer<Tests> queryResult = testsService.getTestsBySearchAndPagination(
                pageNumber,
                itemsPerPage,
                searchDataMap,
                sortCriteria,
                sortOrder
        );
        List<TestDTO> content = toTestDtoFromList(queryResult.getContent());
        return new PageDTO(queryResult.getTotalItems(), content);
    }

    /**
     * Build page without sorting, ordering and searching data
     * @param pageNumber
     * @param itemsPerPage
     * @return
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}", method = RequestMethod.GET)
    public PageDTO<TestDTO> getTestPage(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                        @AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        return pageTestByStudentWithSearch(pageNumber, itemsPerPage, null, null, null, user);
    }

    public static List<TestDTO> toTestDtoFromList(List<Tests> list){
        List<TestDTO> resultList = new ArrayList<>();
        for (Tests test : list) {
            resultList.add(new TestDTO(
                    test.getId(),
                    test.getTitle(),
                    test.getType().name(),
                    test.getSubject().getTitle(),
                    test.getMaxGrade(),
                    test.getAvaible()
            ));
        }
        return resultList;
    }

    public Double countGradeTest(List<QuestionDTO> questionDTOList){
        for (QuestionDTO questionDTO : questionDTOList){
            Double mark = 0d;
            Questions question = questionsService.findById(questionDTO.getId());
            mark = question.getQuestionType().equals(QuestionType.MULTI) ?
                    countGradeMulti(question, questionDTO) : countGradeOne(question, questionDTO);
            totalMark += mark;
        }
        return totalMark;

    }

    public static Double countGradeMulti(Questions question, QuestionDTO questionDTO){
        Double mark = 0d;
        Integer countCorrect = 0;
        Integer divider = 0;
        for (AnswerDTO answerDTO: questionDTO.getAnswerDTOList()){
            if(answerDTO.getCorrect().equals(true)){
                countCorrect++;
            } else break;
        }
        if (countCorrect.equals(question.getAnswerses().size())) {
            return mark;
        }
        for (Answers answer : question.getAnswerses()){
            for(AnswerDTO answerDTO: questionDTO.getAnswerDTOList()){
                if(answer.getId().equals(answerDTO.getId())){
                    if(answerDTO.getCorrect().equals(true)) {
                        if(answer.getGrade().equals(0d)) {
                            divider += 2;
                        } else {
                            mark += answer.getGrade();
                        }
                    }
                }
            }
        }
        if (divider > 0) {
            return mark / divider;
        }
        return mark;
    }

    public static Double countGradeOne(Questions question, QuestionDTO questionDTO){
        Double mark = 0d;
        for (Answers answer : question.getAnswerses()){
            for(AnswerDTO answerDTO: questionDTO.getAnswerDTOList()){
                if(answer.getId().equals(answerDTO.getId()) && answerDTO.getCorrect().equals(true)){
                    mark += answer.getGrade();
                    break;
                }
            }
        }
        return mark;
    }

}
