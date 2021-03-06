package com.testing.edu.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.testing.edu.entity.Groups;
import com.testing.edu.entity.Students;
import com.testing.edu.entity.User;
import com.testing.edu.service.utils.ListToPageTransformer;

import java.util.Map;

public interface StudentsService {

    /**
     * Save student with user
     * @param lastname
     * @param firstname
     * @param middleName
     * @param numberGradebook
     * @param groups
     * @param username
     * @param email
     * @param phone
     */
    void addStudent(String lastname, String firstname, String middleName,
                    String numberGradebook, Groups groups, String username, String email, String phone, Boolean enable);

    /**
     * Save student with params
     * @param lastname
     * @param firstname
     * @param middleName
     * @param numberGradebook 
     */
    void addStudent(String lastname, String firstname, String middleName,
                     String numberGradebook, Groups groups);

    /**
     * Edit student with user
     * @param id
     * @param lastname
     * @param firstname
     * @param middleName
     * @param numberGradebook
     * @param groups
     * @param username
     * @param email
     * @param phone
     * @param password
     */
    void editStudent(Long id, String lastname, String firstname, String middleName, String numberGradebook, Groups groups,
                     String username, String email, String phone, String password, Boolean enable);

    /**
     * Edit student with params
     * @param id
     * @param lastname
     * @param firstname
     * @param middleName
     * @param numberGradebook
     */
    void editStudent(Long id, String lastname, String firstname, String middleName,
                     String numberGradebook, Groups groups);

    /**
     * Delete student by his id
     * @param id
     */
    void removeStudent(Long id);

    /**
     * Find student by id
     * @param id
     * @return
     */
    Students findById(Long id);

    /**
     * Service for building page by SortCriteria, SortOrder and Searching data
     * @param pageNumber
     * @param itemsPerPage
     * @param searchKeys
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    ListToPageTransformer<Students> getStudentBySearchAndPagination(int pageNumber, int itemsPerPage, Map<String, String> searchKeys,
                                                                      String sortCriteria, String sortOrder);

    /**
     * find Student by User
     * @param user
     * @return
     */
    Students findByUser(User user);
}
