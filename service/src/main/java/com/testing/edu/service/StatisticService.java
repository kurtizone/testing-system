package com.testing.edu.service;


import com.testing.edu.entity.User;

public interface StatisticService {

    /**
     * Count Subjects
     * @return amount of subjects
     */
    Long countSubjects();

    /**
     * Count Subjects by Lecturer id
     * @return amount of subjects
     */
    Long countSubjectsByLecturerId(Long lecturerId);

    /**
     * Count Subjects by Lecturer id
     * @param groupId
     * @return amount of subjects
     */
    Long countSubjectsByGroupId(Long groupId);

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
     * Count test witch have lecturer
     * @return
     */
    Long countTestByLecturerId(Long lecturerId);

    /**
     * Count test witch have lecturer
     * @param studentId
     * @return
     */
    Long countTestByStudentId(Long studentId);

    /**
     * Count Results
     * @return amount of results
     */
    Long countResults();


    /**
     * Count results witch have lecturer
     * @return
     */
    Long countResultsByLecturerId(Long lecturerId);

    /**
     * Count results witch have student
     * @param studentId
     * @return
     */
    Long countResultsByStudentId(Long studentId);

    /**
     * Count results witch have group
     * @param groupId
     * @return
     */
    Long countResultsByGroupId(Long groupId);

    /**
     * find user with username
     * @param username
     * @return
     */
    User employeeExist(String username);
}
