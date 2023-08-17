package com.matcha.nlulibrary.service;

import com.matcha.nlulibrary.dao.QuestionDAO;
import com.matcha.nlulibrary.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private QuestionDAO questionDAO;
    @Autowired
    public QuestionService(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public List<Question> getAllQuestion() {
        return questionDAO.findAll();
    }

    public List<Question> getQuestionByCategory(String category) {
        return questionDAO.findByCategory(category);
    }

    public Question addQuestion(Question question) {
        return questionDAO.save(question);
    }
}
