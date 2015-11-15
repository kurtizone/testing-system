package com.testing.edu.service;


import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.Tests;
import com.testing.edu.service.utils.ListToPageTransformer;

import java.util.Locale;
import java.util.Map;

public interface TestsService {

    /**
     *  Save test with params
     *
     * @param title
     * @param type
     * @param maxGrade
     * @param avaible
     * @param subjectId
     */
    void addTest(String title, String type, Integer maxGrade, Boolean avaible, Long subjectId);

    /**
     * Edit test with params
     * @param id
     * @param title
     * @param type
     * @param maxGrade
     * @param avaible
     * @param subjectId
     */
    void editTest(Long id, String title, String type, Integer maxGrade,
                  Boolean avaible, Long subjectId);

    /**
     * Delete test by his id
     * @param id
     */
    void removeTest(Long id);

    /**
     * Find test by id
     *
     * @param id
     * @return
     */
    public Tests findById(Long id);


    /**
     * Service for building page by SortCriteria, SortOrder and Searching data
     * @param pageNumber
     * @param itemsPerPage
     * @param searchKeys
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    ListToPageTransformer<Tests> getTestsBySearchAndPagination(int pageNumber, int itemsPerPage,
                                                                   Map<String, String> searchKeys,
                                                                   String sortCriteria, String sortOrder);
}
