package com.testing.edu.controller.student;

import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.ResultDTO;
import com.testing.edu.dto.admin.TestDTO;
import com.testing.edu.entity.*;
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

@RestController
@RequestMapping(value = "/student/results/")
public class StudentResultsController {

    private static final Logger logger = Logger.getLogger(StudentResultsController.class);

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private TestsService testsService;


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
     * Get result with id
     * @param id Long id of result
     * @return testDTO
     */
    @RequestMapping(value = "get/{id}")
    public ResultDTO getResultByStudent(@PathVariable("id") Long id) {
        Result result = resultService.findById(id);
        ResultDTO resultDTO = new ResultDTO(
                result.getId(),
                result.getStudents().getLastName(),
                result.getStudents().getFirstName(),
                result.getStudents().getMiddleName(),
                result.getStudents().getGroups().getTitle(),
                result.getTests().getTitle(),
                result.getMark(),
                result.getMaxGrade()
        );
        return resultDTO;
    }

    @RequestMapping(value = "{pageNumber}/{itemsPerPage}/{sortCriteria}/{sortOrder}", method = RequestMethod.GET)
    public PageDTO<TestDTO> pageResultsByStudentWithSearch(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                            @PathVariable String sortCriteria, @PathVariable String sortOrder,
                                                            TestDTO searchData,
                                                            @AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        Map<String, String> searchDataMap = TypeConverter.ObjectToMap(searchData);
        User userous = statisticService.employeeExist(user.getUsername());
        Students student = studentsService.findByUser(userous);
        ListToPageTransformer<Result> queryResult = resultService.getResultByStudentBySearchAndPagination(
                pageNumber,
                itemsPerPage, searchDataMap,
                sortCriteria,
                sortOrder,
                student
        );
        List<ResultDTO> content = toResultDtoFromList(queryResult.getContent());
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
        return pageResultsByStudentWithSearch(pageNumber, itemsPerPage, null, null, null, user);
    }
    
    public static List<ResultDTO> toResultDtoFromList(List<Result> list){
        List<ResultDTO> resultList = new ArrayList<>();
        for (Result result : list) {
            resultList.add(new ResultDTO(
                    result.getId(),
                    result.getStudents().getLastName(),
                    result.getStudents().getFirstName(),
                    result.getStudents().getMiddleName(),
                    result.getStudents().getGroups().getTitle(),
                    result.getTests().getSubject().getTitle(),
                    result.getMark(),
                    result.getMaxGrade()
            ));
        }
        return resultList;
    }
}
