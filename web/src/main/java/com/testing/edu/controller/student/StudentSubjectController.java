package com.testing.edu.controller.student;

import com.testing.edu.controller.admin.StatisticController;
import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.LecturerDTO;
import com.testing.edu.dto.admin.SubjectDTO;
import com.testing.edu.entity.Groups;
import com.testing.edu.entity.Students;
import com.testing.edu.entity.Subject;
import com.testing.edu.entity.User;
import com.testing.edu.service.GroupsService;
import com.testing.edu.service.StatisticService;
import com.testing.edu.service.StudentsService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/student/subjects/")
public class StudentSubjectController {

    private final Logger logger = Logger.getLogger(StudentSubjectController.class);

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private GroupsService groupsService;



    @RequestMapping(value = "get/lecturers/{subjectId}")
    public List<LecturerDTO> getLecturers(@PathVariable Long subjectId) {
        Subject subject = subjectService.findById(subjectId);
        return subject.getLecturerses().stream()
                .map(lecturer -> new LecturerDTO(
                        lecturer.getId(),
                        lecturer.getLastName(),
                        lecturer.getFirstName(),
                        lecturer.getMiddleName(),
                        lecturer.getAcademicStatus().name(),
                        lecturer.getDegree().name()
                ))
                .collect(Collectors.toList());
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
        Groups group = groupsService.findById(studentsService.findByUser(userous).getGroups().getId());
        ListToPageTransformer<Subject> queryResult = subjectService.getSubjectBySearchAndPagination(
                pageNumber,
                itemsPerPage,
                searchDataMap,
                sortCriteria,
                sortOrder,
                group
        );
        List<SubjectDTO> content = toSubjectDtoFromList(queryResult.getContent());
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

    public List<SubjectDTO> toSubjectDtoFromList(List<Subject> list){
        List<SubjectDTO> resultList = new ArrayList<>();
        for (Subject subject : list) {
            resultList.add(new SubjectDTO(
                    subject.getId(),
                    subject.getTitle(),
                    subject.getMultiplier().toString(),
                    subject.getHours(),
                    subjectService.countOfTests(subject.getId())
            ));
        }
        return resultList;
    }



}
