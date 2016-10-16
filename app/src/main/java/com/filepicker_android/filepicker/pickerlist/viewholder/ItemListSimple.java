package com.filepicker_android.filepicker.pickerlist.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.contextual.FilepickerContext;
import com.filepicker_android.filepicker.contextual.FilepickerFile;

/**
 * @author alexander karmanov on 2016-10-10.
 */

public class ItemListSimple extends ItemBase {
    public static final int layoutId = R.layout.filepicker_list_simple_item;

    public ItemListSimple(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(FilepickerFile item) {
        super.setUpView(item);
    }
}
