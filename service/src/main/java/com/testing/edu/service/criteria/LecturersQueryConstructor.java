package com.testing.edu.service.criteria;

import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.enumeration.AcademicStatus;
import com.testing.edu.entity.enumeration.Degree;
import com.testing.edu.service.criteria.sort.SortCriteriaLecturers;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class LecturersQueryConstructor {

    static Logger logger = Logger.getLogger(LecturersQueryConstructor.class);

    public static CriteriaQuery<Lecturers> buildSearchQuery(String lastName, String firstName, String middleName, String academicStatus,
                                                            String degree, String sortCriteria, String sortOrder, EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Lecturers> criteriaQuery = cb.createQuery(Lecturers.class);
        Root<Lecturers> root = criteriaQuery.from(Lecturers.class);

        Predicate predicate = LecturersQueryConstructor.buildPredicate(lastName, firstName, middleName, academicStatus, degree, root, cb);
        if((sortCriteria != null)&&(sortOrder != null)) {
            criteriaQuery.orderBy(SortCriteriaLecturers.valueOf(sortCriteria.toUpperCase()).getSortOrder(root, cb, sortOrder));
        } else {
            criteriaQuery.orderBy(cb.desc(root.get("id")));
        }
        criteriaQuery.select(root);
        criteriaQuery.where(predicate);
        return criteriaQuery;
    }

    public static CriteriaQuery<Long> buildCountQuery (String lastName, String firstName, String middleName,
                                                       String academicStatus, String degree, EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Lecturers> root = countQuery.from(Lecturers.class);

        Predicate predicate = LecturersQueryConstructor.buildPredicate(lastName, firstName, middleName, academicStatus, degree, root, cb);
        countQuery.select(cb.count(root));
        countQuery.where(predicate);

        return countQuery;
    }
    private static Predicate buildPredicate(String lastName, String firstName, String middleName, String academicStatus,
                                            String degree, Root<Lecturers> root, CriteriaBuilder cb) {
        Predicate queryPredicate = cb.conjunction();
        if ((lastName != null)&&(lastName.length()>0)) {
            queryPredicate = cb.and(cb.like(root.get("lastName"), "%" + lastName + "%"), queryPredicate);
        }
        if ((firstName != null)&&(firstName.length()>0)) {
            queryPredicate = cb.and(cb.like(root.get("firstName"), "%" + firstName + "%"), queryPredicate);
        }
        if ((middleName != null)&&(middleName.length()>0)) {
            queryPredicate = cb.and(cb.like(root.get("middleName"), "%" + middleName + "%"), queryPredicate);
        }
        if ((academicStatus != null)&&(academicStatus.length()>0)) {
            queryPredicate = cb.and(cb.equal(root.get("academicStatus"),
                    AcademicStatus.valueOf(academicStatus.trim())), queryPredicate);
        }
        if ((degree != null)&&(degree.length()>0)) {
            queryPredicate = cb.and(cb.equal(root.get("degree"),
                    Degree.valueOf(degree.trim())), queryPredicate);
        }
        return queryPredicate;
    }
}
