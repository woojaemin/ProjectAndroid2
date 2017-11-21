package com.hansung.android.projectandroid2;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity implements MainFragment.OnTitleSelectedListener{
    public DBHelper mDbHelper1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper1 = new DBHelper(this);

        ImageButton pbtn = (ImageButton)findViewById(R.id.Phonebutton);

        pbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Phoneaction(v);
            }
        });


    }

    public void Phoneaction(View v) {
        Cursor cursors = mDbHelper1.getAllUsersBySQL();
        cursors.moveToLast();
        String tel = "tel: " + cursors.getString(3);
        Log.v("tel", tel);
        Intent intents = new Intent(Intent.ACTION_VIEW, Uri.parse(tel));
        startActivity(intents);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goto_AddMenuActivity:
                Intent intent = new Intent(getApplicationContext(), AddMenu.class);
                startActivity(intent);
                return true;
        }
        return false;
    }


    @Override
    public void onTitleSelected(int i) {
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            //DetailsFragment detailsFragment = new DetailsFragment();
            detailFragment detailfragment = new detailFragment();
            detailfragment.setSelection(i);
            getSupportFragmentManager().beginTransaction().replace(R.id.details, detailfragment).commit();
        } else {
            Intent intent = new Intent(this, detailActivity.class);
            intent.putExtra("index", i);
            startActivity(intent);

        }
    }


}
