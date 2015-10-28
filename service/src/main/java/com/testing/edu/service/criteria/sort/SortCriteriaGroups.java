package com.testing.edu.service.criteria.sort;

import com.testing.edu.entity.Groups;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public enum SortCriteriaGroups {
    ID(){
        public Order getSortOrder(Root<Groups> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("id"));
            } else {
                return cb.desc(root.get("id"));
            }
        }
    },
    GRADE() {
        public Order getSortOrder(Root<Groups> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("grade"));
            } else {
                return cb.desc(root.get("grade"));
            }
        }
    },
    DEGREE() {
        public Order getSortOrder(Root<Groups> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("degree"));
            } else {
                return cb.desc(root.get("degree"));
            }
        }
    },
    STUDYFORM() {
        public Order getSortOrder(Root<Groups> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("studyForm"));
            } else {
                return cb.desc(root.get("studyForm"));
            }
        }
    };

    public Order getSortOrder(Root<Groups> root, CriteriaBuilder cb, String sortOrder) {
        return null;
    }
}
