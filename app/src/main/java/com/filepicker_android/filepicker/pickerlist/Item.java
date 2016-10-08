package com.filepicker_android.filepicker.pickerlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.filepicker_android.filepicker.R;

/**
 * List item
 *
 * @author alexander karmanov on 2016-10-08.
 */

public class Item extends RecyclerView.ViewHolder {

    private final TextView fileName;
    private final TextView fileSize;
    private final TextView icon;
    private final TextView lastModified;
    private final TextView childCount;
    private final Button pickButton;

    public Item(View itemView) {
        super(itemView);
        this.fileName = (TextView)itemView.findViewById(R.id.li_fileName);
        this.fileSize = (TextView)itemView.findViewById(R.id.li_fileSize);
        this.lastModified = (TextView)itemView.findViewById(R.id.li_lastModified);
        this.icon = (TextView)itemView.findViewById(R.id.li_icon);
        this.childCount = (TextView)itemView.findViewById(R.id.li_childCount);
        this.pickButton = (Button) itemView.findViewById(R.id.li_pickButton);
    }

    public TextView getFileName() {
        return fileName;
    }

    public TextView getFileSize() {
        return fileSize;
    }

    public TextView getIcon() {
        return icon;
    }

    public TextView getLastModified() {
        return lastModified;
    }

    public TextView getChildCount() {
        return childCount;
    }

    public Button getPickButton() {
        return pickButton;
    }
}
