package com.testing.edu.service.impl;


import com.testing.edu.entity.User;
import com.testing.edu.repository.*;
import com.testing.edu.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
