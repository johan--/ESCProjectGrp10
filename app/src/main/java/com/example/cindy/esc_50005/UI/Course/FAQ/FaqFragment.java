package com.example.cindy.esc_50005.UI.Course.FAQ;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cindy.esc_50005.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FaqFragment extends Fragment implements FaqContract {


    public FaqFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.faq_recycler, container, false);
    }

}
