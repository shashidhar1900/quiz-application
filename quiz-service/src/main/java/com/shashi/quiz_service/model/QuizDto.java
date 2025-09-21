package com.shashi.quiz_service.model;

import lombok.Data;

@Data
public class QuizDto {
    private String topic;
    private int numberOfQuestions;
    private  String title;
}
