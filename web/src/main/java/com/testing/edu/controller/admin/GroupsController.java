package com.testing.edu.controller.admin;

import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.GroupDTO;
import com.testing.edu.dto.admin.StudentDTO;
import com.testing.edu.dto.admin.SubjectDTO;
import com.testing.edu.entity.Groups;
import com.testing.edu.entity.Subject;
import com.testing.edu.service.GroupsService;
import com.testing.edu.service.SubjectService;
import com.testing.edu.service.utils.ListToPageTransformer;
import com.testing.edu.service.utils.TypeConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/admin/groups/")
public class GroupsController {

    private final Logger logger = Logger.getLogger(GroupsController.class);

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private SubjectService subjectService;

    /**
     * Add group
     * @param groupDTO object with group data
     * @return a response body with http status {@literal OK} if group
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity addGroup(@RequestBody GroupDTO groupDTO) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        try {
            groupsService.addGroup(
                    groupDTO.getTitle(),
                    groupDTO.getGrade(),
                    groupDTO.getDegree(),
                    groupDTO.getStudyForm()
            );
        } catch (Exception e) {
            logger.error("Got exeption while add group ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Edit group
     * @param groupDTO object with group data
     * @return a response body with http status {@literal OK} if group
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
    public ResponseEntity editGroup(@RequestBody GroupDTO groupDTO,
                                       @PathVariable Long id) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            groupsService.editGroup(
                    id,
                    groupDTO.getTitle(),
                    groupDTO.getGrade(),
                    groupDTO.getDegree(),
                    groupDTO.getStudyForm()
            );
        } catch (Exception e) {
            logger.error("Got exeption while editing group ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Delete group
     * @param id Long id of group
     * @return a response body with http status {@literal OK} if group
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeGroup(@PathVariable Long id) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            groupsService.removeGroup(id);
        } catch (Exception e) {
            logger.error("Got exeption while remove group ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }



    /**
     * Get group with id
     * @param id Long id of group
     * @return subjectDTO
     */
    @RequestMapping(value = "get/{id}")
    public GroupDTO getGroup(@PathVariable("id") Long id) {
        Groups groups = groupsService.findById(id);
        GroupDTO groupDTO = new GroupDTO(
                groups.getId(),
                groups.getTitle(),
                groups.getGrade(),
                groups.getDegree().name(),
                groups.getStudyForm().name()
        );
        return groupDTO;
    }

    /**
     * Get subject with id
     * @param id Integer id of subject
     * @return subjectDTO
     */
    @RequestMapping(value = "get/{id}/subjects")
    public List<SubjectDTO> getListOfSubjects(@PathVariable("id") Long id) {
        Groups group = groupsService.findById(id);
        return group.getSubjects().stream()
                .map(subject -> new SubjectDTO(
                        subject.getId(),
                        subject.getTitle(),
                        subject.getMultiplier().toString(),
                        subject.getHours(),
                        subjectService.countOfGroups(subject.getId()),
                        subjectService.countOfTests(subject.getId())
                )).collect(Collectors.toList());
    }

    /**
     * Get subject with id
     * @param id Integer id of subject
     * @return subjectDTO
     */
    @RequestMapping(value = "get/{id}/students")
    public List<StudentDTO> getListOfStudents(@PathVariable("id") Long id) {
        Groups group = groupsService.findById(id);
        return group.getStudentses().stream()
                .map(student -> new StudentDTO(
                        student.getId(),
                        student.getLastName(),
                        student.getFirstName(),
                        student.getMiddleName(),
                        student.getNumberGradebook()
                )).collect(Collectors.toList());
    }

    /**
     * Get all subjects
     * @return subjectDTO
     */
    @RequestMapping(value = "get/subjects/{id}")
    public List<SubjectDTO> getSubjects(@PathVariable("id") Long id) {
        Groups group = groupsService.findById(id);
        Set<Subject> subjectSet = new HashSet<>(subjectService.getAllSubjects());
        subjectSet.removeAll(group.getSubjects());
        return subjectSet.stream()
                .map(subject -> new SubjectDTO(
                        subject.getId(),
                        subject.getTitle()
                )).collect(Collectors.toList());
    }



    /**
     * @return a response body with http status {@literal OK} if group
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "add/subject/{id}", method = RequestMethod.POST)
    public ResponseEntity addSubject(@RequestBody SubjectDTO subjectDTO, @PathVariable Long id) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            groupsService.addSubject(
                    id,
                    subjectDTO.getId()
            );
        } catch (Exception e) {
            logger.error("Got exeption while adding subject for group ",e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
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
    public PageDTO<GroupDTO> pageGroupWithSearch(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                       @PathVariable String sortCriteria, @PathVariable String sortOrder,
                                                       GroupDTO  searchData) {
        Map<String, String> searchDataMap = TypeConverter.ObjectToMap(searchData);
        ListToPageTransformer<Groups> queryResult = groupsService.getGroupBySearchAndPagination(
                pageNumber,
                itemsPerPage,
                searchDataMap,
                sortCriteria,
                sortOrder
        );
        List<GroupDTO> content = toGroupDtoFromList(queryResult.getContent());
        return new PageDTO(queryResult.getTotalItems(), content);
    }

    /**
     * Build page without sorting, ordering and searching data
     * @param pageNumber
     * @param itemsPerPage
     * @return
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}", method = RequestMethod.GET)
    public PageDTO<GroupDTO> getGroupPage(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage) {
        return pageGroupWithSearch(pageNumber, itemsPerPage, null, null, null);
    }

    /**
     * Convert list of counter types to list CounterTypeDTO
     * @param list
     * @return
     */
    public static List<GroupDTO> toGroupDtoFromList(List<Groups> list){
        List<GroupDTO> resultList = new ArrayList<>();
        for (Groups group : list) {
            resultList.add(new GroupDTO(
                    group.getId(),
                    group.getTitle(),
                    group.getGrade(),
                    group.getDegree().name(),
                    group.getStudyForm().name()
            ));
        }
        return resultList;
    }

}
