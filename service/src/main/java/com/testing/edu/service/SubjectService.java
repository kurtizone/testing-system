package com.testing.edu.service;

import com.testing.edu.entity.*;
import com.testing.edu.service.utils.ListToPageTransformer;

import java.util.List;
import java.util.Map;

public interface SubjectService {

    /**
     * Save subject with params
     * @param title
     * @param multiplier
     * @param hours
     */
    void addSubject(String title, Double multiplier, Integer hours);

    /**
     * Edit subject with params
     * @param id
     * @param title
     * @param multiplier
     * @param hours
     */
    void editSubject(Long id, String title, Double multiplier, Integer hours);

    /**
     * Delete subject by his id
     * @param id
     */
    void removeSubject(Long id);

    /**
     * Find subject by id
     * @param id
     * @return
     */
    Subject findById(Long id);

    /**
     * Service for building page by SortCriteria, SortOrder and Searching data
     * @param pageNumber
     * @param itemsPerPage
     * @param searchKeys
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    ListToPageTransformer<Subject> getSubjectBySearchAndPagination(int pageNumber, int itemsPerPage,
                                                                   Map<String, String> searchKeys,
                                                                   String sortCriteria, String sortOrder);
    /**
     * Service for building page by SortCriteria, SortOrder and Searching data
     * @param pageNumber
     * @param itemsPerPage
     * @param searchKeys
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    ListToPageTransformer<Subject> getSubjectBySearchAndPagination(int pageNumber, int itemsPerPage,
                                                                   Map<String, String> searchKeys,
                                                                   String sortCriteria, String sortOrder, Lecturers lecturer);


    /**
     * Service for building page by SortCriteria, SortOrder and Searching data
     * @param pageNumber
     * @param itemsPerPage
     * @param searchKeys
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    ListToPageTransformer<Subject> getSubjectBySearchAndPagination(int pageNumber, int itemsPerPage,
                                                                   Map<String, String> searchKeys,
                                                                   String sortCriteria, String sortOrder, Groups group);

    /**
     * Count number of groups
     * @param subjectId
     * @return
     */
    Long countOfGroups(Long subjectId);

    /**
     * Count number of tests
     * @param subjectId
     * @return
     */
    Long countOfTests(Long subjectId);

    /**
     * Get all subjects
     * @return List of subjects
     */
    List<Subject> getAllSubjects();
}
