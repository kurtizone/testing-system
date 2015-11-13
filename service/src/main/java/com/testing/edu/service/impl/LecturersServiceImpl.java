package com.testing.edu.service.impl;

import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.Subject;
import com.testing.edu.entity.User;
import com.testing.edu.entity.enumeration.AcademicStatus;
import com.testing.edu.entity.enumeration.Degree;
import com.testing.edu.repository.LecturersRepository;
import com.testing.edu.service.LecturersService;
import com.testing.edu.service.criteria.LecturersQueryConstructor;
import com.testing.edu.service.specification.builder.LecturersSpecificationBuilder;
import com.testing.edu.service.specification.builder.SubjectSpecificationBuilder;
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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Map;

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
}
