package com.testing.edu.service.specification.sort;

import com.testing.edu.service.specification.builder.GroupsSpecificationBuilder;
import org.springframework.data.domain.Sort;

public enum GroupsSortCriteria implements SortCriteria {

    ID {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, GroupsSpecificationBuilder.ID);
            } else {
                return new Sort(Sort.Direction.DESC, GroupsSpecificationBuilder.ID);
            }
        }
    },
    UNDEFINED {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, GroupsSpecificationBuilder.TITLE);
            } else {
                return new Sort(Sort.Direction.DESC, GroupsSpecificationBuilder.TITLE);
            }
        }
    },
    TITLE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, GroupsSpecificationBuilder.TITLE);
            } else {
                return new Sort(Sort.Direction.DESC, GroupsSpecificationBuilder.TITLE);
            }
        }
    },
    GRADE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, GroupsSpecificationBuilder.GRADE);
            } else {
                return new Sort(Sort.Direction.DESC, GroupsSpecificationBuilder.GRADE);
            }
        }
    },
    DEGREE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, GroupsSpecificationBuilder.DEGREE);
            } else {
                return new Sort(Sort.Direction.DESC, GroupsSpecificationBuilder.DEGREE);
            }
        }
    },
    STUDYFORM {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, GroupsSpecificationBuilder.STUDYFORM);
            } else {
                return new Sort(Sort.Direction.DESC, GroupsSpecificationBuilder.STUDYFORM);
            }
        }
    }
}
