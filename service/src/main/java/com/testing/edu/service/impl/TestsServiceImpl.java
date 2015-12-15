package com.testing.edu.service.impl;

import com.testing.edu.entity.*;
import com.testing.edu.entity.enumeration.TestType;
import com.testing.edu.repository.*;
import com.testing.edu.service.TestsService;
import com.testing.edu.service.specification.builder.TestSpecificationBuilder;
import com.testing.edu.service.utils.ListToPageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TestsServiceImpl implements TestsService {

    @Autowired
    private TestsRepository testsRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ResultsRepository resultsRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    /**
     * Save test with params
     *
     * @param title
     * @param type
     * @param maxGrade
     * @param time
     * @param avaible
     * @param subjectId
     */
    @Override
    @Transactional
    public void addTest(String title, String type, Integer maxGrade, Integer time, Boolean avaible, Long subjectId) {
        Tests test = new Tests(
                title,
                TestType.valueOf(type),
                maxGrade,
                time,
                avaible,
                subjectRepository.findOne(subjectId)
        );
        testsRepository.save(test);
    }

    /**
     * Edit test with params
     *
     * @param id
     * @param title
     * @param type
     * @param maxGrade
     * @param time
     * @param avaible
     * @param subjectId
     */
    @Override
    @Transactional
    public void editTest(Long id, String title, String type, Integer maxGrade, Integer time, Boolean avaible, Long subjectId) {
        Tests test = testsRepository.findOne(id);

        test.setTitle(title);
        test.setType(TestType.valueOf(type));
        test.setMaxGrade(maxGrade);
        test.setTime(time);
        test.setAvaible(avaible);
        test.setSubject(subjectRepository.findOne(subjectId));

        testsRepository.save(test);
    }

    /**
     * Delete test by his id
     *
     * @param id
     */
    @Override
    @Transactional
    public void removeTest(Long id) {
        Tests test = testsRepository.findOne(id);
        List<Questions> questionsList = new ArrayList<>(test.getQuestionses());
        for (Questions question : questionsList) {
            List<Answers> answerses = new ArrayList<>(question.getAnswerses());
            answerRepository.delete(answerses);
        }
        questionsRepository.delete(questionsList);
        resultsRepository.delete(test.getResults());
        testsRepository.delete(id);
    }

    /**
     * Find test by id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Tests findById(Long id) {
        return testsRepository.findOne(id);
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
    public ListToPageTransformer<Tests> getTestsBySearchAndPagination(int pageNumber, int itemsPerPage, Map<String, String> searchKeys, String sortCriteria, String sortOrder) {
        TestSpecificationBuilder specificationBuilder = new TestSpecificationBuilder(searchKeys);
        Pageable pageSpec = specificationBuilder.constructPageSpecification(pageNumber - 1, itemsPerPage, sortCriteria, sortOrder);
        Specification<Tests> searchSpec = specificationBuilder.buildPredicate();

        int totalSize = testsRepository.findAll(searchSpec).size();
        Page<Tests> testPage = testsRepository.findAll(searchSpec, pageSpec);
        List<Tests> tests = testPage.getContent();

        ListToPageTransformer<Tests> result = new ListToPageTransformer<>();
        result.setContent(tests);
        result.setTotalItems((long) totalSize);
        return result;
    }
}
