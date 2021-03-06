package com.example.esc_50005.UI.Session.feedback;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.esc_50005.Database.feedback.Feedback;
import com.example.esc_50005.Database.feedback.FeedbackRemoteDataSource;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Otter on 22/3/2018.
 */

public class FeedbackPresenter implements FeedbackContract.Presenter {
    public static final String TAG = "FeedbackPresenter";

    private final FeedbackContract.View mFaqView;
    private FeedbackRemoteDataSource mFeedbackRepository;
    ArrayList<Feedback> feedbackJsonData;

    String sessionId;

    public FeedbackPresenter(@NonNull FeedbackContract.View feedbackView) {
        mFeedbackRepository = new FeedbackRemoteDataSource();
        mFaqView = checkNotNull(feedbackView, "feedbackView cannot be null!");
        mFaqView.setPresenter(this);
    }

    @Override
    public void start() {
        loadFeedback();
    }

    public void loadFeedback() {

        feedbackJsonData = mFeedbackRepository.getFeedbackListBySessionId(sessionId);
        processFeedback(feedbackJsonData);

        Log.i(TAG, "LoadFeedback size is " + feedbackJsonData.size());

    }

    public void processFeedback(ArrayList<Feedback> feedbackJsonData) {

        Log.i(TAG, "Length of feedbacksonData = " + feedbackJsonData.size());

        mFaqView.showFeedback(feedbackJsonData);
        mFaqView.feedbackLoaded();
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
