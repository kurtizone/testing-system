package com.testing.edu.service;


import com.testing.edu.entity.Answers;
import com.testing.edu.entity.Questions;

import java.util.List;

public interface QuestionsService {

    /**
     * Save question with params
     * @param text
     * @param questionType
     * @param answerses
     */
    void addQuestion(String text, String questionType, List<Answers> answerses);

    /**
     * Edit question with params
     * @param id
     * @param text
     * @param questionType
     * @param answerses
     */
    void editQuestion(Long id, String text, String questionType, List<Answers> answerses);

    /**
     * Delete question by his id
     * @param id
     */
    void removeQuestion(Long id);

    /**
     * Find question by id
     * @param id
     * @return
     */
    Questions findById(Long id);
}
