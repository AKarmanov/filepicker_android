package com.filepicker_android.filepicker.pickerlist.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.contextual.FilepickerContext;
import com.filepicker_android.filepicker.contextual.FilepickerFile;

/**
 * @author alexander karmanov on 2016-10-10.
 */

public class ItemGridSimple extends ItemBase {
    public static final int layoutId = R.layout.filepicker_grid_simple_item;

    public ItemGridSimple(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(FilepickerFile item) {
        super.setUpView(item);
        fileName.setText(getAdjustedTitle(item.getName()));
    }
}
