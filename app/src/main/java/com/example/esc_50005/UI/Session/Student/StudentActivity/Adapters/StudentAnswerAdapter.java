package com.example.esc_50005.UI.Session.Student.StudentActivity.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.esc_50005.Database.Quizstuff.QuizQuestions1DO;
import com.example.esc_50005.R;

import java.util.ArrayList;

/**
 * Created by hoonqt on 30/3/18.
 */

public class StudentAnswerAdapter extends RecyclerView.Adapter<StudentAnswerAdapter.QnViewHolder> {

    private ArrayList<QuizQuestions1DO> dataset;
    private static int viewHolderCount = 0;
    private Context context;
    SharedPreferences sharedPreferences;

    public StudentAnswerAdapter(ArrayList<QuizQuestions1DO> input) {

        dataset = input;

    }

    @NonNull
    @Override
    public QnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutIDForListItem = R.layout.student_quiz_recycler;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIDForListItem,parent,shouldAttachToParentImmediately);
        QnViewHolder qnViewHolder = new QnViewHolder(view);
        viewHolderCount++;

        return qnViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QnViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (dataset == null) {
            return 0;
        }
        else return dataset.size();
    }

    class QnViewHolder extends RecyclerView.ViewHolder {

        RadioGroup group;
        TextView questionbox;
        TextView option1;
        TextView option2;
        TextView option3;
        TextView option4;


        public QnViewHolder(View v) {
            super(v);

            questionbox = v.findViewById(R.id.questionBox);
            option1 = v.findViewById(R.id.option1ans);
            option2 = v.findViewById(R.id.option2ans);
            option3 = v.findViewById(R.id.option3ans);
            option4 = v.findViewById(R.id.option4ans);



        }

        public void bind(int position) {

            QuizQuestions1DO question = dataset.get(position);
            questionbox.setText(question.getQuestion());
            option1.setText(question.getOptions().get(0));
            option2.setText(question.getOptions().get(1));
            option3.setText(question.getOptions().get(2));
            option4.setText(question.getOptions().get(3));

        }
    }

}