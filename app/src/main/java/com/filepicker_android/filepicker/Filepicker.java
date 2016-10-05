package com.filepicker_android.filepicker;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Filepicker extends AppCompatActivity implements FilePickerListFragment.ListFragmentLink{

    public static final String EXTRA_SUPPORTED_TYPES = "supportedTypes";
    public static final String EXTRA_MAX_FILES = "maxFiles";
    public static final String EXTRA_VIEW_MODE = "viewMode"; //List or grid
    public static final String EXTRA_INITIAL_PATH = "initialPath";


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

    @Override
    public void notifyFragment(ArrayList<FilepickerFile> list) {
        Log.i("Notifying: ", list.toString());
        Intent backToActivity = new Intent();
        backToActivity.putParcelableArrayListExtra("data", list);
        setResult(1, backToActivity);
        finish();
    }
}
