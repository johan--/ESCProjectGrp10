package com.example.cindy.esc_50005.UI.DataAdder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.cindy.esc_50005.Database.Progress.NewQuizScoresDO;
import com.example.cindy.esc_50005.Database.Progress.ProgressRemoteDataSource;
import com.example.cindy.esc_50005.Database.Quizstuff.QuizQuestions1DO;
import com.example.cindy.esc_50005.Database.Quizstuff.QuizRemoteDataSource;
import com.example.cindy.esc_50005.R;

import java.util.ArrayList;

public class AddDatahere extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_datahere);

        AWSMobileClient.getInstance().initialize(this).execute();

        ProgressRemoteDataSource addhere = new ProgressRemoteDataSource();

        addhere.putScores("1002212","50.004","Quiz 1",3.0,"Brandon Lewis");
        addhere.putScores("1002212","50.004","Quiz 2",4.0,"Brandon Lewis");
        addhere.putScores("1002212","50.004","Quiz 3",3.0,"Brandon Lewis");
        addhere.putScores("1002212","50.004","Quiz 4",5.0,"Brandon Lewis");
        addhere.putScores("1002212","50.004","Quiz 5",2.0,"Brandon Lewis");

    }
}
