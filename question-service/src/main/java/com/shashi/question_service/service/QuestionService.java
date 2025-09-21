package com.shashi.question_service.service;

import com.shashi.question_service.model.Question;
import com.shashi.question_service.model.QuestionWrapper;
import com.shashi.question_service.model.Response;
import com.shashi.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repo;

    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity(repo.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String topic) {

        return new ResponseEntity<>(repo.findByCategory(topic), HttpStatus.OK);
    }

    public void addQuestion(Question question) {
        repo.save(question);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category,int numberOfQuestions) {
        List<Integer> questions = repo.getRandomQuestionsByCategory(category, numberOfQuestions);

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public List<QuestionWrapper> getQuestionsByIds(List<Integer> ids) {
        List<QuestionWrapper> questions = new ArrayList<QuestionWrapper>();
        for(Integer id: ids){
            Question q = repo.findById(id).orElse(null);
            if(q!=null){
                questions.add(new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4()));
            }
        }
        return questions;
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int score = 0;
        for (Response response : responses) {
            Question question = repo.findById(response.getId()).orElse(null);
            if (question != null && response.getResponse().equals(question.getRightAnswer())) {
                score++;
            }
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestions(List<Question> questions) {
        repo.saveAll(questions);
        return new ResponseEntity<>("Questions added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateQuestion(Integer id, Question question) {
        if(repo.existsById(id)){
            question.setId(id);
            repo.save(question);
            return new ResponseEntity<>("Question updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        if(repo.existsById(id)){
            repo.deleteById(id);
            return new ResponseEntity<>("Question deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
    }
}
