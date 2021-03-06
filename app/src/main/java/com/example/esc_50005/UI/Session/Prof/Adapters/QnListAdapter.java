package com.example.esc_50005.UI.Session.Prof.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.esc_50005.Database.Quizstuff.QuizQuestions2DO;
import com.example.esc_50005.R;
import com.example.esc_50005.UI.Session.Prof.SideScreens.QuizEditor;
import com.example.esc_50005.UI.Session.Main.SessionActivity;

import java.util.ArrayList;

/**
 * Created by hoonqt on 27/3/18.
 */

public class QnListAdapter extends RecyclerView.Adapter<QnListAdapter.QnViewHolder>{

    private static int viewHolderCount = 0;
    private Context context;
    private ArrayList<QuizQuestions2DO> questions;
    SharedPreferences sharedPreferences;

    public QnListAdapter(ArrayList<QuizQuestions2DO> input) {
        questions = new ArrayList<>();

        for (int i = 0;i<input.size();i++) {
            if (input.get(i).getIsItQn() == false) {
                questions.add(input.get(i));
            }
        }


    }

    @Override
    public QnListAdapter.QnViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIDForListItem = R.layout.quizlist_recycler;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean shouldAttachToParentImmediately = false;

        context = parent.getContext();

        View view = inflater.inflate(layoutIDForListItem,parent,shouldAttachToParentImmediately);

        QnListAdapter.QnViewHolder quizViewHolder = new QnViewHolder(view);
        viewHolderCount++;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(parent.getContext());


        return quizViewHolder;
    }

    @Override
    public void onBindViewHolder(QnListAdapter.QnViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (questions == null) {
            return 0;
        }

        else return questions.size();
    }

    class QnViewHolder extends RecyclerView.ViewHolder {

        public TextView QuestionHere;
        public ImageView arrowHead;


        int index;


        public QnViewHolder(View v) {
            super(v);
            QuestionHere = (TextView)v.findViewById(R.id.questionwho);
            arrowHead = (ImageView)v.findViewById(R.id.arrowView2);

            arrowHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    QuizEditor editor = new QuizEditor();
                    Bundle bundler = new Bundle();

                    ArrayList<QuizQuestions2DO> tobetransferred = new ArrayList<>(questions);

                    Log.i("All the things",questions.toString());

                    bundler.putSerializable("alltheqns",tobetransferred);


                    bundler.putInt("index",index);

                    editor.setArguments(bundler);

                    SessionActivity myActivity = (SessionActivity) context;
                    myActivity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.profsessionhere,editor).addToBackStack(null).commit();

                }
            });

        }

        public void bind(int position) {

            QuestionHere.setText(questions.get(position).getQuestion());
            index = position;

        }

    }
}
