package com.testing.edu.controller.lecturer;

import com.testing.edu.controller.admin.GroupsController;
import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.GroupDTO;
import com.testing.edu.dto.admin.SubjectDTO;
import com.testing.edu.dto.admin.TestDTO;
import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.Subject;
import com.testing.edu.entity.Tests;
import com.testing.edu.entity.User;
import com.testing.edu.service.LecturersService;
import com.testing.edu.service.StatisticService;
import com.testing.edu.service.SubjectService;
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

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/lecturer/subjects/")
public class SubjectController {

    private final Logger logger = Logger.getLogger(SubjectController.class);

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private LecturersService lecturersService;

    @Autowired
    private StatisticService statisticService;


    /**
     * Get list of groups for this subject with id
     * @param id Integer id of subject
     * @return subjectDTO
     */
    @RequestMapping(value = "get-list-groups/{id}")
    public List<GroupDTO> getGroups(@PathVariable("id") Long id) {
        Subject subject = subjectService.findById(id);
        return GroupsController.toGroupDtoFromList(new ArrayList<>(subject.getGroupses()));
    }

    /**
     * Get list of groups for this subject with id
     * @param id Integer id of subject
     * @return subjectDTO
     */
    @RequestMapping(value = "get-list-tests/{id}")
    public List<TestDTO> getTests(@PathVariable("id") Long id) {
        Subject subject = subjectService.findById(id);
        return TestsController.toTestDtoFromList(new ArrayList<>(subject.getTestses()));
    }

    /**
     * Build page by SortCriteria, SortOrder and Searching data
     * @param pageNumber
     * @param itemsPerPage
     * @param sortCriteria
     * @param sortOrder
     * @param searchData
     * @return
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}/{sortCriteria}/{sortOrder}", method = RequestMethod.GET)
    public PageDTO<SubjectDTO> pageSubjectByLecturerWithSearch(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                     @PathVariable String sortCriteria, @PathVariable String sortOrder,
                                                     SubjectDTO searchData,
                                                     @AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        Map<String, String> searchDataMap = TypeConverter.ObjectToMap(searchData);
        User userous = statisticService.employeeExist(user.getUsername());
        Lecturers lecturer = lecturersService.findByUser(userous);
        ListToPageTransformer<Subject> queryResult = subjectService.getSubjectBySearchAndPagination(
                pageNumber,
                itemsPerPage, searchDataMap,
                sortCriteria,
                sortOrder,
                lecturer
        );
        List<SubjectDTO> content = toCounterTypeDtoFromList(queryResult.getContent());
        return new PageDTO(queryResult.getTotalItems(), content);
    }

    /**
     * Build page without sorting, ordering and searching data
     * @param pageNumber
     * @param itemsPerPage
     * @return
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}", method = RequestMethod.GET)
    public PageDTO<SubjectDTO> getSubjectPage(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                  @AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        return pageSubjectByLecturerWithSearch(pageNumber, itemsPerPage, null, null, null, user);
    }

    public List<SubjectDTO> toCounterTypeDtoFromList(List<Subject> list){
        List<SubjectDTO> resultList = new ArrayList<>();
        for (Subject subject : list) {
            resultList.add(new SubjectDTO(
                    subject.getId(),
                    subject.getTitle(),
                    subject.getMultiplier().toString(),
                    subject.getHours(),
                    subjectService.countOfGroups(subject.getId()),
                    subjectService.countOfTests(subject.getId())
            ));
        }
        return resultList;
    }


}
