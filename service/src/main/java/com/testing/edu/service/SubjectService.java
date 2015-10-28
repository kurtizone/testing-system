package com.testing.edu.service;

import com.testing.edu.entity.Subject;
import com.testing.edu.service.utils.ListToPageTransformer;

public interface SubjectService {

    /**
     * Save subject with params
     * @param title
     * @param multiplier
     * @param hours
     */
    void addSubject(String title, Float multiplier, Integer hours);

    /**
     * Edit subject with params
     * @param id
     * @param title
     * @param multiplier
     * @param hours
     */
    void editSubject(Long id, String title, Float multiplier, Integer hours);

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
     * @param title
     * @param multiplier
     * @param hours
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    ListToPageTransformer<Subject> getSubjectBySearchAndPagination(int pageNumber, int itemsPerPage, String title,
                                                                   String multiplier, Integer hours,
                                                                   String sortCriteria, String sortOrder);

}
