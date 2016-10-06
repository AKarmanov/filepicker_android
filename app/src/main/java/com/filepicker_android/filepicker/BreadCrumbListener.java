package com.filepicker_android.filepicker;


import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BreadCrumbListener implements Button.OnClickListener {

    private String path;

    public BreadCrumbListener(String path) {
        this.path = path;
    }

    @Override
    public void onClick(View view) {
        Log.i("-----", path);
    }
}