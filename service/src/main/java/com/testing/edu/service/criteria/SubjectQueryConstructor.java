package com.testing.edu.service.criteria;


import com.testing.edu.entity.Subject;
import com.testing.edu.service.criteria.sort.SortCriteriaSubject;
import org.apache.log4j.Logger;
import com.testing.edu.service.utils.*;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SubjectQueryConstructor {

    static Logger logger = Logger.getLogger(SubjectQueryConstructor.class);

    public static CriteriaQuery<Subject> buildSearchQuery(String title, Float multiplier, Integer hours,
                                                              String sortCriteria, String sortOrder, EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Subject> criteriaQuery = cb.createQuery(Subject.class);
        Root<Subject> root = criteriaQuery.from(Subject.class);

        Predicate predicate = SubjectQueryConstructor.buildPredicate(title, multiplier, hours, root, cb);
        if((sortCriteria != null)&&(sortOrder != null)) {
            criteriaQuery.orderBy(SortCriteriaSubject.valueOf(sortCriteria.toUpperCase()).getSortOrder(root, cb, sortOrder));
        } else {
            criteriaQuery.orderBy(cb.desc(root.get("title")));
        }
        criteriaQuery.select(root);
        criteriaQuery.where(predicate);
        return criteriaQuery;
    }

    public static CriteriaQuery<Long> buildCountQuery (String title, Float multiplier, Integer hours, EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Subject> root = countQuery.from(Subject.class);

        Predicate predicate = SubjectQueryConstructor.buildPredicate(title, multiplier, hours, root, cb);
        countQuery.select(cb.count(root));
        countQuery.where(predicate);

        return countQuery;
    }
    private static Predicate buildPredicate(String title, Float multiplier, Integer hours,
                                            Root<Subject> root, CriteriaBuilder cb) {
        Predicate queryPredicate = cb.conjunction();
        if ((title != null)&&(title.length()>0)) {
            queryPredicate = cb.and(
                    cb.like(root.get("title"), "%" + title + "%"),
                    queryPredicate);
        }
        if (multiplier != null) {
            queryPredicate = cb.and(cb.like(new FilteringNumbersDataLikeStringData<Float>(cb, root.get("multiplier")),
                    "%" + multiplier.toString() + "%"), queryPredicate);
        }
        if (hours != null) {
            queryPredicate = cb.and(cb.like(new FilteringNumbersDataLikeStringData<Long>(cb, root.get("hours")),
                    "%" + hours.toString() + "%"), queryPredicate);
        }

        return queryPredicate;
    }
}
