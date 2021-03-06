package com.testing.edu.service;


import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.User;
import com.testing.edu.service.utils.ListToPageTransformer;

import java.util.List;
import java.util.Map;

public interface LecturersService {

    /**
     * Save lecturer with user
     * @param lastname
     * @param firstname
     * @param middleName
     * @param academicStatus
     * @param degree
     * @param username
     * @param email
     * @param phone
     */
    void addLecturer(String lastname, String firstname, String middleName,
                     String academicStatus, String degree, String username, String email, String phone, Boolean enable);

    /**
     * Save lecturer with params
     * @param lastname
     * @param firstname
     * @param middleName
     * @param academicStatus
     * @param degree
     */
    void addLecturer(String lastname, String firstname, String middleName,
                     String academicStatus, String degree);


    /**
     * Edit lecturer with user
     * @param id
     * @param lastname
     * @param firstname
     * @param middleName
     * @param academicStatus
     * @param degree
     * @param username
     * @param email
     * @param phone
     * @param password
     */
    void editLecturer(Long id, String lastname, String firstname, String middleName, String academicStatus, String degree,
                      String username, String email, String phone, String password, Boolean enable);
    /**
     * Edit lecturer with params
     * @param id
     * @param lastname
     * @param firstname
     * @param middleName
     * @param academicStatus
     * @param degree
     */
    void editLecturer(Long id, String lastname, String firstname, String middleName,
                      String academicStatus, String degree);

    /**
     * Delete lecturer by his id
     * @param id
     */
    void removeLecturer(Long id);

    /**
     * Delete lecturer by his id
     * @param lecturerId
     * @param subjectId
     */
    void removeSubjectOfLecturer(Long lecturerId, Long subjectId);

    /**
     * Find lecturer by id
     * @param id
     * @return
     */
    Lecturers findById(Long id);

    /**
     * Service for building page by SortCriteria, SortOrder and Searching data
     * @param pageNumber
     * @param itemsPerPage
     * @param
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    ListToPageTransformer<Lecturers> getLecturerBySearchAndPagination(int pageNumber, int itemsPerPage, Map<String, String> searchDataMap,
                                                                      String sortCriteria, String sortOrder);

    /**
     * find Lecturer by User
     * @param user
     * @return
     */
    Lecturers findByUser(User user);

    /**
     * Get all lecturers
     * @return List of lecturers
     */
    List<Lecturers> getAllLecturers();

    /**
     * add new subject for lecturer
     * @param lecturerId
     * @param subjectId
     */
    void addSubject(Long lecturerId, Long subjectId);
}
