package com.matcha.nlulibrary.controller;

import com.matcha.nlulibrary.entity.Question;
import com.matcha.nlulibrary.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private QuestionService questionService;
    // add inject by constructor
    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("")
    public List<Question> getAllQuestion(){
        return questionService.getAllQuestion();
    }
    @GetMapping("/category/{category}")
    public List<Question> getQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionByCategory(category);
    }
    @PostMapping("")
    public Question addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

}
