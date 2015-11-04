package com.testing.edu.service.specification.builder;

import com.testing.edu.entity.Groups;
import com.testing.edu.entity.enumeration.Degree;
import com.testing.edu.entity.enumeration.StudyForm;
import com.testing.edu.service.specification.SearchCriterion;
import com.testing.edu.service.specification.SpecificationBuilder;
import com.testing.edu.service.specification.sort.GroupsSortCriteria;
import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupsSpecificationBuilder extends SpecificationBuilder<Groups> {

    static Logger logger = Logger.getLogger(GroupsSpecificationBuilder.class);

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String GRADE = "grade";
    public static final String DEGREE = "degree";
    public static final String STUDYFORM = "studyForm";

    public GroupsSpecificationBuilder(Map<String, String> searchValues) {
        super(searchValues);
    }

    /**
     * Initialize criteria for Groups Specification
     * @return search criteria
     */
    @Override
    protected List<SearchCriterion> initCriteria(){
        List<SearchCriterion> searchCriteria = new ArrayList<>();
        searchCriteria.add(new SearchCriterion<>(TITLE, "title", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING));
        searchCriteria.add(new SearchCriterion<>(GRADE, "grade", SearchCriterion.Operator.EQUAL, SearchCriterion.ValueType.INTEGER));
        searchCriteria.add(new SearchCriterion<>(DEGREE, "degree", SearchCriterion.Operator.EQUAL_BY_ENUM, Degree.class));
        searchCriteria.add(new SearchCriterion<>(STUDYFORM, "studyForm", SearchCriterion.Operator.EQUAL_BY_ENUM, StudyForm.class));
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
            return GroupsSortCriteria.valueOf(sortCriteria.toUpperCase()).getSort(sortOrder);
        } else {
            return GroupsSortCriteria.UNDEFINED.getSort("asc");
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
