package com.testing.edu.service.impl;

import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.enumeration.AcademicStatus;
import com.testing.edu.entity.enumeration.Degree;
import com.testing.edu.repository.LecturersRepository;
import com.testing.edu.service.LecturersService;
import com.testing.edu.service.criteria.LecturersQueryConstructor;
import com.testing.edu.service.utils.ListToPageTransformer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Service
public class LecturersServiceImpl implements LecturersService {

    private final Logger logger = Logger.getLogger(LecturersServiceImpl.class);

    @Autowired
    private LecturersRepository lecturersRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
        lecturersRepository.delete(id);
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
     * @param lastName
     * @param firstName
     * @param middleName
     * @param academicStatus
     * @param degree
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    @Override
    @Transactional
    public ListToPageTransformer<Lecturers> getLecturerBySearchAndPagination(int pageNumber, int itemsPerPage, String lastName,
                                                                             String firstName, String middleName, String academicStatus,
                                                                             String degree, String sortCriteria, String sortOrder) {
        CriteriaQuery<Lecturers> criteriaQuery = LecturersQueryConstructor
                .buildSearchQuery(lastName, firstName, middleName, academicStatus, degree, sortCriteria, sortOrder, entityManager);

        Long count = entityManager.createQuery(LecturersQueryConstructor
                .buildCountQuery(lastName, firstName, middleName, academicStatus, degree, entityManager)).getSingleResult();

        TypedQuery<Lecturers> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((pageNumber - 1) * itemsPerPage);
        typedQuery.setMaxResults(itemsPerPage);
        List<Lecturers> lecturersList = typedQuery.getResultList();

        ListToPageTransformer<Lecturers> result = new ListToPageTransformer<Lecturers>();
        result.setContent(lecturersList);
        result.setTotalItems(count);
        return result;
    }
}
