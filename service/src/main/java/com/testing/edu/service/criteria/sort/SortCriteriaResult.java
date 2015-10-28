package com.testing.edu.service.criteria.sort;


import com.testing.edu.entity.Result;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public enum SortCriteriaResult {
    ID(){
        public Order getSortOrder(Root<Result> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("id"));
            } else {
                return cb.desc(root.get("id"));
            }
        }
    },
    TESTTITLE() {
        public Order getSortOrder(Root<Result> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("testTitle"));
            } else {
                return cb.desc(root.get("testTitle"));
            }
        }
    },
    MARK() {
        public Order getSortOrder(Root<Result> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("mark"));
            } else {
                return cb.desc(root.get("mark"));
            }
        }
    },
    MAXGRADE() {
        public Order getSortOrder(Root<Result> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("maxGrade"));
            } else {
                return cb.desc(root.get("maxGrade"));
            }
        }
    };

    public Order getSortOrder(Root<Result> root, CriteriaBuilder cb, String sortOrder) {
        return null;
    }
}
