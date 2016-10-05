package com.filepicker_android.filepicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, Filepicker.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("AR: ", "HERE!!!");
        if (requestCode == 1) {
            List<FilepickerFile> list = data.getParcelableArrayListExtra("data");
            Log.i("List size: ", Integer.toString(list.size()));
        }
    }
}
