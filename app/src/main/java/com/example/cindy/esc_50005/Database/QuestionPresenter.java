package com.example.cindy.esc_50005.Database;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by hoonqt on 1/3/18.
 */

public class QuestionPresenter implements QuestionContract {

    DynamoDBMapper dynamoDBMapper;

    public void loadQuestions(final String sessionCode) {

        SessionQuestionsRemoteDataSource questionLoader = new SessionQuestionsRemoteDataSource();
        String allthequestions = questionLoader.getQuestionsList(sessionCode);

    };

    public void addNewQuestion(String question, String sessionCode) {

        SessionQuestionsRemoteDataSource questionAdder = new SessionQuestionsRemoteDataSource();
        questionAdder.addQuestion(question,sessionCode);

    };

    public void processEmptyQuestions() {

    };

    public void upvoteQuestion(String question, String sessionCode) {

        final NewfaqDO newQuestion = new NewfaqDO();

        newQuestion.setSessioncode(sessionCode);
        newQuestion.setQuestion(question);
        newQuestion.setUpvote(newQuestion.getUpvote()+1.0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(newQuestion);
                // Item saved
            }
        }).start();

    };


}
