package com.testing.edu.controller.student;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/student/statistics/")
public class StudentStatisticController {

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private LecturersService lecturersService;

    @Autowired
    private StudentsService studentsService;

    @RequestMapping(value = "subjects", method = RequestMethod.GET)
    public CountDTO countSubjects(@AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        User userous =  statisticService.employeeExist(user.getUsername());
        List<Students> arrayList = new ArrayList<>(userous.getStudentses());
        return new CountDTO(statisticService.countSubjectsByGroupId(arrayList.get(0).getGroups().getId()));
    }

    @RequestMapping(value = "results", method = RequestMethod.GET)
    public CountDTO countResults(@AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        User userous =  statisticService.employeeExist(user.getUsername());
        List<Students> arrayList = new ArrayList<>(userous.getStudentses());
        return new CountDTO(statisticService.countResultsByStudentId(arrayList.get(0).getId()));
    }

    @RequestMapping(value = "group/results", method = RequestMethod.GET)
    public CountDTO countGroupResults(@AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        User userous =  statisticService.employeeExist(user.getUsername());
        List<Students> arrayList = new ArrayList<>(userous.getStudentses());
        return new CountDTO(statisticService.countResultsByGroupId(arrayList.get(0).getGroups().getId()));
    }


    @RequestMapping(value = "tests", method = RequestMethod.GET)
    public CountDTO countTests(@AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        User userous =  statisticService.employeeExist(user.getUsername());
        List<Students> arrayList = new ArrayList<>(userous.getStudentses());
        return new CountDTO(statisticService.countTestByStudentId(arrayList.get(0).getId()));
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
