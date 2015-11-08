package com.testing.edu.service.specification.builder;


import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.Subject;
import com.testing.edu.service.specification.SearchCriterion;
import com.testing.edu.service.specification.SpecificationBuilder;
import com.testing.edu.service.specification.sort.SubjectSortCriteria;
import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubjectSpecificationBuilder extends SpecificationBuilder<Subject> {

    static Logger logger = Logger.getLogger(SubjectSpecificationBuilder.class);

    public static final String ID = "id";
    public static final String ID_LECTURER = "lecturer.id";
    public static final String TITLE = "title";
    public static final String MULTIPLIER = "multiplier";
    public static final String HOURS = "hours";

    public SubjectSpecificationBuilder(Map<String, String> searchValues) {
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
        searchCriteria.add(new SearchCriterion<>("lecturer", "lecturerses", SearchCriterion.Operator.EQUAL, SearchCriterion.ValueType.LONG, "id"));
        searchCriteria.add(new SearchCriterion<>(TITLE, "title", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING));
        searchCriteria.add(new SearchCriterion<>(MULTIPLIER, "multiplier", SearchCriterion.Operator.EQUAL, SearchCriterion.ValueType.DOUBLE));
        searchCriteria.add(new SearchCriterion<>(HOURS, "hours", SearchCriterion.Operator.EQUAL, SearchCriterion.ValueType.INTEGER));

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
            return SubjectSortCriteria.valueOf(sortCriteria.toUpperCase()).getSort(sortOrder);
        } else {
            return SubjectSortCriteria.UNDEFINED.getSort("asc");
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
