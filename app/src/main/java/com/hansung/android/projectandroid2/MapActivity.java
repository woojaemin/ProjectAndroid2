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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    // 지도에 표시된 마커 객체를 가지고 있을 배열입니다
    ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();

    int radious=1;

    public LatLng curLocation; // 현재 위치를 여기다 저장

    private FusedLocationProviderClient mFusedLocationClient; // 마지막 위치를 얻기위해 사용함
    private Location mCurrentLocation;
    GoogleMap mGoogleMap = null;  // 구글맵을 사용하기 위해

    String test;
    int rad = 1000;

    public DBHelper mapDBHelper;
    public DBHelper3 mapDBHelpers;


    Cursor stores;
    final private int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapDBHelper = new DBHelper(this);
//        mapDBHelper.deleteUserByMethod();
        mapDBHelpers = new DBHelper3(this);
//

//        SQLiteDatabase db=mapDBHelpers.getReadableDatabase();

//        Cursor stores = mapDBHelper.getAllUsersBySQL();



        //변수 설정
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (!checkLocationPermissions()) {
            requestLocationPermissions(REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION);
        } else{
            getLastLocation();
        }

        //SupportMapFragment를 사용함으로 맵을 R.id.maps에 띄운다.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);

        mapFragment.getMapAsync(this);

        Button Find = (Button)findViewById(R.id.button);
        Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleMap.clear();
                getAddress();
                ViewStores();

            }
        });

    }

    //메뉴 설정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maps_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }


     //메뉴 아이템
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Cursor distance = mapDBHelper.getAllUsersBySQL();

        switch (item.getItemId()) {

            case R.id.kilo_one:
                mGoogleMap.clear();
                item.setChecked(true);
                rad = 1000;
                radious=2;
//                getLastLocation(); // 현재 위치로

                // 반경 1KM원
                CircleOptions circle1KM = new CircleOptions().center(curLocation) //원점
                        .radius(1000)      //반지름 단위 : m
                        .strokeWidth(2f)  //선너비 0f : 선없음
                        .fillColor(Color.parseColor("#00000000")); //배경색
                mGoogleMap.addCircle(circle1KM);
                count(1000);

               break;


            case R.id.kilo_two:

                mGoogleMap.clear();
                item.setChecked(true);
                test="two";
                rad = 2000;
                //getLastLocation(2);
//                getLastLocation(); // 현재 위치로
                // 반경 2KM원
                CircleOptions circle2KM = new CircleOptions().center(curLocation) //원점
                        .radius(2000)      //반지름 단위 : m
                        .strokeWidth(2f)  //선너비 0f : 선없음
                        .fillColor(Color.parseColor("#00000000")); //배경색
                mGoogleMap.addCircle(circle2KM);
                count(2000);
//
//
//
//                MarkerOptions mar2;
//                double x2, y2, dis2;
//                for (int m=0; m<markers.size(); m++){
//                    mar2 = markers.get(m);
//                    //markers.get(m)
//                    x2 = mar2.getPosition().latitude;
//                    y2 = mar2.getPosition().longitude;
//                    dis2 = Double.parseDouble(getDistance(x2, y2));
//                    Log.i("dis", String.valueOf(dis2));
//
//                    if (dis2 <= 2000){
//                        mGoogleMap.addMarker(mar2);
//                    }
//
//                    mar2 = null;
//                }
//
                break;

            case R.id.kilo_three:
                mGoogleMap.clear();
                item.setChecked(true);
                test="three";
                rad = 3000;
                //getLastLocation(3);
//                getLastLocation();
                // 반경 3KM원
                CircleOptions circle3KM = new CircleOptions().center(curLocation) //원점
                        .radius(3000)      //반지름 단위 : m
                        .strokeWidth(2f)  //선너비 0f : 선없음
                        .fillColor(Color.parseColor("#00000000")); //배경색
                mGoogleMap.addCircle(circle3KM);

                count(3000);


//                MarkerOptions mar3;
//                double x3, y3, dis3;
//                for (int m=0; m<markers.size(); m++){
//                    mar3 = markers.get(m);
//                    //markers.get(m)
//                    x3 = mar3.getPosition().latitude;
//                    y3 = mar3.getPosition().longitude;
//                    dis3 = Double.parseDouble(getDistance(x3, y3));
//                    Log.i("dis", String.valueOf(dis3));
//                    if (dis3 <= 3000){
//                        mGoogleMap.addMarker(mar3);
//                    }
//
//                    mar3 = null;
//                }



                break;

            case R.id.action_icon:
                mGoogleMap.clear();
                getLastLocation();

                break;


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


    // 현재 위치와 거리 차이 구하는 함수
    public String getDistance(double gps1, double gps2){
        String distance;

        Location locationA = new Location("point A");
        locationA.setLatitude(gps1);
        locationA.setLongitude(gps2);
        Log.i("test2", String.valueOf(gps1));
        Log.i("test2", String.valueOf(gps2));

        Location locationB = new Location("point B");
        locationB.setLatitude(mCurrentLocation.getLatitude());
        locationB.setLongitude(mCurrentLocation.getLongitude());
        Log.i("test2", String.valueOf(mCurrentLocation.getLatitude()));
        Log.i("test2", String.valueOf(mCurrentLocation.getLongitude()));
        distance = Double.toString(locationB.distanceTo(locationA));

        Log.i("test2", String.valueOf(distance));
        return distance;
    }



    //ViewStores에서
    public void ViewStores() {

        stores = mapDBHelper.getAllUsersBySQL();

        stores.moveToFirst();

        while(stores.moveToNext()) {

        String Names = stores.getString(1);
        String input = stores.getString(2);

        Log.v("t1", Names);
        Log.v("t1", input);

        double Lati;
        double Longti;

        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA); // 주소를 위치로, 위치를 주소로 반환해주기 위해 사용
            List<Address> addresses = geocoder.getFromLocationName(input, 1);
            //주소로 위치를 얻기위한 address 배열

            // 주소를 위치로 반환해주는 코드
            if (addresses.size() > 0) { // address 배열의 크기가 0보다 크면
                Address bestResult = (Address) addresses.get(0); // 어드레스 배열의 첫번째 어드레스 위치를 bestResult 라고한다.
                //이부분 수정 필요
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////

                Lati = bestResult.getLatitude();
                Longti = bestResult.getLongitude();


                //현재 위치에서 목적지(위도, 경도)와 얼마나 떨어져 있는지 거리 계산
                //getDistance(Lati, Longti);

                Log.v("t1", String.valueOf(Lati));
                Log.v("t1", String.valueOf(Longti));

                // 그 위치로 지도를 줌레벨15로 옯겨주는 코드
                LatLng Locations = new LatLng(Lati, Longti); // bestadress의 위도 경도를 LatLng에 저장한다.

                MarkerOptions Restaurant = new MarkerOptions()
                        .position(Locations)
                        .title(Names)
                        .alpha(0.9f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mat));// --> 저장한 LatLng 위치를 마크설정

                //마커 표시
                mGoogleMap.addMarker(Restaurant);   // 설정한 마크를 구글맵에 찍어준다.
//                markers.add(Restaurant);

            }
        } catch (IOException e) {
            Log.e(getClass().toString(), "Failed in using Geocoder.", e);
            return;
            }
        }
    }

    public void count(int num) {

        stores = mapDBHelper.getAllUsersBySQL();

        stores.moveToFirst();

        while(stores.moveToNext()) {

            String Names = stores.getString(1);
            String input = stores.getString(2);

            double Lati;
            double Longti;

            try {
                Geocoder geocoder = new Geocoder(this, Locale.KOREA); // 주소를 위치로, 위치를 주소로 반환해주기 위해 사용
                List<Address> addresses = geocoder.getFromLocationName(input, 1);

                if (addresses.size() > 0) {
                    Address bestResult = (Address) addresses.get(0);

                    Lati = bestResult.getLatitude();
                    Longti = bestResult.getLongitude();

                    double dis = Double.parseDouble(getDistance(Lati, Longti));

                    LatLng Locations = new LatLng(Lati, Longti);
                    LatLng Mine = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                    MarkerOptions Current = new MarkerOptions()
                            .position(Mine)
                            .title("현재 위치");   // --> 저장한 LatLng 위치를 마크설정

                    if( num >= dis && num==1000){

                        MarkerOptions Restaurant = new MarkerOptions()
                                .position(Locations)
                                .title(Names)
                                .alpha(0.9f)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mat));// --> 저장한 LatLng 위치를 마크설정

                        //마커 표시
                        mGoogleMap.addMarker(Restaurant);   // 설정한 마크를 구글맵에 찍어준다.
                        mGoogleMap.addMarker(Current);
                    }
                    else if( num >= dis && num==2000){
                        MarkerOptions Restaurant = new MarkerOptions()
                                .position(Locations)
                                .title(Names)
                                .alpha(0.9f)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mat));// --> 저장한 LatLng 위치를 마크설정
                        //마커 표시
                        mGoogleMap.addMarker(Restaurant);   // 설정한 마크를 구글맵에 찍어준다.
                        mGoogleMap.addMarker(Current);
                        Log.v("test1", "2000>dis");
                    }
                    else if( num >= dis && num==3000){
                        MarkerOptions Restaurant = new MarkerOptions()
                                .position(Locations)
                                .title(Names)
                                .alpha(0.9f)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mat));// --> 저장한 LatLng 위치를 마크설정
                        //마커 표시
                        mGoogleMap.addMarker(Restaurant);   // 설정한 마크를 구글맵에 찍어준다.
                        mGoogleMap.addMarker(Current);
                        Log.v("test1", "3000>dis");
                    }

                }
            } catch (IOException e) {
                Log.e(getClass().toString(), "Failed in using Geocoder.", e);
                return;
            }
        }
    }


    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {  //현재 위치 얻는 함수
        Task task = mFusedLocationClient.getLastLocation();       // Task<Location> 객체 반환
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {

                    mCurrentLocation = location;

                    curLocation = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation,14));
                    //updateUI();

                    MarkerOptions mymarker = new MarkerOptions()
                                .position(curLocation);   //마커위치

                        //마커추가
                        mGoogleMap.addMarker(mymarker);

                        //마커 클릭됐을 때
                        mGoogleMap.setOnMarkerClickListener(new MyMarkerClickListener());


                        ViewStores();
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
            int count = 0;

            stores = mapDBHelper.getAllUsersBySQL();


            stores.moveToFirst();

            while(stores.moveToNext()) {

                String Store = stores.getString(0);
                String Name = stores.getString(1);
                String input = stores.getString(2);
                Log.v("tests", Name);

//                Log.v("Name", Name);// 잘나옴
//                Log.v("Input", input);// 잘나옴
//                Log.v("makertitle", marker.getTitle()); // 잘나옴

                if(Objects.equals(marker.getTitle(), Name))
                {
                    count++;
                    Log.v("testset", Name);
                    mapDBHelpers.insertUserByMethod(Name, input);

                }

            }

            Log.v("count", String.valueOf(count)); // 잘나옴
                // 이부분 수정
            if (count > 0){

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            else{
                show();
            }

            //show();
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
        int check = 1;
        try {

            //위에 선언해주었던 Cursor stores를 처음으로 이동시킴
            stores.moveToFirst();

            // Cursor를 이동시키면서 검색결과와 등록된 가게의 이름과 동일하면 input값을 가게의 주소로 바꿔줌
            while(stores.moveToNext()){
                if (Objects.equals(stores.getString(1), address.getText().toString())){
                    input = stores.getString(2);
                    check = 0;
                }
            }

            // Geocoder --> 위치 좌표를 주소로, 주소를 위치 좌표로 변경하는데 사용됨
            Geocoder geocoder = new Geocoder(this, Locale.KOREA); //
            List<Address> addresses = geocoder.getFromLocationName(input,1); // 주소를 위치 좌표로

            // 주소를 위치로 반환해주는 코드
            if (addresses.size() >0) {
                Address bestResult = (Address) addresses.get(0);

                mResultText.setText(String.format("[ %s , %s ]",
                        bestResult.getLatitude(),
                        bestResult.getLongitude()));

                // 그 위치로 지도를 줌레벨15로 옯겨주는 코드
                LatLng Location = new LatLng(bestResult.getLatitude(), // LatLng는 위도와 경도를 저장해서 위치를 지정하기 위해 사용하는 것
                        bestResult.getLongitude());
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Location,14)); // 구글맵의 화면 위치 이동

                // 위에서 가게의 이름으로 검색한 경우 마커 추가하는 것은 생략
                if(check != 0) {
                    mGoogleMap.addMarker(
                            new MarkerOptions().
                                    position(Location).
                                    title(input));
                }
            }
        } catch (IOException e) {
            Log.e(getClass().toString(),"Failed in using Geocoder.", e);
            return;
        }
    }
}
