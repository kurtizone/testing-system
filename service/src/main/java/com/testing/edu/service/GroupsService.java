package com.testing.edu.service;


import com.testing.edu.entity.Groups;
import com.testing.edu.entity.Lecturers;
import com.testing.edu.service.utils.ListToPageTransformer;

import java.util.List;
import java.util.Map;

public interface GroupsService {

    /**
     * Save group with params
     * @param title
     * @param grade
     * @param degree
     * @param studyForm
     */
    void addGroup(String title, Integer grade, String degree, String studyForm);

    /**
     * Edit group with params
     * @param id
     * @param title
     * @param grade
     * @param degree
     * @param studyForm
     */
    void editGroup(Long id, String title, Integer grade, String degree, String studyForm);

    /**
     * Delete group by his id
     * @param id
     */
    void removeGroup(Long id);

    /**
     * Delete subject by his id
     * @param groupId
     * @param subjectId
     */
    void removeSubjectOfGroup(Long groupId, Long subjectId);

    /**
     * Find group by id
     * @param id
     * @return
     */
    Groups findById(Long id);

    /**
     * Service for building page by SortCriteria, SortOrder and Searching data
     * @param pageNumber
     * @param itemsPerPage
     * @param searchKeys
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    ListToPageTransformer<Groups> getGroupBySearchAndPagination(int pageNumber, int itemsPerPage,
                                                                   Map<String, String> searchKeys,
                                                                   String sortCriteria, String sortOrder);

    /**
     * get all groups
     * @return list of groups
     */
    List<Groups> getAllGroups ();

    /**
     * add subject to exsting group
     * @param groupId
     * @param subjectId
     */
    void addSubject(Long groupId, Long subjectId);
}

