package com.example.esc_50005.Database.activityQuestion;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class questionCreator {

    DynamoDBMapper dynamoDBMapper;
    JSONObject datainjson;

    public questionCreator() {

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();


    }

    public void createQuestion(String courseID, String sessionID,String question, List<String> options,Double answer) {

        final ActivityQuestionsDO newQuestion = new ActivityQuestionsDO();
        newQuestion.setCourseID(courseID);
        newQuestion.setSessionNO(sessionID);
        newQuestion.setQuestion(question);
        newQuestion.setOptions(options);
        newQuestion.setCorrectOption(answer);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(newQuestion);
                // Item saved
            }
        }).start();

    }

    public void getQuestions(final String courseID, final String sessionNO) {



    }

    public JSONObject getDatainjson(final String courseID, final String sessionNO) {

        getQuestions(courseID,sessionNO);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ActivityQuestionsDO faq = new ActivityQuestionsDO();
                faq.setCourseID(courseID);
                faq.setSessionNO(sessionNO);

                DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                        .withHashKeyValues(faq);


                PaginatedList<ActivityQuestionsDO> result = dynamoDBMapper.query(ActivityQuestionsDO.class,queryExpression);

                Gson gson = new Gson();

                try {
                    datainjson = new JSONObject(gson.toJson(result.get(0)));
                }

                catch (JSONException ex) {
                    System.out.println(ex);
                }


            }
        });

        thread.start();


        try {
            thread.join();
        }

        catch (InterruptedException ex) {

        }


        return datainjson;
    }
}