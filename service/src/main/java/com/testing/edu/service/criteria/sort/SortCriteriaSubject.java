package com.testing.edu.service.criteria.sort;


import com.testing.edu.entity.Subject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public enum SortCriteriaSubject {
    ID(){
        public Order getSortOrder(Root<Subject> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("id"));
            } else {
                return cb.desc(root.get("id"));
            }
        }
    },
    TITLE() {
        public Order getSortOrder(Root<Subject> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("title"));
            } else {
                return cb.desc(root.get("title"));
            }
        }
    },
    MULTIPLIER() {
        public Order getSortOrder(Root<Subject> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("multiplier"));
            } else {
                return cb.desc(root.get("multiplier"));
            }
        }
    },
    HOURS() {
        public Order getSortOrder(Root<Subject> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("hours"));
            } else {
                return cb.desc(root.get("hours"));
            }
        }
    };

    public Order getSortOrder(Root<Subject> root, CriteriaBuilder cb, String sortOrder) {
        return null;
    }
}
