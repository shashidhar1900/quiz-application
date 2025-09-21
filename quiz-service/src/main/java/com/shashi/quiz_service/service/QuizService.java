package com.shashi.quiz_service.service;



import com.shashi.quiz_service.feign.QuizInterface;
import com.shashi.quiz_service.model.*;
import com.shashi.quiz_service.repository.QuizRepository;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepo;

    @Autowired
    private QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numberOfQuestions,String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numberOfQuestions).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizRepo.save(quiz);

        return new ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(Integer id) {
        Optional<Quiz> quiz= quizRepo.findById(id);
        if(quiz.isPresent()){
            List<Integer> questionIds = quiz.get().getQuestionIds();
            return new ResponseEntity<>(quizInterface.getQuestionsByIds(questionIds).getBody(),HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Integer> calculateScore(Integer id, List<Response> responses) {

        return quizInterface.getScore(responses);
    }

    public ResponseEntity<String> deleteQuiz(Integer id) {
        quizRepo.deleteById(id);
        return new ResponseEntity<>("Quiz deleted successfully", HttpStatus.OK);
    }
}
