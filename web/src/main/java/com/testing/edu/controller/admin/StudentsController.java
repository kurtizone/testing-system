package com.testing.edu.controller.admin;

import com.testing.edu.dto.PageDTO;
import com.testing.edu.dto.admin.GroupDTO;
import com.testing.edu.dto.admin.StudentDTO;
import com.testing.edu.dto.admin.SubjectDTO;
import com.testing.edu.entity.Groups;
import com.testing.edu.entity.Students;
import com.testing.edu.entity.Subject;
import com.testing.edu.service.GroupsService;
import com.testing.edu.service.StudentsService;
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
@RequestMapping(value = "/admin/students/")
public class StudentsController {
    private final Logger logger = Logger.getLogger(StudentsController.class);

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private SubjectService subjectService;

    /**
     * Add student
     *
     * @param studentDTO object with student data
     * @return a response body with http status {@literal OK} if student
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity addStudent(@RequestBody StudentDTO studentDTO) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        try {
            studentsService.addStudent(
                    studentDTO.getLastName(),
                    studentDTO.getFirstName(),
                    studentDTO.getMiddleName(),
                    studentDTO.getNumberGradebook(),
                    groupsService.findById(studentDTO.getGroupId()),
                    studentDTO.getUsername(),
                    studentDTO.getEmail(),
                    studentDTO.getPhone()
            );
        } catch (Exception e) {
            logger.error("Got exeption while add student ", e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Edit student
     *
     * @param studentDTO object with student data
     * @return a response body with http status {@literal OK} if student
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
    public ResponseEntity editStudent(@RequestBody StudentDTO studentDTO,
                                      @PathVariable Long id) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            studentsService.editStudent(
                    id,
                    studentDTO.getLastName(),
                    studentDTO.getFirstName(),
                    studentDTO.getMiddleName(),
                    studentDTO.getNumberGradebook(),
                    groupsService.findById(studentDTO.getGroupId()),
                    studentDTO.getUsername(),
                    studentDTO.getEmail(),
                    studentDTO.getPhone(),
                    studentDTO.getPassword()
            );
        } catch (Exception e) {
            logger.error("Got exeption while editing student ", e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Delete student
     *
     * @param id Long id of student
     * @return a response body with http status {@literal OK} if student
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeStudent(@PathVariable Long id) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            studentsService.removeStudent(id);
        } catch (Exception e) {
            logger.error("Got exeption while remove student ", e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Get student with id
     *
     * @param id Long id of student
     * @return subjectDTO
     */
    @RequestMapping(value = "get/{id}")
    public StudentDTO getStudent(@PathVariable("id") Long id) {
        Students students = studentsService.findById(id);
        StudentDTO studentDTO = new StudentDTO(
                students.getId(),
                students.getLastName(),
                students.getFirstName(),
                students.getMiddleName(),
                students.getNumberGradebook(),
                students.getGroups().getId(),
                students.getGroups().getTitle(),
                students.getUser().getUsername(),
                students.getUser().getEmail(),
                students.getUser().getPhone()
        );
        return studentDTO;
    }

    /**
     * Get all groups
     *
     * @return subjectDTO
     */
    @RequestMapping(value = "get/groups")
    public List<GroupDTO> getGroups() {
        return groupsService.getAllGroups().stream()
                .map(group -> new GroupDTO(
                        group.getId(),
                        group.getTitle()
                )).collect(Collectors.toList());
    }

    /**
     * Get subject with id
     * @param id Integer id of subject
     * @return subjectDTO
     */
    @RequestMapping(value = "get/{id}/subjects")
    public List<SubjectDTO> getListOfSubjects(@PathVariable("id") Long id) {
        Students student= studentsService.findById(id);
        Groups group = student.getGroups();
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
     * Build page by SortCriteria, SortOrder and Searching data
     *
     * @param pageNumber
     * @param itemsPerPage
     * @param sortCriteria
     * @param sortOrder
     * @param searchData
     * @return
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}/{sortCriteria}/{sortOrder}", method = RequestMethod.GET)
    public PageDTO<StudentDTO> pageStudentWithSearch(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                     @PathVariable String sortCriteria, @PathVariable String sortOrder,
                                                     StudentDTO searchData) {
        Map<String, String> searchDataMap = TypeConverter.ObjectToMap(searchData);
        ListToPageTransformer<Students> queryResult = studentsService.getStudentBySearchAndPagination(
                pageNumber,
                itemsPerPage,
                searchDataMap,
                sortCriteria,
                sortOrder
        );
        List<StudentDTO> content = toStudentDtoFromList(queryResult.getContent());
        return new PageDTO(queryResult.getTotalItems(), content);
    }

    /**
     * Build page without sorting, ordering and searching data
     *
     * @param pageNumber
     * @param itemsPerPage
     * @return
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}", method = RequestMethod.GET)
    public PageDTO<StudentDTO> getStudentPage(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage) {
        return pageStudentWithSearch(pageNumber, itemsPerPage, null, null, null);
    }

    /**
     * Convert list of counter types to list CounterTypeDTO
     *
     * @param list
     * @return
     */
    public static List<StudentDTO> toStudentDtoFromList(List<Students> list) {
        List<StudentDTO> resultList = new ArrayList<>();
        for (Students student : list) {
            resultList.add(new StudentDTO(
                    student.getId(),
                    student.getLastName(),
                    student.getFirstName(),
                    student.getMiddleName(),
                    student.getNumberGradebook(),
                    student.getGroups().getTitle()
            ));
        }
        return resultList;
    }
}
