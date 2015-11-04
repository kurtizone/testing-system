package com.testing.edu.service.impl;

import com.testing.edu.entity.Subject;
import com.testing.edu.repository.SubjectRepository;
import com.testing.edu.service.SubjectService;
import com.testing.edu.service.criteria.SubjectQueryConstructor;
import com.testing.edu.service.specification.builder.SubjectSpecificationBuilder;
import com.testing.edu.service.utils.ListToPageTransformer;
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
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save subject with params
     *
     * @param title
     * @param multiplier
     * @param hours
     */
    @Override
    @Transactional
    public void addSubject(String title, Double multiplier, Integer hours) {
        Subject subject = new Subject(title, multiplier, hours);
        subjectRepository.save(subject);
    }

    /**
     * Edit subject with params
     *
     * @param id
     * @param title
     * @param multiplier
     * @param hours
     */
    @Override
    @Transactional
    public void editSubject(Long id, String title, Double multiplier, Integer hours) {
        Subject subject = subjectRepository.findOne(id);
        subject.setTitle(title);
        subject.setMultiplier(multiplier);
        subject.setHours(hours);

        subjectRepository.save(subject);
    }

    /**
     * Delete subject by his id
     *
     * @param id
     */
    @Override
    @Transactional
    public void removeSubject(Long id) {
        subjectRepository.delete(id);
    }

    /**
     * Find subject by id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Subject findById(Long id) {
        return subjectRepository.findOne(id);
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
    public ListToPageTransformer<Subject> getSubjectBySearchAndPagination(int pageNumber, int itemsPerPage,
                                                                          Map<String, String> searchKeys,
                                                                          String sortCriteria, String sortOrder) {
        SubjectSpecificationBuilder specificationBuilder = new SubjectSpecificationBuilder(searchKeys);
        Pageable pageSpec = specificationBuilder.constructPageSpecification(pageNumber - 1, itemsPerPage, sortCriteria, sortOrder);
        Specification<Subject> searchSpec = specificationBuilder.buildPredicate();

        Page<Subject> subjectPage = subjectRepository.findAll(searchSpec, pageSpec);
        List<Subject> subjects = subjectPage.getContent();

        ListToPageTransformer<Subject> result = new ListToPageTransformer<>();
        result.setContent(subjects);
        result.setTotalItems((long) subjects.size());
        return result;
    }
}
