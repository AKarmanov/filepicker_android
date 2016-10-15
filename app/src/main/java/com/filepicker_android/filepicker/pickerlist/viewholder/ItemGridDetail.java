package com.filepicker_android.filepicker.pickerlist.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.contextual.FilepickerContext;
import com.filepicker_android.filepicker.contextual.FilepickerFile;

/**
 * @author alexander karmanov on 2016-10-10.
 */

public class ItemGridDetail extends ItemBase {
    public static final int layoutId = R.layout.filepicker_grid_detail_item;

    public ItemGridDetail(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(FilepickerFile item) {
        fileSizeOrCount.setText(item.getSizeString());
        lastModified.setText(item.getLastModifiedAsString());
        super.setUpView(item);
    }
}
