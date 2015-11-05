package com.testing.edu.controller.admin;

import com.testing.edu.dto.CountDTO;
import com.testing.edu.dto.admin.UsersPageItem;
import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.Students;
import com.testing.edu.entity.User;
import com.testing.edu.entity.enumeration.UserRole;
import com.testing.edu.service.LecturersService;
import com.testing.edu.service.StatisticService;
import com.testing.edu.service.StudentsService;
import com.testing.edu.service.security.SecurityUserDetailsService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/statistics/")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private LecturersService lecturersService;

    @Autowired
    private StudentsService studentsService;

    @RequestMapping(value = "subjects", method = RequestMethod.GET)
    public CountDTO countSubjects() {
        return new CountDTO(statisticService.countSubjects());
    }

    @RequestMapping(value = "lecturers", method = RequestMethod.GET)
    public CountDTO countUsers() {
        return new CountDTO(statisticService.countLecturers());
    }

    @RequestMapping(value = "groups", method = RequestMethod.GET)
    public CountDTO countDevices() {
        return new CountDTO(statisticService.countGroups());
    }

    @RequestMapping(value = "students", method = RequestMethod.GET)
    public CountDTO countCounterTypes() {
        return new CountDTO(statisticService.countStudents());
    }


    @RequestMapping(value = "employee", method = RequestMethod.GET)
    public UsersPageItem getEmployee(@AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        UsersPageItem usersPageItem = new UsersPageItem();
        User userous =  statisticService.employeeExist(user.getUsername());
        if(userous.getUserRole().equals(UserRole.LECTURER)) {
            Lecturers lecturer = lecturersService.findByUser(userous);
            usersPageItem.setFirstName(lecturer.getFirstName());
            usersPageItem.setLastName(lecturer.getLastName());
            usersPageItem.setMiddleName(lecturer.getMiddleName());
        } else if(userous.getUserRole().equals(UserRole.STUDENT)) {
            Students student = studentsService.findByUser(userous);
            usersPageItem.setFirstName(student.getFirstName());
            usersPageItem.setLastName(student.getLastName());
            usersPageItem.setMiddleName(student.getMiddleName());
        }
        usersPageItem.setUsername(userous.getUsername());
        return  usersPageItem;
    }

}
