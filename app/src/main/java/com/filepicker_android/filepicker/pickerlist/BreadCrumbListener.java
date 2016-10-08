package com.filepicker_android.filepicker.pickerlist;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.filepicker_android.filepicker.dirutils.DirectoryExplorer;

public class BreadCrumbListener implements Button.OnClickListener {

    private String path;
    private DirectoryExplorer de;
    private AlphaAnimation buttonClick;
    private FilePickerFragment fpf;
    
    public BreadCrumbListener(String path, DirectoryExplorer de, FilePickerFragment fpf) {
        this.path = path;
        this.de = de;
        buttonClick = new AlphaAnimation(1f, 0.2f);
        this.fpf = fpf;
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(buttonClick);
        de.unlistPaths(path);
        fpf.navigateToPath(path);
    }
}