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
    GROUP_TITLE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, StudentsSpecificationBuilder.GROUP_JOIN_TITLE);
            } else {
                return new Sort(Sort.Direction.DESC, StudentsSpecificationBuilder.GROUP_JOIN_TITLE);
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
    },
    USERNAME {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, StudentsSpecificationBuilder.USER_JOIN_USERNAME);
            } else {
                return new Sort(Sort.Direction.DESC, StudentsSpecificationBuilder.USER_JOIN_USERNAME);
            }
        }
    },
    EMAIL {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, StudentsSpecificationBuilder.USER_JOIN_EMAIL);
            } else {
                return new Sort(Sort.Direction.DESC, StudentsSpecificationBuilder.USER_JOIN_EMAIL);
            }
        }
    },
    PHONE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, StudentsSpecificationBuilder.USER_JOIN_PHONE);
            } else {
                return new Sort(Sort.Direction.DESC, StudentsSpecificationBuilder.USER_JOIN_PHONE);
            }
        }
    },
    ENABLE {
        public Sort getSort(String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return new Sort(Sort.Direction.ASC, StudentsSpecificationBuilder.USER_JOIN_ENABLE);
            } else {
                return new Sort(Sort.Direction.DESC, StudentsSpecificationBuilder.USER_JOIN_ENABLE);
            }
        }
    }
}
