package com.testing.edu.service;

import com.testing.edu.entity.Students;
import com.testing.edu.entity.User;
import com.testing.edu.service.utils.ListToPageTransformer;

import java.util.Map;

public interface StudentsService {
    /**
     * Save student with params
     * @param lastname
     * @param firstname
     * @param middleName
     * @param numberGradebook 
     */
    void addStudent(String lastname, String firstname, String middleName,
                     String numberGradebook);

    /**
     * Edit student with params
     * @param id
     * @param lastname
     * @param firstname
     * @param middleName
     * @param numberGradebook 
     */
    void editStudent(Long id, String lastname, String firstname, String middleName,
                     String numberGradebook);

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
