package com.testing.edu.service.criteria.sort;


import com.testing.edu.entity.Students;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public enum SortCriteriaStudents {
    ID(){
        public Order getSortOrder(Root<Students> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("id"));
            } else {
                return cb.desc(root.get("id"));
            }
        }
    },
    LASTNAME() {
        public Order getSortOrder(Root<Students> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("lastName"));
            } else {
                return cb.desc(root.get("lastName"));
            }
        }
    },
    FIRSTNAME() {
        public Order getSortOrder(Root<Students> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("firstName"));
            } else {
                return cb.desc(root.get("firstName"));
            }
        }
    },
    MIDDLENAME() {
        public Order getSortOrder(Root<Students> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("middleName"));
            } else {
                return cb.desc(root.get("middleName"));
            }
        }
    },
    NUMBERGRADEBOOK() {
        public Order getSortOrder(Root<Students> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("numberGradebook"));
            } else {
                return cb.desc(root.get("numberGradebook"));
            }
        }
    };

    public Order getSortOrder(Root<Students> root, CriteriaBuilder cb, String sortOrder) {
        return null;
    }
}
