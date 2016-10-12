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
    public void setUpView(FilepickerFile item, int position) {
        super.setUpView(item, position);
        FilepickerContext appContext = (FilepickerContext) pickerFragment.getAppContext();

        fileName.setText(item.getName());
        if (item.isDir()) {
            //
        }
        else {
            icon.setText(R.string.icon_file);
        }

        icon.setTypeface(appContext.getTypeFaces().get("fontAwesome"));
        pickButton.setTypeface(appContext.getTypeFaces().get("fontAwesome"));
    }
}
