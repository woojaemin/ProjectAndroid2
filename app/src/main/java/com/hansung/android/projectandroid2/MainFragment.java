package com.hansung.android.projectandroid2;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by jhim0 on 2017-11-18.
 */

public class MainFragment extends Fragment {

    int mCurCheckPosition = -1;

    public interface OnTitleSelectedListener {
        public void onTitleSelected(int i);
    }
    public MainFragment(){

    }
    public DBHelper mDbHelper;
    public DBHelper2 mDbHelper2;

    final int REQUEST_CODE_READ_CONTACTS = 1;

    EditText menu_title;
    EditText menu_price;
    EditText menu_explain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){ // 화면에 보일 뷰 만듬

        final View rootView = (View)inflater.inflate(R.layout.fragment_main, container, false);  // 뷰 객체 만들어줌

        menu_title = (EditText)rootView.findViewById(R.id.menu_title);
        menu_price = (EditText)rootView.findViewById(R.id.menu_price);
        menu_explain = (EditText)rootView.findViewById(R.id.menu_explain);

        mDbHelper = new DBHelper(getActivity());

        TextView txv1 = (TextView) rootView.findViewById(R.id.mainTextView);
        TextView txv2 = (TextView) rootView.findViewById(R.id.addTextView1);
        TextView txv3 = (TextView) rootView.findViewById(R.id.addTextView2);
        ImageView imv1 = (ImageView) rootView.findViewById(R.id.imageView1);

        Cursor cursor = mDbHelper.getAllUsersBySQL();


        cursor.moveToLast();

        txv1.setText(cursor.getString(1));
        txv2.setText(cursor.getString(2));
        txv3.setText(cursor.getString(3));

        Uri img = Uri.parse("file://"+cursor.getString(4));
        imv1.setImageURI(img);


        mDbHelper2 = new DBHelper2(getActivity());
        Cursor cursor2 = mDbHelper2.getAllUsersByMethod();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_item,
                cursor2, new String[]{
                UserContract2.Users.KEY_MENU_NAME,
                UserContract2.Users.KEY_MENU_PRICE,
                UserContract2.Users.KEY_PICTURE,
                UserContract2.Users.KEY_MENU_EXPLANATION},
                new int[]{R.id.textView1, R.id.textView2, R.id.imageView},
                0
        );

        ListView lv = (ListView)rootView.findViewById(R.id.listview);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Adapter adapter = parent.getAdapter();

                String b = ((Cursor)adapter.getItem(i)).getString(1);
                String c = ((Cursor)adapter.getItem(i)).getString(2);


                Intent intent = new Intent();

                intent.putExtra("title", i);

                Activity activity = getActivity();
                ((OnTitleSelectedListener)activity).onTitleSelected(i);

            }
        });
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        return rootView;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mCurCheckPosition = savedInstanceState.getInt("curChoice", -1);
            if (mCurCheckPosition >= 0) {
                Activity activity = getActivity(); // activity associated with the current fragment
                ((OnTitleSelectedListener)activity).onTitleSelected(mCurCheckPosition);

                ListView lv = (ListView) getView().findViewById(R.id.listview);
                lv.setSelection(mCurCheckPosition);
                lv.smoothScrollToPosition(mCurCheckPosition);
            }
        }
    }

    //    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }



}
