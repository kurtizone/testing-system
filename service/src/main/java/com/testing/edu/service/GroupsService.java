package com.testing.edu.service;


import com.testing.edu.entity.Groups;
import com.testing.edu.service.utils.ListToPageTransformer;

import java.util.Map;

public interface GroupsService {

    /**
     * Save group with params
     * @param title
     * @param grade
     * @param degree
     * @param studyForm
     */
    void addGroup(String title, Integer grade, String degree, String studyForm);

    /**
     * Edit group with params
     * @param id
     * @param title
     * @param grade
     * @param degree
     * @param studyForm
     */
    void editGroup(Long id, String title, Integer grade, String degree, String studyForm);

    /**
     * Delete group by his id
     * @param id
     */
    void removeGroup(Long id);

    /**
     * Find group by id
     * @param id
     * @return
     */
    Groups findById(Long id);

    /**
     * Service for building page by SortCriteria, SortOrder and Searching data
     * @param pageNumber
     * @param itemsPerPage
     * @param searchKeys
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    ListToPageTransformer<Groups> getGroupBySearchAndPagination(int pageNumber, int itemsPerPage,
                                                                   Map<String, String> searchKeys,
                                                                   String sortCriteria, String sortOrder);
}

