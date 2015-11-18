package com.testing.edu.controller.lecturer;


import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.ResultDTO;
import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.Result;
import com.testing.edu.entity.User;
import com.testing.edu.service.LecturersService;
import com.testing.edu.service.ResultService;
import com.testing.edu.service.StatisticService;
import com.testing.edu.service.security.SecurityUserDetailsService;
import com.testing.edu.service.utils.ListToPageTransformer;
import com.testing.edu.service.utils.TypeConverter;
import org.apache.log4j.Logger;
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
@RequestMapping(value = "/lecturer/results/")
public class LecturerResultsController {

    private static final Logger logger = Logger.getLogger(LecturerResultsController.class);

    @Autowired
    private ResultService resultService;

    @Autowired
    private LecturersService lecturersService;

    @Autowired
    private StatisticService statisticService;

    /**
     * Build page with sorting, ordering and searching data
     * @param pageNumber
     * @param itemsPerPage
     * @param sortCriteria
     * @param sortOrder
     * @param searchData
     * @param user
     * @return
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}/{sortCriteria}/{sortOrder}", method = RequestMethod.GET)
    public PageDTO<ResultDTO> pageResultsByStudentWithSearch(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                             @PathVariable String sortCriteria, @PathVariable String sortOrder,
                                                             ResultDTO searchData,
                                                             @AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        Map<String, String> searchDataMap = TypeConverter.ObjectToMap(searchData);
        User userous = statisticService.employeeExist(user.getUsername());
        Lecturers lecturer = lecturersService.findByUser(userous);
        searchDataMap.put("lecturer", lecturer.getId().toString());
        ListToPageTransformer<Result> queryResult = resultService.getResultByStudentBySearchAndPagination(
                pageNumber,
                itemsPerPage,
                searchDataMap,
                sortCriteria,
                sortOrder
        );
        List<ResultDTO> content = toResultDtoFromList(queryResult.getContent());
        return new PageDTO(queryResult.getTotalItems(), content);
    }

    /**
     * Build page without sorting, ordering and searching data
     * @param pageNumber
     * @param itemsPerPage
     * @param user
     * @return
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}", method = RequestMethod.GET)
    public PageDTO<ResultDTO> getResultStudentPage(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
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
                    result.getTestTitle(),
                    result.getTests().getType().name(),
                    result.getMark(),
                    result.getMaxGrade()
            ));
        }
        return resultList;
    }


}
