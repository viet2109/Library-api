package com.matcha.nlulibrary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    @JsonProperty("is_true")
    private boolean isTrue;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;


}
