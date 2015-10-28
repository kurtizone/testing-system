package com.testing.edu.service.criteria.sort;


import com.testing.edu.entity.Tests;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public enum SortCriteriaTest {
    ID(){
        public Order getSortOrder(Root<Tests> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("id"));
            } else {
                return cb.desc(root.get("id"));
            }
        }
    },
    TITLE(){
        public Order getSortOrder(Root<Tests> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("title"));
            } else {
                return cb.desc(root.get("title"));
            }
        }
    },
    TYPE() {
        public Order getSortOrder(Root<Tests> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("type"));
            } else {
                return cb.desc(root.get("type"));
            }
        }
    },
    MAXGRADE() {
        public Order getSortOrder(Root<Tests> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("maxGrade"));
            } else {
                return cb.desc(root.get("maxGrade"));
            }
        }
    },
    AVAIBLE() {
        public Order getSortOrder(Root<Tests> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("avaible"));
            } else {
                return cb.desc(root.get("avaible"));
            }
        }
    };
    public Order getSortOrder(Root<Tests> root, CriteriaBuilder cb, String sortOrder) {
        return null;
    }
}
