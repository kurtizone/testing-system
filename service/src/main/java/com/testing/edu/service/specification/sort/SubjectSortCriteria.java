package com.testing.edu.service.specification.sort;

import com.testing.edu.service.specification.builder.SubjectSpecificationBuilder;
import org.springframework.data.domain.Sort;


public enum SubjectSortCriteria implements SortCriteria{
    ID {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, SubjectSpecificationBuilder.ID);
            } else {
                return new Sort(Sort.Direction.DESC, SubjectSpecificationBuilder.ID);
            }
        }
    },
    UNDEFINED {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, SubjectSpecificationBuilder.TITLE);
            } else {
                return new Sort(Sort.Direction.DESC, SubjectSpecificationBuilder.TITLE);
            }
        }
    },
    TITLE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, SubjectSpecificationBuilder.TITLE);
            } else {
                return new Sort(Sort.Direction.DESC, SubjectSpecificationBuilder.TITLE);
            }
        }
    },
    MULTIPLIER {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, SubjectSpecificationBuilder.MULTIPLIER);
            } else {
                return new Sort(Sort.Direction.DESC, SubjectSpecificationBuilder.MULTIPLIER);
            }
        }
    },
    HOURS {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, SubjectSpecificationBuilder.HOURS);
            } else {
                return new Sort(Sort.Direction.DESC, SubjectSpecificationBuilder.HOURS);
            }
        }
    }
}
