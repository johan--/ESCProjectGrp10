package com.example.cindy.esc_50005.Database.Progress;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "escproject-mobilehub-27166461-newQuizScores")

public class NewQuizScoresDO {
    private String _studentIDsubjectID;
    private String _quizID;
    private String _name;
    private Double _score;

    @DynamoDBHashKey(attributeName = "studentIDsubjectID")
    @DynamoDBAttribute(attributeName = "studentIDsubjectID")
    public String getStudentIDsubjectID() {
        return _studentIDsubjectID;
    }

    public void setStudentIDsubjectID(final String _studentIDsubjectID) {
        this._studentIDsubjectID = _studentIDsubjectID;
    }
    @DynamoDBRangeKey(attributeName = "quizID")
    @DynamoDBAttribute(attributeName = "quizID")
    public String getQuizID() {
        return _quizID;
    }

    public void setQuizID(final String _quizID) {
        this._quizID = _quizID;
    }
    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }
    @DynamoDBAttribute(attributeName = "score")
    public Double getScore() {
        return _score;
    }

    public void setScore(final Double _score) {
        this._score = _score;
    }

}
