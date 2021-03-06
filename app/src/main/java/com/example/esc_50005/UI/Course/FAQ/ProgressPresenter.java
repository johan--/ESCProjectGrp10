package com.example.esc_50005.UI.Course.FAQ;

import android.support.annotation.NonNull;


import com.example.esc_50005.Database.Progress.ProgressRemoteDataSource;
import com.example.esc_50005.Database.Progress.QuizScores4DO;
import com.example.esc_50005.Log;


import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 1002215 on 12/3/18.
 */

public class ProgressPresenter implements ProgressContract.Presenter {

    public static final String TAG = "ProgressPresenter";

    private final ProgressContract.View mProgressView;
    private ProgressRemoteDataSource mProgressRepository;
    ArrayList<QuizScores4DO> progressArrayList;
    ArrayList<QuizScores4DO> nameList;
    ArrayList<Double> avgList;

    String studentId;
    String courseId;

    public ProgressPresenter(@NonNull ProgressRemoteDataSource progressRepository, @NonNull ProgressContract.View progressView) {
        mProgressRepository = progressRepository;
        mProgressView = checkNotNull(progressView, "progressView cannot be null!");
        mProgressView.setPresenter(this);
    }

    @Override
    public void start() {
        loadScores();
//        loadNames();
//        processAverage(progressArrayList);
        processScores(progressArrayList);
    }

    @Override
    public void loadScores() {
        progressArrayList = mProgressRepository.getScores(courseId,studentId);// need to change it to base on the user login details
        processScores(progressArrayList);

        Log.i(TAG, "LoadScores size is " + progressArrayList.size());
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void processScores(ArrayList<QuizScores4DO> progressArrayList) {
        ArrayList<Double> scoreList = new ArrayList<Double>();
        Log.i(TAG, "Length of progressArrayList = " + progressArrayList.size());

        if (progressArrayList.size() != 0) {

            for(int i = 0; i<progressArrayList.size();i++){
                try{
                    scoreList.add(progressArrayList.get(i).getScore());
                } catch(Exception e){
                    e.printStackTrace();
                }

            }
        }

        mProgressView.showProgress(scoreList);


    }



    @Override
    public ArrayList<Double> processAverage(ArrayList<QuizScores4DO> progressArrayList) {
        ArrayList<Double> scoreList = new ArrayList<Double>();

        double total=0;
        double avg = 0;
        String student;


        if (progressArrayList.size() != 0) {
            Log.i(TAG, "Length of progressArrayList = " + progressArrayList.size());
            student = progressArrayList.get(0).getStudentIDSessionID();//might need to change in the future
            for(int i = 0; i<progressArrayList.size();i++){
                try{
                    total += progressArrayList.get(i).getScore();
                } catch(Exception e){
                    e.printStackTrace();
                }

            }
            avg = total/progressArrayList.size();
        }
        avgList.add(avg);
       return avgList;

    }

    @Override
    public void loadNames() {
        nameList = mProgressRepository.getScores(courseId,studentId);// need to change it to base on the user login details
        processNames(nameList);

        Log.i(TAG, "LoadName size is " + nameList.size() + nameList.get(0).getName());
    }

    public void processNames(ArrayList<QuizScores4DO> nameList) {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> studentIds = new ArrayList<String>();
        Log.i(TAG, "Length of nameList = " + nameList.size());


        if (nameList.size() != 0) {

            for(int i = 0; i<nameList.size();i++){
                if(names.size()==0){
                    try{
                        names.add(nameList.get(i).getName());
                        studentIds.add(nameList.get(i).getStudentIDSessionID());
                        Log.i(TAG, "firstname = " + nameList.get(i).getName());
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                } else{
                    boolean exist = false;
                    for(int j = 0; j<names.size();j++){
                        if(names.get(j).equals(nameList.get(i).getName())){
                            exist = true;
                        }
                    }
                    if(!exist){
                        try{
                            names.add(nameList.get(i).getName());
                            Log.i(TAG, "non-existent name = " + nameList.get(i).getName());
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }



            }
        }

        mProgressView.showNames(names, studentIds, processAverage(progressArrayList));


    }
}

//public class ProgressPresenter implements ProgressContract.Presenter {
//
//    private final ProgressContract.View mCourseProgressView;
//    private QuestionRemoteDataSource mSessionQuestionsRepository= new QuestionRemoteDataSource();
//    ProgressPresenter.QuizJsonData[] quizJsonData;
//
//
//    public ProgressPresenter(@NonNull QuestionRemoteDataSource sessionQuestionsRepository, @NonNull ProgressContract.View courseProgressView) {
//        Log.i("question presenter","question presenter");
//        mSessionQuestionsRepository = checkNotNull(sessionQuestionsRepository, "sessionQuestionsRepository cannot be null");
//        mCourseProgressView = checkNotNull(courseProgressView, "courseProgressView cannot be null!");
//        mCourseProgressView.setPresenter(this);
//    }
//    @Override
//    public void start() {
//
//        loadScores();
//    }
//
//    @Override
//    public void loadScores() {
////        ArrayList<JSONObject> scores = mSessionQuestionsRepository.getDataInJson("111");
////        Log.i("scores",scores.toString());
//        ArrayList<JSONObject> quiz = new ArrayList<JSONObject>();
//        try{
//            JSONObject obj = new JSONObject();
//            for(int i = 0; i<5; i++){
//                obj.put("name","Quiz " + i);
////                obj.put("score", i * 30);
//                quiz.add(obj);
//            }
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        for(int j = 0; j<quiz.size(); j++){
//
//        }
//        Gson gson = new Gson();
//        quizJsonData=gson.fromJson(quiz.toString(), ProgressPresenter.QuizJsonData[].class);
//        processLoadedScores(quizJsonData);
//
//    }
//
//    @Override
//    public void processAverage() {
//
//    }
//
//    public class QuizJsonData {
//
//        String quiz;
//        String score;
//
//    }
//
//
//    public void processLoadedScores(ProgressPresenter.QuizJsonData[] quizJsonData)
//    {
//        if(quizJsonData.length!=0)
//        {
//            Log.i("quiz json data",quizJsonData.toString());
//            mCourseProgressView.showProgress(quizJsonData);
//        }
//    }
//
//
//
//}

