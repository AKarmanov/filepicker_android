package com.filepicker_android.filepicker;

import android.graphics.Typeface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Hashtable;
import java.util.Map;

public class FilepickerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filepicker);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragmentContainer, new FilePickerListFragment());
        ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public void onBackPressed() {

    }
}
