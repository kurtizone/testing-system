package com.testing.edu.service.impl;

import com.testing.edu.entity.Groups;
import com.testing.edu.entity.enumeration.Degree;
import com.testing.edu.entity.enumeration.StudyForm;
import com.testing.edu.repository.GroupsRepository;
import com.testing.edu.service.GroupsService;
import com.testing.edu.service.specification.builder.GroupsSpecificationBuilder;
import com.testing.edu.service.utils.ListToPageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Service
public class GroupsServiceImpl implements GroupsService {

    @Autowired
    private GroupsRepository groupsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save group with params
     *
     * @param title
     * @param grade
     * @param degree
     * @param studyForm
     */
    @Override
    @Transactional
    public void addGroup(String title, Integer grade, String degree, String studyForm) {
        Groups groups = new Groups(title, grade, Degree.valueOf(degree), StudyForm.valueOf(studyForm));
        groupsRepository.save(groups);
    }

    /**
     * Edit group with params
     *
     * @param id
     * @param title
     * @param grade
     * @param degree
     * @param studyForm
     */
    @Override
    @Transactional
    public void editGroup(Long id, String title, Integer grade, String degree, String studyForm) {
        Groups group = groupsRepository.findOne(id);
        group.setTitle(title);
        group.setGrade(grade);
        group.setDegree(Degree.valueOf(degree));
        group.setStudyForm(StudyForm.valueOf(studyForm));

        groupsRepository.save(group);
    }

    /**
     * Delete group by his id
     *
     * @param id
     */
    @Override
    @Transactional
    public void removeGroup(Long id) {
        groupsRepository.delete(id);
    }

    /**
     * Find group by id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Groups findById(Long id) {
        return groupsRepository.findOne(id);
    }

    /**
     * Service for building page by SortCriteria, SortOrder and Searching data
     *
     * @param pageNumber
     * @param itemsPerPage
     * @param searchKeys
     * @param sortCriteria
     * @param sortOrder
     * @return
     */
    @Override
    @Transactional
    public ListToPageTransformer<Groups> getGroupBySearchAndPagination(int pageNumber, int itemsPerPage, Map<String, String> searchKeys, String sortCriteria, String sortOrder) {

        GroupsSpecificationBuilder groupsSpecificationBuilder = new GroupsSpecificationBuilder(searchKeys);
        Pageable pageSpec = groupsSpecificationBuilder.constructPageSpecification(pageNumber - 1, itemsPerPage, sortCriteria, sortOrder);
        Specification<Groups> searchSpec = groupsSpecificationBuilder.buildPredicate();

        int totalItems = groupsRepository.findAll(searchSpec).size();
        Page<Groups> groupsPage = groupsRepository.findAll(searchSpec, pageSpec);
        List<Groups> groupses = groupsPage.getContent();

        ListToPageTransformer<Groups> result = new ListToPageTransformer<>();
        result.setContent(groupses);
        result.setTotalItems((long) totalItems);
        return result;
    }
}
