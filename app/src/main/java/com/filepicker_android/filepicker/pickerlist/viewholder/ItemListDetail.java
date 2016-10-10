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

public class ItemListDetail extends ItemBase {

    public static final int layoutId = R.layout.filepicker_list_detail_item;

    public ItemListDetail(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(FilepickerFile item, int position) {
        FilepickerContext appContext = (FilepickerContext) pickerFragment.getAppContext();

        fileName.setText(item.getName());
        fileSize.setText(item.getSizeString());
        lastModified.setText(item.getLastModifiedAsString());
        if (item.isDir()) {
            childCount.setVisibility(TextView.VISIBLE);
            fileSize.setVisibility(TextView.INVISIBLE);
            childCount.setText(String.format("%s file(s)", item.getChildCount()));
        }
        else {
            icon.setText(R.string.icon_file);
            childCount.setVisibility(TextView.INVISIBLE);
            fileSize.setVisibility(TextView.VISIBLE);
        }

        if (!appContext.fileSelectable(item)) {
            pickButton.setVisibility(Button.INVISIBLE);
        }
        else {
            pickButton.setVisibility(Button.VISIBLE);
        }

        icon.setTypeface(appContext.getTypeFaces().get("fontAwesome"));
        pickButton.setTypeface(appContext.getTypeFaces().get("fontAwesome"));
    }
}
