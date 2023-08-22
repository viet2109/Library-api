package com.matcha.nlulibrary.dao;

import com.matcha.nlulibrary.entity.Answer;
import com.matcha.nlulibrary.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerDAO extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestion(Question question);
}
