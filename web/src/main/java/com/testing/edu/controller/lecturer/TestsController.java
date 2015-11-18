package com.testing.edu.controller.lecturer;

import com.testing.edu.controller.admin.StatisticController;
import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.SubjectDTO;
import com.testing.edu.dto.admin.TestDTO;
import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.Subject;
import com.testing.edu.entity.Tests;
import com.testing.edu.entity.User;
import com.testing.edu.service.LecturersService;
import com.testing.edu.service.StatisticService;
import com.testing.edu.service.SubjectService;
import com.testing.edu.service.TestsService;
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
@RequestMapping(value = "/lecturer/tests/")
public class TestsController {

    private static final Logger logger = Logger.getLogger(TestsController.class);

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private LecturersService lecturersService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TestsService testsService;

    /**
     * Add test
     * @param testDTO object with test data
     * @return a response body with http status {@literal OK} if test
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity addTest(@RequestBody TestDTO testDTO) {

        HttpStatus httpStatus = HttpStatus.CREATED;
        try {
            testsService.addTest(
                    testDTO.getTitle(),
                    testDTO.getType(),
                    testDTO.getMaxGrade(),
                    testDTO.getTime(),
                    testDTO.getAvaible(),
                    testDTO.getSubjectId()
            );
        } catch (Exception e) {
            logger.error("Got exeption while add test ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Edit test
     * @param testDTO object with test data
     * @return a response body with http status {@literal OK} if test
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "edit/{testId}", method = RequestMethod.POST)
    public ResponseEntity editTest(@RequestBody TestDTO testDTO,
                                      @PathVariable Long testId) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            testsService.editTest(
                    testId,
                    testDTO.getTitle(),
                    testDTO.getType(),
                    testDTO.getMaxGrade(),
                    testDTO.getTime(),
                    testDTO.getAvaible(),
                    testDTO.getSubjectId()
            );
        } catch (Exception e) {
            logger.error("Got exeption while editing test ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Delete test
     * @param testId Long id of test
     * @return a response body with http status {@literal OK} if test
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "delete/{testId}", method = RequestMethod.DELETE)
    public ResponseEntity removeTest(@PathVariable Long testId) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            testsService.removeTest(testId);
        } catch (Exception e) {
            logger.error("Got exeption while remove test ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
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
                test.getTime(),
                test.getAvaible(),
                test.getSubject().getId()
        );
        return testDTO;
    }

    @RequestMapping(value = "get/subjects")
    public List<SubjectDTO> getSubjects(@AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        User userous = statisticService.employeeExist(user.getUsername());
        Lecturers lecturer = lecturersService.findByUser(userous);
        return lecturer.getSubjects().stream()
                .map(subject -> new SubjectDTO(subject.getId(), subject.getTitle()))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "get/subjects/tests/{subjectId}")
    public List<TestDTO> getTests(@PathVariable Long subjectId) {
        Subject subject = subjectService.findById(subjectId);
        return subject.getTestses().stream()
                .map(test -> new TestDTO(test.getId(), test.getTitle()))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "{pageNumber}/{itemsPerPage}/{sortCriteria}/{sortOrder}", method = RequestMethod.GET)
    public PageDTO<TestDTO> pageTestByLecturerWithSearch(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                            @PathVariable String sortCriteria, @PathVariable String sortOrder,
                                                            TestDTO searchData,
                                                            @AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        Map<String, String> searchDataMap = TypeConverter.ObjectToMap(searchData);
        User userous = statisticService.employeeExist(user.getUsername());
        Lecturers lecturer = lecturersService.findByUser(userous);
        searchDataMap.put("lecturer", lecturer.getId().toString());
        ListToPageTransformer<Tests> queryResult = testsService.getTestsBySearchAndPagination(
                pageNumber,
                itemsPerPage, searchDataMap,
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
        return pageTestByLecturerWithSearch(pageNumber, itemsPerPage, null, null, null, user);
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
                    test.getTime(),
                    test.getAvaible()
            ));
        }
        return resultList;
    }
}
