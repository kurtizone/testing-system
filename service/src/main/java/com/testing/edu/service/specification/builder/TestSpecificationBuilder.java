package com.testing.edu.service.specification.builder;

import com.testing.edu.entity.Tests;
import com.testing.edu.entity.enumeration.TestType;
import com.testing.edu.service.specification.SearchCriterion;
import com.testing.edu.service.specification.SpecificationBuilder;
import com.testing.edu.service.specification.sort.TestsSortCriteria;
import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestSpecificationBuilder extends SpecificationBuilder<Tests> {
    static Logger logger = Logger.getLogger(TestSpecificationBuilder.class);

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String TYPE = "type";
    public static final String SUBJECT = "subject";
    public static final String MAXGRADE = "maxGrade";
    public static final String AVAIBLE = "avaible";

    public TestSpecificationBuilder(Map<String, String> searchValues) {
        super(searchValues);
    }

    /**
     * Initialize criteria for Subject Specification
     *
     * @return search criteria
     */
    @Override
    protected List<SearchCriterion> initCriteria() {
        List<SearchCriterion> searchCriteria = new ArrayList<>();
        searchCriteria.add(new SearchCriterion<>("lecturer", "subject", SearchCriterion.Operator.EQUAL, SearchCriterion.ValueType.LONG, "lecturerses", "id"));
        searchCriteria.add(new SearchCriterion<>("subject", "subject", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING, "title"));
        searchCriteria.add(new SearchCriterion<>(TITLE, "title", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING));
        searchCriteria.add(new SearchCriterion<>(TYPE, "type", SearchCriterion.Operator.EQUAL_BY_ENUM, TestType.class));
        searchCriteria.add(new SearchCriterion<>(MAXGRADE, "maxGrade", SearchCriterion.Operator.EQUAL, SearchCriterion.ValueType.INTEGER));
        searchCriteria.add(new SearchCriterion<>(AVAIBLE, "avaible", SearchCriterion.Operator.EQUAL, SearchCriterion.ValueType.BOOLEAN));

        for (SearchCriterion searchCriterion : searchCriteria) {
            logger.info("-----searchCriteria------ " + searchCriterion);
        }
        return searchCriteria;
    }

    /**
     * Create Sort object for Specification executor by criteria and order
     *
     * @param sortCriteria sorting criterion
     * @param sortOrder    sorting order
     * @return Sort object
     */
    @Override
    public Sort getSort(String sortCriteria, String sortOrder) {
        if (checkValue(sortCriteria) && checkValue(sortOrder)) {
            return TestsSortCriteria.valueOf(sortCriteria.toUpperCase()).getSort(sortOrder);
        } else {
            return TestsSortCriteria.UNDEFINED.getSort("asc");
        }
    }

    /**
     * Creates a new PageRequest with sort parameters applied.
     *
     * @param pageNumber   zero-based page index.
     * @param itemsPerPage the size of the page to be returned.
     * @param sortCriteria sorting criterion
     * @param sortOrder    sorting order
     * @return
     */
    @Override
    public Pageable constructPageSpecification(int pageNumber, int itemsPerPage, String sortCriteria, String sortOrder) {
        return new PageRequest(pageNumber, itemsPerPage, getSort(sortCriteria, sortOrder));
    }
}
