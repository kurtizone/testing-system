package com.testing.edu.service.impl;


import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.Subject;
import com.testing.edu.entity.Tests;
import com.testing.edu.entity.User;
import com.testing.edu.repository.*;
import com.testing.edu.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class StatisticServiceImpl implements StatisticService{

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private LecturersRepository lecturersRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Count Subjects
     *
     * @return amount of subjects
     */
    @Override
    public Long countSubjects() {
        return subjectRepository.count();
    }

    /**
     * Count Subjects by Lecturer id
     *
     * @param lecturerId
     * @return amount of subjects
     */
    @Override
    public Long countSubjectsByLecturerId(Long lecturerId) {
        Lecturers lecturer = lecturersRepository.findOne(lecturerId);
        return (long) lecturer.getSubjects().size();
    }

    /**
     * Count Lecturers
     *
     * @return amount of lecturers
     */
    @Override
    public Long countLecturers() {
        return lecturersRepository.count();
    }

    /**
     * Count Groups
     *
     * @return amount of groups
     */
    @Override
    public Long countGroups() {
        return groupsRepository.count();
    }

    /**
     * Count Students
     *
     * @return amount of Students
     */
    @Override
    public Long countStudents() {
        return studentsRepository.count();
    }

    /**
     * Count test witch have lecturer
     *
     * @param lecturerId
     * @return
     */
    @Override
    public Long countTestByLecturerId(Long lecturerId) {
        Lecturers lecturer = lecturersRepository.findOne(lecturerId);
        Long totalSize = 0l;
        Set<Subject> subjectSet = lecturer.getSubjects();
        for (Subject subject : subjectSet){
            totalSize += subject.getTestses().size();
        }
        return totalSize;
    }

    /**
     * Count results witch have lecturer
     *
     * @param lecturerId
     * @return
     */
    @Override
    public Long countResultsByLecturerId(Long lecturerId) {
        Lecturers lecturer = lecturersRepository.findOne(lecturerId);
        Long totalSize = 0l;
        Set<Subject> subjectSet = lecturer.getSubjects();
        for (Subject subject : subjectSet){
            Set<Tests> testsSet = subject.getTestses();
            for (Tests tests : testsSet){
                totalSize += tests.getResults().size();
            }
        }
        return totalSize;
    }

    /**
     * find user with username
     *
     * @param username
     * @return
     */
    @Override
    @Transactional
    public User employeeExist(String username) {
        return userRepository.findByUsername(username);
    }
}
