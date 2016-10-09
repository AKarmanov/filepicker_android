package com.filepicker_android.filepicker;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.filepicker_android.filepicker.contextual.FilepickerCollection;
import com.filepicker_android.filepicker.contextual.FilepickerConfig;
import com.filepicker_android.filepicker.contextual.FilepickerContext;
import com.filepicker_android.filepicker.dirutils.FilepickerFile;
import com.filepicker_android.filepicker.pickerlist.FilePickerFragment;
import com.filepicker_android.filepicker.pickslist.PicksListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Filepicker extends AppCompatActivity implements FragmentToActivityInterface {

    public static final int PICK_FRAGMENT = 0;
    public static final int PICKS_FRAGMENT = 1;

    private FilepickerContext appContext;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = (FilepickerContext) getApplicationContext();
        FilepickerConfig config = getIntent().getParcelableExtra(FilepickerConfig.EXTRA_CONFIG);
        if (config != null) {
            appContext.setConfig(config);
        }
        setContentView(R.layout.activity_filepicker);
        transitionFragment(PICK_FRAGMENT);
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            goBackToCallingActivity();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filepicker_options_menu, menu);
        for (int i = 0; i < menu.size() && i < 1; i++) {
            menu.getItem(i).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        this.menu = menu;
        menu.getItem(0).setTitle(String.format(Locale.ENGLISH, "%d Pick(s)", 0));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.donePickingButton) {
            goBackToCallingActivity();
        }
        else if (item.getItemId() == R.id.picksCount) {
            transitionFragment(PICKS_FRAGMENT);
        }
        return true;
    }

    @Override
    public boolean addRemoveItem(List<FilepickerFile> files, boolean add, int position) {
        Log.d("AddRemoveInfo: ", Boolean.toString(add) +", "+ Integer.toString(position) +", "+ Integer.toString(files.size()));
        if (add) {
            FilepickerCollection fc = appContext.getCollection();
            if (fc.getCollectionSize() < appContext.getConfig().getMaxFiles()) {
                fc.addFile(files.get(position));
                files.remove(position);
                menu.getItem(0).setTitle(String.format(Locale.ENGLISH, "%d Pick(s)", fc.getCollectionSize()));
                return true;
            }
            else {
                Toast.makeText(getApplicationContext(), "You have picked maximum number of files", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else {
            FilepickerCollection fc = appContext.getCollection();
            fc.removeFile(files.get(position));
            menu.getItem(0).setTitle(String.format(Locale.ENGLISH, "%d Pick(s)", fc.getCollectionSize()));
            return true;
        }
    }

    public void transitionFragment(int switchCase) {
        FragmentTransaction ft;
        switch(switchCase) {
            case PICK_FRAGMENT:
                ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.fragmentContainer, new FilePickerFragment());
                ft.addToBackStack("list-fragment");
                ft.commit();
                break;
            case PICKS_FRAGMENT:
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, new PicksListFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
        }
    }

    private void goBackToCallingActivity() {
        Log.i("Notifying: ", "DONE");
        ArrayList<FilepickerFile> list = appContext
                .getCollection()
                .copyPicks();
        Intent backToActivity = new Intent();
        backToActivity.putParcelableArrayListExtra("data", list);
        setResult(1, backToActivity);
        finish();
    }
}
