package com.testing.edu.service;


import com.testing.edu.entity.*;
import com.testing.edu.service.utils.ListToPageTransformer;

import java.util.Map;

public interface ResultService {


    /**
     * Save result with params
     * @param result
     */
    void saveResult(Result result);


    /**
     * Find result by id
     * @param id
     * @return
     */
    Result findById(Long id);

    /**
     * Delete result by his id
     * @param id
     */
    void removeResult(Long id);

    /**
     * Service for building page by SortCriteria, SortOrder and Searching data
     * @param pageNumber
     * @param itemsPerPage
     * @param searchKeys
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    ListToPageTransformer<Result> getResultByStudentBySearchAndPagination(int pageNumber, int itemsPerPage,
                                                                   Map<String, String> searchKeys,
                                                                   String sortCriteria, String sortOrder);
}
