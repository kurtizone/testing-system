package com.testing.edu.service.specification.sort;

import com.testing.edu.service.specification.builder.LecturersSpecificationBuilder;
import org.springframework.data.domain.Sort;

public enum LecturersSortCriteria implements SortCriteria {
    ID {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, LecturersSpecificationBuilder.ID);
            } else {
                return new Sort(Sort.Direction.DESC, LecturersSpecificationBuilder.ID);
            }
        }
    },
    UNDEFINED {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, LecturersSpecificationBuilder.LASTNAME);
            } else {
                return new Sort(Sort.Direction.DESC, LecturersSpecificationBuilder.LASTNAME);
            }
        }
    },
    LASTNAME {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, LecturersSpecificationBuilder.LASTNAME);
            } else {
                return new Sort(Sort.Direction.DESC, LecturersSpecificationBuilder.LASTNAME);
            }
        }
    },
    FIRSTNAME {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, LecturersSpecificationBuilder.FIRSTNAME);
            } else {
                return new Sort(Sort.Direction.DESC, LecturersSpecificationBuilder.FIRSTNAME);
            }
        }
    },
    MIDDLENAME {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, LecturersSpecificationBuilder.MIDDLENAME);
            } else {
                return new Sort(Sort.Direction.DESC, LecturersSpecificationBuilder.MIDDLENAME);
            }
        }
    },
    ACADEMICSTATUS {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, LecturersSpecificationBuilder.ACADEMICSTATUS);
            } else {
                return new Sort(Sort.Direction.DESC, LecturersSpecificationBuilder.ACADEMICSTATUS);
            }
        }
    },
    DEGREE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, LecturersSpecificationBuilder.DEGREE);
            } else {
                return new Sort(Sort.Direction.DESC, LecturersSpecificationBuilder.DEGREE);
            }
        }
    }
}
