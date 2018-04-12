package com.example.esc_50005.UI.Session.addQuestionDialog;

import com.example.esc_50005.Database.Database.SessionQuestionsDO;
import com.example.esc_50005.Database.Database.SessionQuestionsRemoteDataSource;
import com.example.esc_50005.Database.feedback.FeedbackRemoteDataSource;

import java.util.ArrayList;

/**
 * Created by tan_j on 5/3/2018.
 */

public class AddQuestionDialogPresenter implements AddQuestionDialogContract.Presenter {

    public static final String TAG = "AddQuestionDialogPresenter";

    private final SessionQuestionsRemoteDataSource mQuestionRepository;

    private final AddQuestionDialogContract.View mFeedbackView;

    private String sessionId;
    private String userId;
    private String name;

//    public AddQuestionDialogPresenter(@NonNull FeedbackRepository feedbackRepository,
//                             @NonNull AddQuestionDialogContract.View feedbackView) {
//        mQuestionRepository = checkNotNull(feedbackRepository, "tasksRepository cannot be null");;
//        mFeedbackView = checkNotNull(feedbackView, "tasksView cannot be null!");;
//        mFeedbackView.setPresenter(this);
//    }

    public AddQuestionDialogPresenter(AddQuestionDialogContract.View view) {
        mQuestionRepository = new SessionQuestionsRemoteDataSource();
        mFeedbackView = view;
        mFeedbackView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void addQuestion(String question) {
        SessionQuestionsDO newQuestion = new SessionQuestionsDO();
        newQuestion.setSessionId(sessionId);
        newQuestion.setUsersVoted(new ArrayList<String>());
        newQuestion.setQuestion(question);

        mQuestionRepository.saveQuestion(newQuestion);
        mFeedbackView.dismissDialog();

    }

    @Override
    public void onLaterClicked() {
        mFeedbackView.dismissDialog();
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
