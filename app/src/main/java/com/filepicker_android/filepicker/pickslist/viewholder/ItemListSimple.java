package com.filepicker_android.filepicker.pickslist.viewholder;

import android.view.View;
import android.widget.Button;

import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.contextual.FilepickerContext;
import com.filepicker_android.filepicker.contextual.FilepickerFile;

/**
 * @author alexander karmanov on 2016-10-10.
 */

public class ItemListSimple extends ItemBase {
    public static final int layoutId = R.layout.filepicker_picks_list_simple_item;

    public ItemListSimple(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(FilepickerFile item, int position) {
        FilepickerContext appContext = (FilepickerContext) picksFragment.getAppContext();
        fileName.setText(item.getName());
        if (item.isDir()) {
            //
        }
        else {
            icon.setText(R.string.icon_file);
        }

        icon.setTypeface(appContext.getTypeFaces().get("fontAwesome"));
        removeButton.setTypeface(appContext.getTypeFaces().get("fontAwesome"));
    }
}
