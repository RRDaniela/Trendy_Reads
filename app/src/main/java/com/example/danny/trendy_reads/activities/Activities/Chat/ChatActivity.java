package com.example.danny.trendy_reads.activities.Activities.Chat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.danny.trendy_reads.R;
import com.example.danny.trendy_reads.activities.Activities.Chat.SectionsPagerAdapter;

import java.util.function.ToLongBiFunction;

public class ChatActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager viewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
       mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Chat");


        viewPager = (ViewPager) findViewById(R.id.maintab_pager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //El error está aquí
       viewPager.setAdapter(mSectionsPagerAdapter);

        mTablayout = (TabLayout) findViewById(R.id.main_tabs);
        mTablayout.setupWithViewPager(viewPager);

    }

}
