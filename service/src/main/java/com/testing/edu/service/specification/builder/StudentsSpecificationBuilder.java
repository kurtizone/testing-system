package com.testing.edu.service.specification.builder;

import com.testing.edu.entity.Students;
import com.testing.edu.entity.enumeration.Degree;
import com.testing.edu.service.specification.SearchCriterion;
import com.testing.edu.service.specification.SpecificationBuilder;
import com.testing.edu.service.specification.sort.StudentsSortCriteria;
import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentsSpecificationBuilder extends SpecificationBuilder<Students> {

    static Logger logger = Logger.getLogger(StudentsSpecificationBuilder.class);

    public static final String ID = "id";
    public static final String LASTNAME = "lastName";
    public static final String FIRSTNAME = "firstName";
    public static final String MIDDLENAME = "middleName";
    public static final String NUMBERGRADEBOOK = "numberGradebook";
    public static final String GROUP_TITLE = "groupTitle";
    public static final String GROUP_JOIN_TITLE = "groups.title";

    public StudentsSpecificationBuilder(Map<String, String> searchValues) {
        super(searchValues);
    }

    /**
     * Initialize criteria for Students Specification
     * @return search criteria
     */
    @Override
    protected List<SearchCriterion> initCriteria(){
        List<SearchCriterion> searchCriteria = new ArrayList<>();
        searchCriteria.add(new SearchCriterion<>(GROUP_TITLE, "groups", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING, "title"));
        searchCriteria.add(new SearchCriterion<>(LASTNAME, "lastName", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING));
        searchCriteria.add(new SearchCriterion<>(FIRSTNAME, "firstName", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING));
        searchCriteria.add(new SearchCriterion<>(MIDDLENAME, "middleName", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING));
        searchCriteria.add(new SearchCriterion<>(NUMBERGRADEBOOK, "numberGradebook", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING));
        for (SearchCriterion searchCriterion : searchCriteria) {
            logger.info("-----searchCriteria------ "  + searchCriterion);
        }
        return searchCriteria;
    }

    /**
     * Create Sort object for Specification executor by criteria and order
     * @param sortCriteria sorting criterion
     * @param sortOrder sorting order
     * @return Sort object
     */
    @Override
    public Sort getSort(String sortCriteria, String sortOrder) {
        if (checkValue(sortCriteria) && checkValue(sortOrder)) {
            return StudentsSortCriteria.valueOf(sortCriteria.toUpperCase()).getSort(sortOrder);
        } else {
            return StudentsSortCriteria.UNDEFINED.getSort("asc");
        }
    }

    /**
     * Creates a new PageRequest with sort parameters applied.
     * @param pageNumber zero-based page index.
     * @param itemsPerPage the size of the page to be returned.
     * @param sortCriteria sorting criterion
     * @param sortOrder sorting order
     * @return
     */
    @Override
    public Pageable constructPageSpecification(int pageNumber, int itemsPerPage, String sortCriteria, String sortOrder) {
        return new PageRequest(pageNumber, itemsPerPage, getSort(sortCriteria, sortOrder));
    }

}
