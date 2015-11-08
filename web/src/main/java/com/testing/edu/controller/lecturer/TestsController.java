package com.testing.edu.controller.lecturer;

import com.testing.edu.controller.admin.StatisticController;
import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.TestDTO;
import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.Tests;
import com.testing.edu.entity.User;
import com.testing.edu.service.LecturersService;
import com.testing.edu.service.StatisticService;
import com.testing.edu.service.TestsService;
import com.testing.edu.service.security.SecurityUserDetailsService;
import com.testing.edu.service.utils.ListToPageTransformer;
import com.testing.edu.service.utils.TypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/lecturer/tests/")
public class TestsController {

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private LecturersService lecturersService;

    @Autowired
    private TestsService testsService;

    @RequestMapping(value = "{pageNumber}/{itemsPerPage}/{sortCriteria}/{sortOrder}", method = RequestMethod.GET)
    public PageDTO<TestDTO> pageSubjectByLecturerWithSearch(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                            @PathVariable String sortCriteria, @PathVariable String sortOrder,
                                                            TestDTO searchData,
                                                            @AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        Map<String, String> searchDataMap = TypeConverter.ObjectToMap(searchData);
        User userous = statisticService.employeeExist(user.getUsername());
        Lecturers lecturer = lecturersService.findByUser(userous);
        ListToPageTransformer<Tests> queryResult = testsService.getTestsBySearchAndPagination(
                pageNumber,
                itemsPerPage, searchDataMap,
                sortCriteria,
                sortOrder,
                lecturer
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
    public PageDTO<TestDTO> getSubjectPage(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                              @AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        return pageSubjectByLecturerWithSearch(pageNumber, itemsPerPage, null, null, null, user);
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
