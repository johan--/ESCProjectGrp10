package com.example.cindy.esc_50005.UI.Course.FAQ;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.example.cindy.esc_50005.Database.activityQuestion.questionCreator;
import com.example.cindy.esc_50005.R;
import com.example.cindy.esc_50005.UI.Session.SessionActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class CourseActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);

        AWSMobileClient.getInstance().initialize(this).execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        btn = findViewById(R.id.clickToGoToPostQuestion);
        btn.setOnClickListener(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.sessions));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.faq));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.progress));

        //fragment is reusable cuz you can use it in other activities
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final com.example.cindy.esc_50005.UI.Course.FAQ.CoursePagerAdapter coursePagerAdapter = new com.example.cindy.esc_50005.UI.Course.FAQ.CoursePagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(coursePagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    }

    @Override
    public void onClick(View v) {
        Log.i("this","button is pressed");
        Intent intent = new Intent(CourseActivity.this, SessionActivity.class);
        startActivity(intent);
        finish();

    }
}
