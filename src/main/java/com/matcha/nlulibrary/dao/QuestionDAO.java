package com.matcha.nlulibrary.dao;

import com.matcha.nlulibrary.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionDAO extends JpaRepository<Question, Integer> {
    Optional<List<Question>> findByCategory(String category);
}
