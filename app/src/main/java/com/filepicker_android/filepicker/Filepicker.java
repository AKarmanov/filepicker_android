package com.filepicker_android.filepicker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Filepicker extends AppCompatActivity implements FragmentToActivityInterface {

    public static final String EXTRA_SUPPORTED_TYPES = "supportedTypes";
    public static final String EXTRA_MAX_FILES = "maxFiles";
    public static final String EXTRA_VIEW_MODE = "viewMode"; //List or grid
    public static final String EXTRA_INITIAL_PATH = "initialPath";

    private Context appContext;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = getApplicationContext();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filepicker_options_menu, menu);
        for (int i = 0; i < menu.size() && i < 1; i++) {
            menu.getItem(i).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("DONE")) {
            Log.i("Notifying: ", "DONE");
            ArrayList<FilepickerFile> list = ((FilepickerContext)getApplicationContext())
                    .getCollection()
                    .getPicks();
            Intent backToActivity = new Intent();
            backToActivity.putParcelableArrayListExtra("data", list);
            setResult(1, backToActivity);
            finish();
        }
        return true;
    }

    @Override
    public void addRemoveItem(List<FilepickerFile> files, boolean add, int position) {
        if (add) {
            FilepickerCollection fc = ((FilepickerContext)appContext).getCollection();
            fc.addFile(files.get(position));
            menu.getItem(0).setTitle(String.format(Locale.ENGLISH, "%d Pick(s)", fc.getCollectionSize()));
        }
        else {
            FilepickerCollection fc = ((FilepickerContext)appContext).getCollection();
            fc.removeFile(files.get(position));
            menu.getItem(0).setTitle(String.format(Locale.ENGLISH, "%d Pick(s)", fc.getCollectionSize()));
        }
    }
}
