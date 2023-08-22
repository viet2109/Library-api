package com.matcha.nlulibrary.service;

import com.matcha.nlulibrary.dao.AnswerDAO;
import com.matcha.nlulibrary.dao.QuestionDAO;
import com.matcha.nlulibrary.entity.Answer;
import com.matcha.nlulibrary.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionDAO questionDAO;
    private final AnswerDAO answerDAO;


    public List<Question> getAllQuestion(String category) {
        // if the category exist return list based on category
        System.out.println(category != null);
        if (category != null){
            return getQuestionByCategory(category);
        }
        return questionDAO.findAll();
    }

    private List<Question> getQuestionByCategory(String category) {
        return questionDAO.findByCategory(category).orElseThrow(IndexOutOfBoundsException::new);
    }

    public Question addQuestion(Question question) {
        // Ensure that each answer has a reference to the question
        for (Answer answer : question.getAnswers()) {
            answer.setQuestion(question);
        }
        return questionDAO.save(question);
    }

    public Question getQuestionById(int id) {
        return questionDAO.findById(id).orElseThrow(IndexOutOfBoundsException::new);
    }
}
