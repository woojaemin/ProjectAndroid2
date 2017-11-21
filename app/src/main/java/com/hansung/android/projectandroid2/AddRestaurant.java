package com.hansung.android.projectandroid2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRestaurant extends AppCompatActivity{

    public DBHelper mDBHelper;
    public DBHelper2 mDBHelpers;

    String StoreImg;

    private File mPhotoFile =null;         // 사진 저장할 파일 이름
    private String mPhotoFileName = null; //* 사진 이름
    final int REQUEST_IMAGE_CAPTURE = 30;  //* 이미지 크기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrestaurantactivity);

        mDBHelpers = new DBHelper2(this);
        mDBHelpers.deleteUserByMethod2();

        //외부 저장소 권한 사용 Permission
        checkDangerousPermissions();

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        Button erollmentBtn = (Button) findViewById(R.id.enrollment);


        mDBHelper = new DBHelper(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        erollmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_MainActivity = new Intent(getApplicationContext(), MainActivity.class);

                insertRecord();

                startActivity(intent_MainActivity);
            }
        });


    }

    private void insertRecord() {                          //레코드 추가 삽입 가능
        EditText name = (EditText) findViewById(R.id.storename);
        EditText address = (EditText) findViewById(R.id.address);
        EditText phone = (EditText) findViewById(R.id.callnumber);
        //ImageButton picture = (ImageButton) findViewById(R.id.imageButton);

        long nOfRows = mDBHelper.insertUserByMethod(name.getText().toString(), address.getText().toString(), phone.getText().toString(), StoreImg  );   //이미지 받아오는것 확인 / 수정!!!



        Log.v("Textadfsdf", String.valueOf(name));
        //Log.v("Textadfsdf", String.valueOf(picture));

        if (nOfRows > 0)
            Toast.makeText(this, "맛집이 등록되었습니다.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "다시 입력해주세요.", Toast.LENGTH_SHORT).show();
    }








    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    final int REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA = 0;
    //권한 검사 및 요청
    public void checkDangerousPermissions() {
        String[] permissions = { // 이 3개는 선언되있지 않으면 앱이 비정상 종료될수 있기 떄문에
                // 굉장히  dagerous한 권한들임임 반면에 인터넷 권한은 그렇게 중요한 권한은 아님
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA);

        }
    }
    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////


    //============================카메라 앱에 들어가서 사진 촬영=======================
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //1. 카메라 앱으로 찍은 이미지를 저장할 파일 객체 생성
            mPhotoFileName = "IMG" + currentDateFormat() + ".jpg";
            mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);
            StoreImg = mPhotoFile.getAbsolutePath();

            if (mPhotoFile != null) {
                //2. 생성된 파일 객체에 대한 Uri 객체를 얻기
                Uri imageUri = FileProvider.getUriForFile(this,
                        "com.example.jhim0.project1_new", mPhotoFile);

                //3. Uri 객체를 Extras를 통해 카메라 앱으로 전달
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            else
                Toast.makeText(getApplicationContext(), "file null", Toast.LENGTH_SHORT).show();

        }
    }
    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////

    //==================================촬영한 데이터 보야주기==================================
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //촬영한 사진 화면에 나타내기
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            if (mPhotoFileName != null){
                mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);
                Uri uri = Uri.fromFile(mPhotoFile);
                ImageButton imageButton = (ImageButton)findViewById(R.id.imageButton);
                imageButton.setImageURI(uri);
            }
            else {
                Toast.makeText(getApplicationContext(), "mPhotoFile is null", Toast.LENGTH_SHORT).show();
            }
        }
    }






    //날짜, 시간 나타내는 함수
    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }
}
