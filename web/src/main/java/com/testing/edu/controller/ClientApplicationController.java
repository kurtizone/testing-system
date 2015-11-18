package com.testing.edu.controller;

import com.testing.edu.dto.admin.GroupDTO;
import com.testing.edu.dto.admin.LecturerDTO;
import com.testing.edu.dto.admin.StudentDTO;
import com.testing.edu.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/application/")
/**
 * Used in main application form (application-sending.html)
 * for creating verifications
 * and sending notifications about that to customerID's email
 */
public class ClientApplicationController {

    Logger logger = Logger.getLogger(ClientApplicationController.class);

    @Autowired
    private UserService usersService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private LecturersService lecturersService;

    @Autowired
    private MailService mailService;


    /**
     * Add student
     *
     * @param studentDTO object with student data
     * @return a response body with http status {@literal OK} if student
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "/users/student/add", method = RequestMethod.POST)
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
     * Add lecturer
     *
     * @param lecturerDTO object with lecturer data
     * @return a response body with http status {@literal OK} if lecturer
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "users/lecturer/add", method = RequestMethod.POST)
    public ResponseEntity addLecturer(@RequestBody LecturerDTO lecturerDTO) {
        HttpStatus httpStatus = HttpStatus.CREATED;

        try {
            lecturersService.addLecturer(
                    lecturerDTO.getLastName(),
                    lecturerDTO.getFirstName(),
                    lecturerDTO.getMiddleName(),
                    lecturerDTO.getAcademicStatus(),
                    lecturerDTO.getDegree(),
                    lecturerDTO.getUsername(),
                    lecturerDTO.getEmail(),
                    lecturerDTO.getPhone()
            );
        } catch (Exception e) {
            logger.error("Got exeption while add student ", e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }



    @RequestMapping(value = "users/available/username/{username}", method = RequestMethod.GET)
    public Boolean isValidUsername(@PathVariable String username) {
        boolean isAvailable = false;
        if (username != null) {
            isAvailable = usersService.isUsernameExist(username);
        }
        return isAvailable;
    }

    /**
     * return all groups
     * @return ist of groups wrapped into DeviceLightDTO
     */
    @RequestMapping(value = "groups", method = RequestMethod.GET)
    public List<GroupDTO> getAll() {
        return groupsService.getAllGroups().stream()
                .map(group -> new GroupDTO(group.getId(), group.getTitle()))
                .collect(Collectors.toList());
    }

}
