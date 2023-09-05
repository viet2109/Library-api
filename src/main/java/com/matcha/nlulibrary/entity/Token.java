package com.matcha.nlulibrary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tokens")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue
    private Integer id;
    private String token;
    private boolean expired;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
