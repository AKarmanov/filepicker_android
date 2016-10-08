package com.filepicker_android.filepicker.pickerlist;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.contextual.FilepickerContext;
import com.filepicker_android.filepicker.dirutils.FilepickerFile;

import java.util.List;

/**
 * Adapter for recycler view
 *
 * @author alexander karmanov on 2016-10-08.
 */

public class FilePickerAdapter extends RecyclerView.Adapter<Item> {

    private List<FilepickerFile> list;
    private FilepickerContext appContext;

    public FilePickerAdapter(List<FilepickerFile> list) {
        this.list = list;
    }

    @Override
    public Item onCreateViewHolder(ViewGroup parent, int viewType) {
        appContext = (FilepickerContext)parent.getContext().getApplicationContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filepicker_item, parent, false);
        return new Item(v);
    }

    @Override
    public void onBindViewHolder(Item holder, int position) {
        FilepickerFile item = list.get(position);
//        holder.getPickButton().setOnClickListener(new FilePickerListFragment.FilePickerListAdapter.PickButtonListener(position));
        holder.getFileName().setText(item.getName());
        holder.getFileSize().setText(item.getSizeString());
        holder.getLastModified().setText(item.getLastModifiedAsString());
        if (item.isDir()) {
            holder.getChildCount().setVisibility(TextView.VISIBLE);
            holder.getFileSize().setVisibility(TextView.INVISIBLE);
            holder.getChildCount().setText(String.format("%s file(s)", item.getChildCount()));
        }
        else {
            holder.getIcon().setText(R.string.icon_file);
            holder.getChildCount().setVisibility(TextView.INVISIBLE);
            holder.getFileSize().setVisibility(TextView.VISIBLE);
        }

        if (!appContext.fileSelectable(item)) {
            holder.getPickButton().setVisibility(Button.INVISIBLE);
        }
        else {
            holder.getPickButton().setVisibility(Button.VISIBLE);
        }


        if (appContext.getCollection().filePicked(item)) {
            holder.getPickButton().setTag(false);
            holder.getPickButton().setText(R.string.icon_minus);
        }
        else {
            holder.getPickButton().setTag(true);
            holder.getPickButton().setText(R.string.icon_plus);
        }

        holder.getIcon().setTypeface(appContext.getTypeFaces().get("fontAwesome"));
        holder.getPickButton().setTypeface(appContext.getTypeFaces().get("fontAwesome"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
