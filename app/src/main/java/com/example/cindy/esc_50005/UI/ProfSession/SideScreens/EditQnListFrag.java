package com.example.cindy.esc_50005.UI.ProfSession.SideScreens;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cindy.esc_50005.Database.Quizstuff.QuizQuestions1DO;
import com.example.cindy.esc_50005.R;
import com.example.cindy.esc_50005.UI.ProfSession.Adapters.QnListAdapter;
import com.example.cindy.esc_50005.UI.ProfSession.MainScreens.ActivityProfFrag;
import com.example.cindy.esc_50005.UI.Session.Main.SessionActivity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditQnListFrag extends Fragment implements Serializable {
    private Context context;

    private RecyclerView quizRecycler;
    private QnListAdapter mQnListAdapter;
    private EditQnListFrag.LayoutManagerType mCurrentLayoutManagerType;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<QuizQuestions1DO> dataset;

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }


    public EditQnListFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_qnlist_edit, container, false);
        quizRecycler = view.findViewById(R.id.recyclerViewQuizList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        quizRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final Bundle bundle = this.getArguments();

        if (bundle != null) {
            dataset = (ArrayList<QuizQuestions1DO>)bundle.getSerializable("allthequestions");
            Log.i("Here",dataset.get(0).getQuestion());
        }

        mQnListAdapter = new QnListAdapter(dataset);

        quizRecycler.setAdapter(mQnListAdapter);

        ImageView questionAdder = view.findViewById(R.id.addbutton);

        questionAdder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                QuizEditor editor = new QuizEditor();
                Bundle toEditor = new Bundle();
                bundle.putSerializable("allthequestions",dataset);
                editor.setArguments(toEditor);

                SessionActivity myActivity = (SessionActivity) context;
                myActivity.getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.slide_out_up,R.anim.slide_in_up).replace(R.id.profsessionhere,editor).addToBackStack(null).commit();

            }
        });

        return view;
    }

}
