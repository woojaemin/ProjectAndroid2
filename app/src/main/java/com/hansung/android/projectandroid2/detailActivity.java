package com.hansung.android.projectandroid2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by USER on 2017-10-03.
 */

public class detailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }


        detailFragment details = new detailFragment();

        details.setSelection(getIntent().getIntExtra("index",-1));

        getSupportFragmentManager().beginTransaction().replace(R.id.details,details).commit();

    }
}