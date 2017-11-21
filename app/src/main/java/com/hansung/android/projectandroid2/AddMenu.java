package com.hansung.android.projectandroid2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jhim0 on 2017-11-17.
 */


public class AddMenu extends AppCompatActivity {

    String StoreImg;

    private String mPhotoFileName = null;
    private File mPhotoFile = null;

    EditText m_menu_name;
    EditText m_menu_price;
    EditText m_menu_explanation;
    ImageButton m_menu_Picture;

    private DBHelper2 mDbHelper;

    Button menu_enrollmentBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_addmenu);
        super.onCreate(savedInstanceState);

        menu_enrollmentBtn = (Button) findViewById(R.id.menu_1);

        m_menu_name = (EditText) findViewById(R.id.menu_title);
        m_menu_price = (EditText) findViewById(R.id.menu_price);
        m_menu_explanation = (EditText) findViewById(R.id.menu_explain);
        m_menu_Picture = (ImageButton) findViewById(R.id.imageButton);

        mDbHelper = new DBHelper2(this);


        m_menu_Picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });



        menu_enrollmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_MainActivity = new Intent(getApplicationContext(), MainActivity.class);

                insertRecord1();

                startActivity(intent_MainActivity);
            }
        });

    }


    private void insertRecord1() {
        EditText menu_name = (EditText) findViewById(R.id.menu_title);
        EditText menu_price = (EditText) findViewById(R.id.menu_price);
        EditText menu_explanation = (EditText) findViewById(R.id.menu_explain);


        long nOfRows = mDbHelper.insertUserByMethod2(menu_name.getText().toString(), menu_price.getText().toString(), StoreImg, menu_explanation.getText().toString());
        if (nOfRows > 0)
            Toast.makeText(this, "메뉴가 등록되었습니다.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "다시 입력해주세요.", Toast.LENGTH_SHORT).show();
    }

    final int REQUEST_IMAGE_CAPTURE = 100;


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            mPhotoFileName = "IMG" + currentDateFormat() + ".jpg";
            mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);
            StoreImg = mPhotoFile.getAbsolutePath();

            if (mPhotoFile != null) {
                Uri imageUri = FileProvider.getUriForFile(this, "com.hansung.android.projectandroid2", mPhotoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (mPhotoFileName != null) {
                mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);

                ImageButton imageButton4 = (ImageButton) findViewById(R.id.imageButton);
                imageButton4.setImageURI(Uri.fromFile(mPhotoFile));
            }
        }
    }

    private String currentDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

}
