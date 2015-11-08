package com.testing.edu.service.specification.sort;

import com.testing.edu.service.specification.builder.TestSpecificationBuilder;
import org.springframework.data.domain.Sort;

public enum TestsSortCriteria implements SortCriteria {
    ID {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, TestSpecificationBuilder.ID);
            } else {
                return new Sort(Sort.Direction.DESC, TestSpecificationBuilder.ID);
            }
        }
    },
    UNDEFINED {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, TestSpecificationBuilder.TITLE);
            } else {
                return new Sort(Sort.Direction.DESC, TestSpecificationBuilder.TITLE);
            }
        }
    },
    TITLE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, TestSpecificationBuilder.TITLE);
            } else {
                return new Sort(Sort.Direction.DESC, TestSpecificationBuilder.TITLE);
            }
        }
    },
    SUBJECT {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, TestSpecificationBuilder.SUBJECT);
            } else {
                return new Sort(Sort.Direction.DESC, TestSpecificationBuilder.SUBJECT);
            }
        }
    },
    TYPE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, TestSpecificationBuilder.TYPE);
            } else {
                return new Sort(Sort.Direction.DESC, TestSpecificationBuilder.TYPE);
            }
        }
    },
    MAXGRADE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, TestSpecificationBuilder.MAXGRADE);
            } else {
                return new Sort(Sort.Direction.DESC, TestSpecificationBuilder.MAXGRADE);
            }
        }
    },
    AVAIBLE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, TestSpecificationBuilder.AVAIBLE);
            } else {
                return new Sort(Sort.Direction.DESC, TestSpecificationBuilder.AVAIBLE);
            }
        }
    }
}

