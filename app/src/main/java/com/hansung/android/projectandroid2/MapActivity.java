package com.hansung.android.projectandroid2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FusedLocationProviderClient mFusedLocationClient;
    private Location mCurrentLocation;
    GoogleMap mGoogleMap = null;

    String test;
    int rad = 1000;

    public DBHelper mapDBHelper;

    final private int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapDBHelper = new DBHelper(this);

        Cursor stores = mapDBHelper.getAllUsersBySQL();

//        test = stores.getString(1);
//
//        Log.v("test", test);

        Log.v("test", "1");


        //변수 설정
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (!checkLocationPermissions()) {
            requestLocationPermissions(REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION);
        } else{
            getLastLocation();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        Button Find = (Button)findViewById(R.id.button);
        Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddress();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maps_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.kilo_one:
                item.setChecked(true);
                test = "one";
                rad = 1000;
               getLastLocation();
               Log.v("test","1");

            case R.id.kilo_two:
                item.setChecked(true);
                test="two";
                rad = 2000;
                getLastLocation();

            case R.id.kilo_three:
                item.setChecked(true);
                test="three";
                rad = 3000;
                getLastLocation();

            case R.id.action_icon:
                getLastLocation();
        }
        return false;


    }

    //구글 맵 객체를 얻기 위한 코드
    private boolean checkLocationPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermissions(int requestCode) {
        ActivityCompat.requestPermissions(
                MapActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                requestCode    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
        );
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        Task task = mFusedLocationClient.getLastLocation();       // Task<Location> 객체 반환
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    mCurrentLocation = location;
                    LatLng curLocation = new LatLng(mCurrentLocation.getLatitude(),
                            mCurrentLocation.getLongitude());
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation,14));
                    //updateUI();

                    MarkerOptions mymarker = new MarkerOptions()
                                .position(curLocation);   //마커위치


                        // 반경 1KM원
                        CircleOptions circle1KM = new CircleOptions().center(curLocation) //원점
                                .radius(1500)      //반지름 단위 : m
                                .strokeWidth(2f)  //선너비 0f : 선없음
                                .fillColor(Color.parseColor("#00000000")); //배경색

                        //마커추가
                        mGoogleMap.addMarker(mymarker);

                        //원추가
                        mGoogleMap.addCircle(circle1KM);

                        mGoogleMap.setOnMarkerClickListener(new MyMarkerClickListener());
                }

                else{

                    Toast.makeText(getApplicationContext(),
                            getString(R.string.no_location_detected),
                            Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });
    }

    class MyMarkerClickListener implements GoogleMap.OnMarkerClickListener {

        @Override
        public boolean onMarkerClick(Marker marker) {
//               AlertDialog.Builder atd = new AlertDialog.Builder(getApplication());
//                    atd.setTitle("맛집등록");
//                    atd.setMessage("맛집을 등록하시겠습니까?");
//                    atd.setIcon(R.drawable.firework3);
//                    atd.setPositiveButton("아니오", null);
//                    atd.setNegativeButton("예", null);
//                    atd.show();
            show();
            return false;
        }
    }

    void show()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("맛집 등록");
        builder.setMessage("새로운 맛집을 등록하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent Add = new Intent(getApplicationContext(), AddRestaurant.class);
                        startActivity(Add);
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }




    //카메라를 맨 처음 실행 하자마자 줌레벨15로 옮겨주는 코드
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
//        if(mCurrentLocation != null){
//                      LatLng Location = new LatLng(mCurrentLocation.getLatitude(),
//                                    mCurrentLocation.getAltitude());
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Location,15));
//        }


    }



    private void getAddress() {
        TextView mResultText = (TextView) findViewById(R.id.result);
        EditText address = (EditText)findViewById(R.id.edit_text);
        String input = address.getText().toString();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocationName(input,1);

            // 주소를 위치로 반환해주는 코드
            if (addresses.size() >0) {
                Address bestResult = (Address) addresses.get(0);

                mResultText.setText(String.format("[ %s , %s ]",
                        bestResult.getLatitude(),
                        bestResult.getLongitude()));

                // 그 위치로 지도를 줌레벨15로 옯겨주는 코드
                LatLng Location = new LatLng(bestResult.getLatitude(),
                        bestResult.getLongitude());
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Location,14));

                //마커 표시(위치와 마커 클릭시정보창에 표시되는 문자열)
                mGoogleMap.addMarker(
                        new MarkerOptions().
                                position(Location).
                                title(input));
            }
        } catch (IOException e) {
            Log.e(getClass().toString(),"Failed in using Geocoder.", e);
            return;
        }
    }
}
