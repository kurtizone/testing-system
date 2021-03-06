package com.testing.edu.service.impl;

import com.testing.edu.entity.Groups;
import com.testing.edu.entity.Students;
import com.testing.edu.entity.User;
import com.testing.edu.entity.enumeration.AcademicStatus;
import com.testing.edu.entity.enumeration.Degree;
import com.testing.edu.entity.enumeration.UserRole;
import com.testing.edu.repository.StudentsRepository;
import com.testing.edu.repository.UserRepository;
import com.testing.edu.service.GroupsService;
import com.testing.edu.service.MailService;
import com.testing.edu.service.StudentsService;
import com.testing.edu.service.specification.builder.StudentsSpecificationBuilder;
import com.testing.edu.service.utils.ListToPageTransformer;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Service
public class StudentsServiceImpl implements StudentsService {

    private final Logger logger = Logger.getLogger(LecturersServiceImpl.class);

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MailService mailService;

    /**
     * Save student with user
     *
     * @param lastname
     * @param firstname
     * @param middleName
     * @param numberGradebook
     * @param groups
     * @param username
     * @param email
     * @param phone
     */
    @Override
    @Transactional
    public void addStudent(String lastname, String firstname, String middleName, String numberGradebook, Groups groups,
                           String username, String email, String phone, Boolean enable) {

        String password = RandomStringUtils.randomAlphanumeric(6);
        String passwordEncoded = new BCryptPasswordEncoder().encode(password);
        User user = new User(username, email, passwordEncoded, phone, enable, UserRole.STUDENT);
        userRepository.save(user);

        Students student = new Students(lastname, firstname, middleName, numberGradebook, groups, user);
        studentsRepository.save(student);
        mailService.sendRegistrationMail(email, firstname, lastname, middleName, username, password);
    }

    /**
     * Save student with params
     *
     * @param lastname
     * @param firstname
     * @param middleName
     * @param numberGradebook
     * @param groups
     */
    @Override
    @Transactional
    public void addStudent(String lastname, String firstname, String middleName, String numberGradebook, Groups groups) {
        Students students = new Students(
                lastname,
                firstname,
                middleName,
                numberGradebook,
                groups
        );

        studentsRepository.save(students);
    }

    /**
     * Edit student with user
     *
     * @param id
     * @param lastname
     * @param firstname
     * @param middleName
     * @param numberGradebook
     * @param groups
     * @param username
     * @param email
     * @param phone
     * @param password
     */
    @Override
    @Transactional
    public void editStudent(Long id, String lastname, String firstname, String middleName, String numberGradebook, Groups groups,
                            String username, String email, String phone, String password, Boolean enable) {
        Students students = studentsRepository.findOne(id);
        User user = students.getUser();
        String newPassword;
        user.setEmail(email);
        user.setPhone(phone);
        user.setEnable(enable);
        if(password != null && password.equals("generate") && !user.getPassword().isEmpty()){
            newPassword = RandomStringUtils.randomAlphanumeric(6);
            mailService.sendNewPassword(user.getEmail(), students.getFirstName(), user.getUsername(), newPassword);
            String passwordEncoded = new BCryptPasswordEncoder().encode(newPassword);
            user.setPassword(passwordEncoded);
        }
        userRepository.save(user);

        students.setLastName(lastname);
        students.setFirstName(firstname);
        students.setMiddleName(middleName);
        students.setNumberGradebook(numberGradebook);
        students.setGroups(groups);
        students.setUser(user);

        studentsRepository.save(students);

        mailService.sendStudentChanges(email, firstname, lastname, middleName, groups.getTitle(), numberGradebook, phone, username);
    }

    /**
     * Edit student with params
     *
     * @param id
     * @param lastname
     * @param firstname
     * @param middleName
     * @param numberGradebook
     */
    @Override
    @Transactional
    public void editStudent(Long id, String lastname, String firstname, String middleName, String numberGradebook, Groups groups) {
        Students students = studentsRepository.findOne(id);

        students.setLastName(lastname);
        students.setFirstName(firstname);
        students.setMiddleName(middleName);
        students.setNumberGradebook(numberGradebook);
        students.setGroups(groups);

        studentsRepository.save(students);
    }

    /**
     * Delete student by his id
     *
     * @param id
     */
    @Override
    @Transactional
    public void removeStudent(Long id) {
        Students students = studentsRepository.findOne(id);
        User user = students.getUser();
        userRepository.delete(user);
        studentsRepository.delete(students);
    }

    /**
     * Find student by id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Students findById(Long id) {
        return studentsRepository.findOne(id);
    }

    /**
     * Service for building page by SortCriteria, SortOrder and Searching data
     *
     * @param pageNumber
     * @param itemsPerPage
     * @param searchKeys
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    @Override
    @Transactional
    public ListToPageTransformer<Students> getStudentBySearchAndPagination(int pageNumber, int itemsPerPage, Map<String, String> searchKeys, String sortCriteria, String sortOrder) {
        StudentsSpecificationBuilder specificationBuilder = new StudentsSpecificationBuilder(searchKeys);
        Pageable pageSpec = specificationBuilder.constructPageSpecification(pageNumber - 1, itemsPerPage, sortCriteria, sortOrder);
        Specification<Students> searchSpec = specificationBuilder.buildPredicate();

        int totalItems = studentsRepository.findAll(searchSpec).size();
        Page<Students> studentsPage = studentsRepository.findAll(searchSpec, pageSpec);
        List<Students> students = studentsPage.getContent();

        ListToPageTransformer<Students> result = new ListToPageTransformer<>();
        result.setContent(students);
        result.setTotalItems((long) totalItems);
        return result;
    }

    /**
     * find Student by User
     *
     * @param user
     * @return
     */
    @Override
    @Transactional
    public Students findByUser(User user) {
        return studentsRepository.findByUserId(user.getId());
    }

}
