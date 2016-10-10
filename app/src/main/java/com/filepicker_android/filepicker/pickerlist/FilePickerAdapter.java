package com.filepicker_android.filepicker.pickerlist;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.filepicker_android.filepicker.Filepicker;
import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.contextual.FilepickerContext;
import com.filepicker_android.filepicker.contextual.FilepickerFile;
import com.filepicker_android.filepicker.pickerlist.viewholder.Item;

import java.util.List;

/**
 * Adapter for recycler view
 *
 * @author alexander karmanov on 2016-10-08.
 */

public class FilePickerAdapter extends RecyclerView.Adapter<Item>  {

    private List<FilepickerFile> list;
    private FilepickerContext appContext;
    private Filepicker filepicker;
    private FilePickerFragment pickerFragment;

    public FilePickerAdapter(FilePickerFragment pickerFragment) {
        this.filepicker = pickerFragment.getFilepicker();
        this.list = pickerFragment.getFiles();
        this.appContext = (FilepickerContext) pickerFragment.getAppContext();
        this.pickerFragment = pickerFragment;
    }

    @Override
    public Item onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("FPA", "Created view holder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filepicker_item, parent, false);
        Item item = new Item(v);
        item.setPickerFragment(pickerFragment);
        return item;
    }

    

    @Override
    public void onBindViewHolder(Item holder, int position) {
        Log.i("FPA", "On bind view holder");
        //TODO switch view here depending on current mode
        FilepickerFile item = list.get(position);
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

        holder.getIcon().setTypeface(appContext.getTypeFaces().get("fontAwesome"));
        holder.getPickButton().setTypeface(appContext.getTypeFaces().get("fontAwesome"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
