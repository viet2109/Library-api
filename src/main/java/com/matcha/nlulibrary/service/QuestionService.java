package com.matcha.nlulibrary.service;

import com.matcha.nlulibrary.dao.AnswerDAO;
import com.matcha.nlulibrary.dao.QuestionDAO;
import com.matcha.nlulibrary.entity.Answer;
import com.matcha.nlulibrary.entity.Question;
import com.matcha.nlulibrary.entity.User;
import com.matcha.nlulibrary.exception.UserNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionDAO questionDAO;
    private final AnswerDAO answerDAO;
    private  Authentication authentication;


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
        authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!authentication.getAuthorities().equals("ROLE_ADMIN")) {
//            throw new AccessDeniedException("Access denied: You do not have permission to perform this action.");
//        }
        System.out.println(authentication.getAuthorities());

        // Ensure that each answer has a reference to the question
        for (Answer answer : question.getAnswers()) {
            answer.setQuestion(question);
        }
        // add user has been created question
        if (authentication != null) {

            User currentUser =(User) authentication.getPrincipal();
                // Bây giờ bạn có thể làm việc với đối tượng User
                question.setCreateBy(currentUser);

        }
        // set time create question
        question.setCreateAt(LocalDateTime.now());


        return questionDAO.save(question);
    }

    public Question getQuestionById(int id) {
        return questionDAO.findById(id).orElseThrow(IndexOutOfBoundsException::new);
    }
}
