package com.testing.edu.controller.student;


import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.ResultDTO;
import com.testing.edu.dto.admin.SubjectDTO;
import com.testing.edu.dto.admin.TestDTO;
import com.testing.edu.entity.*;
import com.testing.edu.service.ResultService;
import com.testing.edu.service.StatisticService;
import com.testing.edu.service.StudentsService;
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
@RequestMapping(value = "/student/tests/")
public class StudentTestsController {

    private static final Logger logger = Logger.getLogger(StudentResultsController.class);

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private TestsService testsService;


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
}
