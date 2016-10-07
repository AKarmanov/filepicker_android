package com.filepicker_android.filepicker;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

public class BreadCrumbListener implements Button.OnClickListener {

    private String path;
    private DirectoryExplorer de;
    private AlphaAnimation buttonClick;
    private FilePickerListFragment fplf;
    
    public BreadCrumbListener(String path, DirectoryExplorer de, FilePickerListFragment fplf) {
        this.path = path;
        this.de = de;
        buttonClick = new AlphaAnimation(1f, 0.2f);
        this.fplf = fplf;
    }

    @Override
    public void onClick(View view) {
        view.startAnimation(buttonClick);
        de.unlistPaths(path);
        fplf.navigateToPath(path);
    }
}