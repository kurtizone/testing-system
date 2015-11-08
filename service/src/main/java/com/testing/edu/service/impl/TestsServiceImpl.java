package com.testing.edu.service.impl;

import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.Subject;
import com.testing.edu.entity.Tests;
import com.testing.edu.repository.TestsRepository;
import com.testing.edu.service.TestsService;
import com.testing.edu.service.specification.builder.TestSpecificationBuilder;
import com.testing.edu.service.utils.ListToPageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

@Service
public class TestsServiceImpl implements TestsService {

    @Autowired
    private TestsRepository testsRepository;
    /**
     * Service for building page by SortCriteria, SortOrder and Searching data
     *
     * @param pageNumber
     * @param itemsPerPage
     * @param searchKeys
     * @param sortCriteria
     * @param sortOrder
     * @param lecturer
     * @return
     */
    @Override
    @Transactional
    public ListToPageTransformer<Tests> getTestsBySearchAndPagination(int pageNumber, int itemsPerPage, Map<String, String> searchKeys, String sortCriteria, String sortOrder, Lecturers lecturer) {
        searchKeys.put("lecturer", lecturer.getId().toString());
        TestSpecificationBuilder specificationBuilder = new TestSpecificationBuilder(searchKeys);
        Pageable pageSpec = specificationBuilder.constructPageSpecification(pageNumber - 1, itemsPerPage, sortCriteria, sortOrder);
        Specification<Tests> searchSpec = specificationBuilder.buildPredicate();

        Page<Tests> testPage = testsRepository.findAll(searchSpec, pageSpec);
        List<Tests> tests = testPage.getContent();

        ListToPageTransformer<Tests> result = new ListToPageTransformer<>();
        result.setContent(tests);
        result.setTotalItems((long) tests.size());
        return result;
    }
}
