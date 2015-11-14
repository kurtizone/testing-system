package com.testing.edu.service.impl;

import com.testing.edu.entity.Groups;
import com.testing.edu.entity.Students;
import com.testing.edu.entity.User;
import com.testing.edu.repository.StudentsRepository;
import com.testing.edu.service.GroupsService;
import com.testing.edu.service.StudentsService;
import com.testing.edu.service.specification.builder.StudentsSpecificationBuilder;
import com.testing.edu.service.utils.ListToPageTransformer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    @PersistenceContext
    private EntityManager entityManager;

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
        studentsRepository.delete(id);
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
