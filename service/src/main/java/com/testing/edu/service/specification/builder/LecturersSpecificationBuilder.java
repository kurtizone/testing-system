package com.testing.edu.service.specification.builder;

import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.enumeration.AcademicStatus;
import com.testing.edu.entity.enumeration.Degree;
import com.testing.edu.service.specification.SearchCriterion;
import com.testing.edu.service.specification.SpecificationBuilder;
import com.testing.edu.service.specification.sort.LecturersSortCriteria;
import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LecturersSpecificationBuilder extends SpecificationBuilder<Lecturers> {

    static Logger logger = Logger.getLogger(SubjectSpecificationBuilder.class);

    public static final String ID = "id";
    public static final String LASTNAME = "lastName";
    public static final String FIRSTNAME = "firstName";
    public static final String MIDDLENAME = "middleName";
    public static final String ACADEMICSTATUS = "academicStatus";
    public static final String DEGREE = "degree";

    public LecturersSpecificationBuilder(Map<String, String> searchValues) {
        super(searchValues);
    }

    /**
     * Initialize criteria for Lecturers Specification
     * @return search criteria
     */
    @Override
    protected List<SearchCriterion> initCriteria(){
        List<SearchCriterion> searchCriteria = new ArrayList<>();
        searchCriteria.add(new SearchCriterion<>(LASTNAME, "lastName", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING));
        searchCriteria.add(new SearchCriterion<>(FIRSTNAME, "firstName", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING));
        searchCriteria.add(new SearchCriterion<>(MIDDLENAME, "middleName", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING));
        searchCriteria.add(new SearchCriterion<>(ACADEMICSTATUS, "academicStatus", SearchCriterion.Operator.EQUAL_BY_ENUM, AcademicStatus.class));
        searchCriteria.add(new SearchCriterion<>(DEGREE, "degree", SearchCriterion.Operator.EQUAL_BY_ENUM, Degree.class));
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
            return LecturersSortCriteria.valueOf(sortCriteria.toUpperCase()).getSort(sortOrder);
        } else {
            return LecturersSortCriteria.UNDEFINED.getSort("asc");
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
