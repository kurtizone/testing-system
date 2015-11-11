package com.testing.edu.service.specification.sort;

import com.testing.edu.service.specification.builder.ResultSpecificationBuilder;
import org.springframework.data.domain.Sort;

public enum ResultsSortCriteria implements SortCriteria{
    ID {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, ResultSpecificationBuilder.ID);
            } else {
                return new Sort(Sort.Direction.DESC, ResultSpecificationBuilder.ID);
            }
        }
    },
    UNDEFINED {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, ResultSpecificationBuilder.TEST_JOIN_SUBJECT_JOIN_TITLE);
            } else {
                return new Sort(Sort.Direction.DESC, ResultSpecificationBuilder.TEST_JOIN_SUBJECT_JOIN_TITLE);
            }
        }
    },
    STUDENT_FIRST_NAME {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, ResultSpecificationBuilder.STUDENT_JOIN_FIRSTNAME);
            } else {
                return new Sort(Sort.Direction.DESC, ResultSpecificationBuilder.STUDENT_JOIN_FIRSTNAME);
            }
        }
    },
    STUDENT_LAST_NAME {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, ResultSpecificationBuilder.STUDENT_JOIN_LASTNAME);
            } else {
                return new Sort(Sort.Direction.DESC, ResultSpecificationBuilder.STUDENT_JOIN_LASTNAME);
            }
        }
    },
    STUDENT_MIDDLE_NAME {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, ResultSpecificationBuilder.STUDENT_JOIN_MIDDLENAME);
            } else {
                return new Sort(Sort.Direction.DESC, ResultSpecificationBuilder.STUDENT_JOIN_MIDDLENAME);
            }
        }
    },
    STUDENT_GROUP_TITLE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, ResultSpecificationBuilder.STUDENT_JOIN_GROUP_JOIN_TITLE);
            } else {
                return new Sort(Sort.Direction.DESC, ResultSpecificationBuilder.STUDENT_JOIN_GROUP_JOIN_TITLE);
            }
        }
    },
    TEST_SUBJECT_TITLE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, ResultSpecificationBuilder.TEST_JOIN_SUBJECT_JOIN_TITLE);
            } else {
                return new Sort(Sort.Direction.DESC, ResultSpecificationBuilder.TEST_JOIN_SUBJECT_JOIN_TITLE);
            }
        }
    },
    MARK {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, ResultSpecificationBuilder.MARK);
            } else {
                return new Sort(Sort.Direction.DESC, ResultSpecificationBuilder.MARK);
            }
        }
    },
    MAX_GRADE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, ResultSpecificationBuilder.MAX_GRADE);
            } else {
                return new Sort(Sort.Direction.DESC, ResultSpecificationBuilder.MAX_GRADE);
            }
        }
    }
}
