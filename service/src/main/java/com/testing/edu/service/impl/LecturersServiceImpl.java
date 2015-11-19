package com.testing.edu.service.impl;

import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.Subject;
import com.testing.edu.entity.User;
import com.testing.edu.entity.enumeration.AcademicStatus;
import com.testing.edu.entity.enumeration.Degree;
import com.testing.edu.entity.enumeration.UserRole;
import com.testing.edu.repository.LecturersRepository;
import com.testing.edu.repository.SubjectRepository;
import com.testing.edu.repository.UserRepository;
import com.testing.edu.service.LecturersService;
import com.testing.edu.service.MailService;
import com.testing.edu.service.criteria.LecturersQueryConstructor;
import com.testing.edu.service.specification.builder.LecturersSpecificationBuilder;
import com.testing.edu.service.specification.builder.SubjectSpecificationBuilder;
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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class LecturersServiceImpl implements LecturersService {

    private final Logger logger = Logger.getLogger(LecturersServiceImpl.class);

    @Autowired
    private LecturersRepository lecturersRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MailService mailService;

    /**
     * Save lecturer with user
     *
     * @param lastname
     * @param firstname
     * @param middleName
     * @param academicStatus
     * @param degree
     * @param username
     * @param email
     * @param phone
     * @param enable
     */
    @Override
    public void addLecturer(String lastname, String firstname, String middleName, String academicStatus, String degree,
                            String username, String email, String phone, Boolean enable) {
        String password = RandomStringUtils.randomAlphanumeric(6);
        String passwordEncoded = new BCryptPasswordEncoder().encode(password);
        User user = new User(username, email, passwordEncoded, phone, enable, UserRole.LECTURER);
        userRepository.save(user);

        Lecturers lecturer = new Lecturers(
                lastname,
                firstname,
                middleName,
                AcademicStatus.valueOf(academicStatus),
                Degree.valueOf(degree),
                user
        );
        lecturersRepository.save(lecturer);
        mailService.sendRegistrationMail(email, firstname, lastname, middleName, username, password);
    }

    /**
     * Save lecturer with params
     *
     * @param lastname
     * @param firstname
     * @param middleName
     * @param academicStatus
     * @param degree
     */
    @Override
    @Transactional
    public void addLecturer(String lastname, String firstname, String middleName, String academicStatus, String degree) {
        Lecturers lecturer = new Lecturers(
                lastname,
                firstname,
                middleName,
                AcademicStatus.valueOf(academicStatus),
                Degree.valueOf(degree)
        );
        lecturersRepository.save(lecturer);
    }

    /**
     * Edit lecturer with user
     *
     * @param id
     * @param lastname
     * @param firstname
     * @param middleName
     * @param academicStatus
     * @param degree
     * @param username
     * @param email
     * @param phone
     * @param password
     */
    @Override
    public void editLecturer(Long id, String lastname, String firstname, String middleName, String academicStatus, String degree,
                             String username, String email, String phone, String password, Boolean enable) {
        Lecturers lecturer = lecturersRepository.findOne(id);
        User user = lecturer.getUser();
        String newPassword;
        user.setEmail(email);
        user.setPhone(phone);
        user.setEnable(enable);
        if(password != null && password.equals("generate") && !user.getPassword().isEmpty()){
            newPassword = RandomStringUtils.randomAlphanumeric(6);
            mailService.sendNewPassword(user.getEmail(), lecturer.getFirstName(), user.getUsername(), newPassword);
            String passwordEncoded = new BCryptPasswordEncoder().encode(newPassword);
            user.setPassword(passwordEncoded);
        }
        userRepository.save(user);

        lecturer.setLastName(lastname);
        lecturer.setFirstName(firstname);
        lecturer.setMiddleName(middleName);
        lecturer.setAcademicStatus(AcademicStatus.valueOf(academicStatus));
        lecturer.setDegree(Degree.valueOf(degree));
        lecturer.setUser(user);

        lecturersRepository.save(lecturer);
        mailService.sendLecturerChanges(email, firstname, lastname, middleName, getAcademicStatus(academicStatus),
                getDegree(degree), phone, username);
    }

    /**
     * Edit lecturer with params
     *
     * @param id
     * @param lastname
     * @param firstname
     * @param middleName
     * @param academicStatus
     * @param degree
     */
    @Override
    @Transactional
    public void editLecturer(Long id, String lastname, String firstname, String middleName, String academicStatus, String degree) {
        Lecturers lecturer = lecturersRepository.findOne(id);

        lecturer.setLastName(lastname);
        lecturer.setFirstName(firstname);
        lecturer.setMiddleName(middleName);
        lecturer.setAcademicStatus(AcademicStatus.valueOf(academicStatus));
        lecturer.setDegree(Degree.valueOf(degree));

        lecturersRepository.save(lecturer);
    }

    /**
     * Delete lecturer by his id
     *
     * @param id
     */
    @Override
    @Transactional
    public void removeLecturer(Long id) {
        Lecturers lecturers = lecturersRepository.findOne(id);
        User user = lecturers.getUser();
        userRepository.delete(user);
        lecturersRepository.delete(lecturers);
    }

    /**
     * Delete lecturer by his id
     *
     * @param lecturerId
     * @param subjectId
     */
    @Override
    public void removeSubjectOfLecturer(Long lecturerId, Long subjectId) {
        Lecturers lecturer = lecturersRepository.findOne(lecturerId);
        Set<Subject> subjectSet = lecturer.getSubjects();
        Subject subject = subjectRepository.findOne(subjectId);
        subjectSet.remove(subject);
        lecturer.setSubjects(subjectSet);
        lecturersRepository.save(lecturer);
    }

    /**
     * Find lecturer by id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Lecturers findById(Long id) {
        return lecturersRepository.findOne(id);
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
    public ListToPageTransformer<Lecturers> getLecturerBySearchAndPagination(int pageNumber, int itemsPerPage, Map<String, String> searchKeys,
                                                                             String sortCriteria, String sortOrder) {
        LecturersSpecificationBuilder specificationBuilder = new LecturersSpecificationBuilder(searchKeys);
        Pageable pageSpec = specificationBuilder.constructPageSpecification(pageNumber - 1, itemsPerPage, sortCriteria, sortOrder);
        Specification<Lecturers> searchSpec = specificationBuilder.buildPredicate();

        int totalItems = lecturersRepository.findAll(searchSpec).size();
        Page<Lecturers> lecturersPage = lecturersRepository.findAll(searchSpec, pageSpec);
        List<Lecturers> lecturers = lecturersPage.getContent();

        ListToPageTransformer<Lecturers> result = new ListToPageTransformer<>();
        result.setContent(lecturers);
        result.setTotalItems((long) totalItems);
        return result;
    }

    /**
     * find Lecturer by User
     *
     * @param user
     * @return
     */
    @Override
    public Lecturers findByUser(User user) {
        return lecturersRepository.findByUserId(user.getId());
    }

    /**
     * Get all lecturers
     *
     * @return List of lecturers
     */
    @Override
    @Transactional
    public List<Lecturers> getAllLecturers() {
        return (List<Lecturers>) lecturersRepository.findAll();
    }

    /**
     * add new subject for lecturer
     *
     * @param lecturerId
     * @param subjectId
     */
    @Override
    @Transactional
    public void addSubject(Long lecturerId, Long subjectId) {
        Lecturers lecturer = lecturersRepository.findOne(lecturerId);
        Subject subject = subjectRepository.findOne(subjectId);
        Set<Subject> subjectSet = lecturer.getSubjects();
        subjectSet.add(subject);
        lecturer.setSubjects(subjectSet);
        lecturersRepository.save(lecturer);
    }


    public static String getAcademicStatus(String academic){
        switch (AcademicStatus.valueOf(academic)){
            case PROFESSOR: return "Професор";
            case DOCENT: return "Доцент";
            case SENIOR_LECTURER: return "Старший викладач";
        }
        return null;
    }


    public static String getDegree(String degree){
        switch (Degree.valueOf(degree)){
            case CANDIDATE: return "Кандидат наук";
            case DOCTOR: return "Доктор наук";
            case POSTGRADUATE: return "Аспірант";
        }
        return null;
    }
}
