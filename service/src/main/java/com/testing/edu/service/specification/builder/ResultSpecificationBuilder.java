package com.testing.edu.service.specification.builder;


import com.testing.edu.entity.Result;
import com.testing.edu.entity.enumeration.TestType;
import com.testing.edu.service.specification.SearchCriterion;
import com.testing.edu.service.specification.SpecificationBuilder;
import com.testing.edu.service.specification.sort.ResultsSortCriteria;
import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultSpecificationBuilder extends SpecificationBuilder<Result>{

    static Logger logger = Logger.getLogger(ResultSpecificationBuilder.class);

    public static final String ID = "id";
    public static final String STUDENT_JOIN_FIRSTNAME = "students.firstName";
    public static final String STUDENT_FIRSTNAME = "studentFirstname";
    public static final String STUDENT = "students";
    public static final String STUDENT_GROUP = "studentsGroup";
    public static final String STUDENT_JOIN_LASTNAME = "students.lastName";
    public static final String STUDENT_LASTNAME = "studentLastname";
    public static final String STUDENT_JOIN_MIDDLENAME = "students.middleName";
    public static final String STUDENT_MIDDLENAME = "studentMiddlename";
    public static final String STUDENT_JOIN_GROUP_JOIN_TITLE = "students.groups.title";
    public static final String GROUP_TITLE = "groupTitle";
    public static final String TEST_JOIN_SUBJECT_JOIN_TITLE = "tests.subject.title";
    public static final String SUBJECT_TITLE = "subjectTitle";
    public static final String TEST_TITLE = "testTitle";
    public static final String TEST_JOIN_TEST_TYPE = "tests.type";
    public static final String TEST_TYPE = "testType";
    public static final String MARK = "mark";
    public static final String MAX_GRADE = "maxGrade";

    public ResultSpecificationBuilder(Map<String, String> searchValues) {
        super(searchValues);
    }

    /**
     * Initialize criteria for Agreement Specification
     * @return search criteria
     */
    @Override
    protected List<SearchCriterion> initCriteria(){
        List<SearchCriterion> searchCriteria = new ArrayList<>();
        searchCriteria.add(new SearchCriterion<>(STUDENT, "students", SearchCriterion.Operator.EQUAL, SearchCriterion.ValueType.LONG, "id"));
        searchCriteria.add(new SearchCriterion<>(STUDENT_GROUP, "students", SearchCriterion.Operator.EQUAL, SearchCriterion.ValueType.LONG, "groups", "id"));
        searchCriteria.add(new SearchCriterion<>(STUDENT_FIRSTNAME, "students", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING, "firstName"));
        searchCriteria.add(new SearchCriterion<>(STUDENT_LASTNAME, "students", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING, "lastName"));
        searchCriteria.add(new SearchCriterion<>(STUDENT_MIDDLENAME, "students", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING, "middleName"));
        searchCriteria.add(new SearchCriterion<>(GROUP_TITLE, "students", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING, "groups", "title"));
        searchCriteria.add(new SearchCriterion<>(SUBJECT_TITLE, "tests", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING, "subject", "title"));
        searchCriteria.add(new SearchCriterion<>(TEST_TITLE, "testTitle", SearchCriterion.Operator.LIKE, SearchCriterion.ValueType.STRING));
        searchCriteria.add(new SearchCriterion<>(TEST_TYPE, "tests", SearchCriterion.Operator.EQUAL_BY_ENUM, TestType.class, "type"));
        searchCriteria.add(new SearchCriterion<>(MARK, "mark", SearchCriterion.Operator.EQUAL, SearchCriterion.ValueType.DOUBLE));
        searchCriteria.add(new SearchCriterion<>(MAX_GRADE, "maxGrade", SearchCriterion.Operator.EQUAL, SearchCriterion.ValueType.INTEGER));
        return searchCriteria;
    }

    @Override
    public Sort getSort(String sortCriteria, String sortOrder) {
        if (checkValue(sortCriteria) && checkValue(sortOrder)) {
            return ResultsSortCriteria.valueOf(sortCriteria.toUpperCase()).getSort(sortOrder);
        } else {
            return ResultsSortCriteria.UNDEFINED.getSort("asc");
        }
    }

    @Override
    public Pageable constructPageSpecification(int pageNumber, int itemsPerPage, String sortCriteria, String sortOrder) {
        return new PageRequest(pageNumber, itemsPerPage, getSort(sortCriteria, sortOrder));
    }
}
