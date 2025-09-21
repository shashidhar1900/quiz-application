package com.shashi.quiz_service.repository;


import com.shashi.quiz_service.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    // Custom query methods can be defined here if needed




}
