package com.testing.edu.service;


import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.Tests;
import com.testing.edu.service.utils.ListToPageTransformer;

import java.util.Map;

public interface TestsService {

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
                                                                   String sortCriteria, String sortOrder, Lecturers lecturer);
}
