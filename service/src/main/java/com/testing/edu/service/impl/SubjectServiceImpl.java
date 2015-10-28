package com.testing.edu.service.impl;

import com.testing.edu.entity.Subject;
import com.testing.edu.repository.SubjectRepository;
import com.testing.edu.service.SubjectService;
import com.testing.edu.service.criteria.SubjectQueryConstructor;
import com.testing.edu.service.utils.ListToPageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

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
    public void addSubject(String title, Float multiplier, Integer hours) {
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
    public void editSubject(Long id, String title, Float multiplier, Integer hours) {
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
     * @param title
     * @param multiplier
     * @param hours
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    @Override
    @Transactional
    public ListToPageTransformer<Subject> getSubjectBySearchAndPagination(int pageNumber, int itemsPerPage, String title,
                                                                          Float multiplier, Integer hours,
                                                                          String sortCriteria, String sortOrder) {
        CriteriaQuery<Subject> criteriaQuery = SubjectQueryConstructor
                .buildSearchQuery(title, multiplier, hours, sortCriteria, sortOrder, entityManager);

        Long count = entityManager.createQuery(SubjectQueryConstructor
                .buildCountQuery(title, multiplier, hours, entityManager)).getSingleResult();

        TypedQuery<Subject> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((pageNumber - 1) * itemsPerPage);
        typedQuery.setMaxResults(itemsPerPage);
        List<Subject> subjectsList = typedQuery.getResultList();

        ListToPageTransformer<Subject> result = new ListToPageTransformer<>();
        result.setContent(subjectsList);
        result.setTotalItems(count);
        return result;
    }
}
