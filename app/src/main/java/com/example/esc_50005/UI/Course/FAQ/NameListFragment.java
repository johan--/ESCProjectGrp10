package com.example.esc_50005.UI.Course.FAQ;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.esc_50005.Database.utilities.Injection;
import com.example.esc_50005.R;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 1002215 on 27/3/18.
 */

public class NameListFragment extends Fragment implements ProgressContract.View {

    @Override
    public void showProgress(ArrayList<Double> scoreList) {

    }

    @Override
    public void showAverage(double avg) {


    }

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;
    private ProgressContract.Presenter mPresenter;
    private LinearLayout mNameListView;
    private RecyclerView nameListRecycler;
    private SwipeRefreshLayout swipeLayout;
    private SharedPreferences userInformation;
    private NameListAdapter mNameListAdapter;
    private String courseId;
    private FrameLayout frameLayout;
    private FloatingActionButton fab;

    public NameListFragment() {
        // Required empty public constructor
    }

    public static NameListFragment newInstance() {
        return new NameListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull ProgressContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.name_list_main, container, false);
        mPresenter = new NameListPresenter(
                Injection.provideProgressRepository(getActivity().getApplicationContext()), this);
        nameListRecycler = (RecyclerView) view.findViewById(R.id.name_list_rv);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        nameListRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        userInformation = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        courseId = userInformation.getString(getString(R.string.course_full_name),"");
        frameLayout = (FrameLayout) view.findViewById(R.id.name_list_fl);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.name_list_swipe);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadNames(); // change it to load namelist
            }
        });

        fab = (FloatingActionButton) getActivity().findViewById(R.id.course_fab);

        mPresenter.setCourseId(courseId.substring(0,6));
        return view;
    }

    public void showNames(ArrayList<String> nameList, ArrayList<String> studentIdsList, ArrayList<Double> avg) {

//        Log.i("NameListFragment", "showNames: " + nameList.get(0));
        mNameListAdapter = new NameListAdapter(nameList, studentIdsList, avg, mItemListener);
        nameListRecycler.setAdapter(mNameListAdapter);

    }

    public void showNoName() {

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> studentIdsList = new ArrayList<>();
        ArrayList<Double> avg = new ArrayList<>();
        mNameListAdapter = new NameListAdapter(nameList, studentIdsList, avg, mItemListener);
        nameListRecycler.setAdapter(mNameListAdapter);
//            Snackbar snackbar = Snackbar
//                    .make(frameLayout, "No Connection", Snackbar.LENGTH_LONG)
//                    .setAction("Retry", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            mPresenter.loadNames();
//                        }
//                    });
//            snackbar.show();
//            snackbar.setActionTextColor(Color.WHITE);
    }

    public void showLoadNameError() {

    }

    public void nameLoaded() {
        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
    }

    NameListItemListener mItemListener = new NameListItemListener() {
        @Override
        public void onArrowClick(String studentId, String studentName) {
            Intent intent = new Intent(getActivity(), ProfessorProgressActivity.class);
            intent.putExtra("STUDENT_ID", studentId);
            intent.putExtra("STUDENT_NAME", studentName);
            startActivity(intent);//need to switch that fragment here
        }

        @Override
        public void onRetryClick() {
            mPresenter.loadNames();
        }


    };

    public void setFab() {
        fab.setVisibility(View.GONE);
    }
}

