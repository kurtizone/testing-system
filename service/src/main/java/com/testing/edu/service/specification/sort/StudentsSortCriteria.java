package com.testing.edu.service.specification.sort;

import com.testing.edu.service.specification.builder.StudentsSpecificationBuilder;
import org.springframework.data.domain.Sort;

public enum StudentsSortCriteria implements SortCriteria {
    ID {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, StudentsSpecificationBuilder.ID);
            } else {
                return new Sort(Sort.Direction.DESC, StudentsSpecificationBuilder.ID);
            }
        }
    },
    UNDEFINED {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, StudentsSpecificationBuilder.LASTNAME);
            } else {
                return new Sort(Sort.Direction.DESC, StudentsSpecificationBuilder.LASTNAME);
            }
        }
    },
    LASTNAME {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, StudentsSpecificationBuilder.LASTNAME);
            } else {
                return new Sort(Sort.Direction.DESC, StudentsSpecificationBuilder.LASTNAME);
            }
        }
    },
    FIRSTNAME {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, StudentsSpecificationBuilder.FIRSTNAME);
            } else {
                return new Sort(Sort.Direction.DESC, StudentsSpecificationBuilder.FIRSTNAME);
            }
        }
    },
    MIDDLENAME {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, StudentsSpecificationBuilder.MIDDLENAME);
            } else {
                return new Sort(Sort.Direction.DESC, StudentsSpecificationBuilder.MIDDLENAME);
            }
        }
    },
    NUMBERGRADEBOOK {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, StudentsSpecificationBuilder.NUMBERGRADEBOOK);
            } else {
                return new Sort(Sort.Direction.DESC, StudentsSpecificationBuilder.NUMBERGRADEBOOK);
            }
        }
    }
}
