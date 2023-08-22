package com.matcha.nlulibrary.controller;

import com.matcha.nlulibrary.entity.Question;
import com.matcha.nlulibrary.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {
    private QuestionService questionService;
    // add inject by constructor
    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
    // get all videos
    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestion(@RequestParam(value = "category", required = false) String category){
        return ResponseEntity.ok(questionService.getAllQuestion(category));
    }
    // Get a single question
    @GetMapping("/questions/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable int id){
        return ResponseEntity.ok(questionService.getQuestionById(id));

    }


//    @GetMapping("/category/{category}")
//    public ResponseEntity<List<QuestionDTO>> getQuestionByCategory(@PathVariable String category){
//        return ResponseEntity.ok(questionService.getQuestionByCategory(category));
//    }
    @PostMapping("/admin/questions")
    public Question addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }
    // api to check question
//    @PostMapping("/answer")
//    public ResponseEntity<>


}
