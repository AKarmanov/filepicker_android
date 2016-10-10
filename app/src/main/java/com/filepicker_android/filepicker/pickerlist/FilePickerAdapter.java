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
import com.filepicker_android.filepicker.contextual.FilepickerFilter;
import com.filepicker_android.filepicker.pickerlist.viewholder.ItemBase;
import com.filepicker_android.filepicker.pickerlist.viewholder.ItemGridDetail;
import com.filepicker_android.filepicker.pickerlist.viewholder.ItemGridSimple;
import com.filepicker_android.filepicker.pickerlist.viewholder.ItemListDetail;
import com.filepicker_android.filepicker.pickerlist.viewholder.ItemListSimple;

import java.util.List;

/**
 * Adapter for recycler view
 *
 * @author alexander karmanov on 2016-10-08.
 */

public class FilePickerAdapter extends RecyclerView.Adapter<ItemBase>  {

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
    public ItemBase onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.i("FPA", "Created view holder");
        FilepickerFilter.FilterSetting setting = FilepickerFilter.getLayoutOption();
        View v;
        ItemBase item;
        switch(setting.getOption()) {
            case FilepickerFilter.GRID :
                if (setting.getType().equals(FilepickerFilter.LAYOUT_TYPE_DETAILED)) {
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(ItemGridDetail.layoutId, parent, false);
                    item = new ItemGridDetail(v);
                    Log.i("Case", "Grid detail");
                }
                else {
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(ItemGridSimple.layoutId, parent, false);
                    item = new ItemGridSimple(v);
                    Log.i("Case", "Grid simple");
                }
                break;
            case FilepickerFilter.LIST :
                if (setting.getType().equals(FilepickerFilter.LAYOUT_TYPE_DETAILED)) {
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(ItemListDetail.layoutId, parent, false);
                    item = new ItemListDetail(v);
                    Log.i("Case", "List detail");
                }
                else {
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(ItemListSimple.layoutId, parent, false);
                    item = new ItemListSimple(v);
                    Log.i("Case", "List simple");
                }
                break;
            default :
                v = LayoutInflater.from(parent.getContext())
                        .inflate(ItemListDetail.layoutId, parent, false);
                item = new ItemListDetail(v);
                Log.i("Case", "Default");
        }
        item.setPickerFragment(pickerFragment);
        return item;
    }

    

    @Override
    public void onBindViewHolder(ItemBase holder, int position) {
//        Log.i("FPA", "On bind view holder");
        //TODO switch view here depending on current mode
        FilepickerFile item = list.get(position);
        holder.setUpView(item, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
