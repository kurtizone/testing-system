package com.testing.edu.service.impl;

import com.testing.edu.entity.Answers;
import com.testing.edu.entity.Questions;
import com.testing.edu.entity.Tests;
import com.testing.edu.entity.enumeration.QuestionType;
import com.testing.edu.repository.AnswerRepository;
import com.testing.edu.repository.QuestionsRepository;
import com.testing.edu.repository.TestsRepository;
import com.testing.edu.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    private AnswerRepository answerRepository;

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
    @Transactional
    public void addQuestion(String text, String questionType, List<Answers> answerses, Tests test, Double gradeForQuestion) {
        recountGrades(test.getQuestionses(), test, gradeForQuestion);
        Questions questions = new Questions(
                text,
                QuestionType.valueOf(questionType),
                test);
        questionsRepository.save(questions);
        for (Answers answers : answerses) {
            answers.setQuestions(questions);
        }
        answerRepository.save(answerses);
        questions.setAnswerses(new HashSet<>(answerses));
        questionsRepository.save(questions);
        test.getQuestionses().add(questions);
        testsRepository.save(test);
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
    @Transactional
    public void editQuestion(Long id, String text, String questionType, List<Answers> answerses, Double gradeForQuestion) {
        Questions question = questionsRepository.findOne(id);
        Tests test = question.getTests();
        recountGrades(test.getQuestionses(), test, gradeForQuestion);
        for (Answers answers : answerses) {
            if(!answers.getId().equals(null)){
                Answers existAnswer = answerRepository.findOne(answers.getId());
                existAnswer.setGrade(answers.getGrade());
                existAnswer.setText(answers.getText());
                existAnswer.setQuestions(question);
                answerRepository.save(existAnswer);
            } else {
                answers.setQuestions(question);
                answerRepository.save(answers);
            }
        }
        question.setQuestionType(QuestionType.valueOf(questionType));
        question.setText(text);
        question.setAnswerses(new HashSet<>(answerses));
        questionsRepository.save(question);
    }

    /**
     * Delete question by his id
     *
     * @param id
     */
    @Override
    @Transactional
    public void removeQuestion(Long id) {
        Questions question = questionsRepository.findOne(id);
        Tests test = question.getTests();
        Set<Questions> questionses = test.getQuestionses();
        questionses.remove(question);
        test.setQuestionses(questionses);
        recountGrades(
                questionses,
                test,
                (double) test.getMaxGrade() / (questionses.size())
        );
        answerRepository.delete(question.getAnswerses());
        questionsRepository.delete(question);
    }

    /**
     * Find question by id
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Questions findById(Long id) {
        return questionsRepository.findOne(id);
    }

    public void recountGrades(Set<Questions> questionses, Tests test, Double gradeForQuestion){
        for (Questions questions : questionses) {
            int correctAnswers = 0;
            for (Answers answers : questions.getAnswerses()){
                if(answers.getGrade() > 0) {
                    correctAnswers++;
                }

            }
            Double grade = gradeForQuestion / correctAnswers;
            for (Answers answer : questions.getAnswerses()){
                if(answer.getGrade() > 0) {
                    answer.setGrade(grade);
                }

            }
            answerRepository.save(questions.getAnswerses());
        }
        questionsRepository.save(questionses);
        test.setQuestionses(questionses);
        testsRepository.save(test);
    }
}
