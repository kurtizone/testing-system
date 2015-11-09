package com.testing.edu.service.impl;

import com.testing.edu.entity.Answers;
import com.testing.edu.entity.Questions;
import com.testing.edu.entity.Tests;
import com.testing.edu.repository.QuestionsRepository;
import com.testing.edu.repository.TestsRepository;
import com.testing.edu.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private TestsRepository testsRepository;

    /**
     * Save question with params
     *
     * @param text
     * @param questionType
     * @param answerses
     */
    @Override
    public void addQuestion(String text, String questionType, List<Answers> answerses) {

    }

    /**
     * Edit question with params
     *
     * @param id
     * @param text
     * @param questionType
     * @param answerses
     */
    @Override
    public void editQuestion(Long id, String text, String questionType, List<Answers> answerses) {

    }

    /**
     * Delete question by his id
     *
     * @param id
     */
    @Override
    public void removeQuestion(Long id) {

    }

    /**
     * Find question by id
     *
     * @param id
     * @return
     */
    @Override
    public Questions findById(Long id) {
        return null;
    }
}
