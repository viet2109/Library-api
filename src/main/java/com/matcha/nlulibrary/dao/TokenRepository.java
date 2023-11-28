package com.matcha.nlulibrary.dao;

import com.matcha.nlulibrary.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
    select t from Token t join User u on t.user.id = u.id
    where u.id = :userId and (t.expired = false)
""")
    List<Token> findAllValidTokensByUser(@Param("userId") Integer userId);
    Optional<Token> findByToken(String token);
}
