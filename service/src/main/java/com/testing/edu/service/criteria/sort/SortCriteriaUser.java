package com.testing.edu.service.criteria.sort;


import com.testing.edu.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;


public enum SortCriteriaUser {
    ID(){
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("id"));
            } else {
                return cb.desc(root.get("id"));
            }
        }
    },
    USERNAME(){
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {

            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("username"));
            } else {
                return cb.desc(root.get("username"));
            }
        }
    },
    EMAIL() {
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("email"));
            } else {
                return cb.desc(root.get("email"));
            }
        }
    },
    ENABLE() {
        public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {
            if(sortOrder.equalsIgnoreCase("asc")) {
                return cb.asc(root.get("enable"));
            } else {
                return cb.desc(root.get("enable"));
            }
        }
    };
    public Order getSortOrder(Root<User> root, CriteriaBuilder cb, String sortOrder) {
        return null;
    }
}
