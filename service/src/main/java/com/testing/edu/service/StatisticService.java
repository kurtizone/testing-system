package com.testing.edu.service;


import com.testing.edu.entity.User;

public interface StatisticService {

    /**
     * Count Subjects
     * @return amount of subjects
     */
    Long countSubjects();

    /**
     * Count Lecturers
     * @return amount of lecturers
     */
    Long countLecturers();

    /**
     * Count Groups
     * @return amount of groups
     */
    Long countGroups();

    /**
     * Count Students
     * @return amount of Students
     */
    Long countStudents();

    /**
     * find user with username
     * @param username
     * @return
     */
    User employeeExist(String username);
}
