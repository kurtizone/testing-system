package com.testing.edu.service.impl;

import com.testing.edu.entity.Result;
import com.testing.edu.entity.Students;
import com.testing.edu.repository.ResultsRepository;
import com.testing.edu.service.ResultService;
import com.testing.edu.service.specification.builder.ResultSpecificationBuilder;
import com.testing.edu.service.utils.ListToPageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultsRepository resultsRepository;

    /**
     * Find result by id
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Result findById(Long id) {
        return resultsRepository.findOne(id);
    }

    /**
     * Delete result by his id
     *
     * @param id
     */
    @Override
    public void removeResult(Long id) {
        resultsRepository.delete(id);
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
    public ListToPageTransformer<Result> getResultByStudentBySearchAndPagination(int pageNumber, int itemsPerPage, Map<String, String> searchKeys, String sortCriteria, String sortOrder) {
        ResultSpecificationBuilder specificationBuilder = new ResultSpecificationBuilder(searchKeys);
        Pageable pageSpec = specificationBuilder.constructPageSpecification(pageNumber - 1, itemsPerPage, sortCriteria, sortOrder);
        Specification<Result> searchSpec = specificationBuilder.buildPredicate();

        int totalItems = resultsRepository.findAll(searchSpec).size();
        Page<Result> resultsPage = resultsRepository.findAll(searchSpec, pageSpec);
        List<Result> results = resultsPage.getContent();

        ListToPageTransformer<Result> result = new ListToPageTransformer<>();
        result.setContent(results);
        result.setTotalItems((long) totalItems);
        return result;
    }
}
