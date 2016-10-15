package com.filepicker_android.filepicker.pickslist;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.filepicker_android.filepicker.Filepicker;
import com.filepicker_android.filepicker.contextual.FilepickerContext;
import com.filepicker_android.filepicker.contextual.FilepickerFile;
import com.filepicker_android.filepicker.contextual.FilepickerFilter;
import com.filepicker_android.filepicker.pickslist.viewholder.ItemBase;
import com.filepicker_android.filepicker.pickslist.viewholder.ItemGridSimple;
import com.filepicker_android.filepicker.pickslist.viewholder.ItemListSimple;

import java.util.List;

/**
* Adapter for recycler view
*
* @author alexander karmanov on 2016-10-08.
*/

public class FilePicksAdapter extends RecyclerView.Adapter<ItemBase>  {

   private List<FilepickerFile> list;
   private FilepickerContext appContext;
   private Filepicker filepicker;
   private FilePicksFragment picksFragment;

   public FilePicksAdapter(FilePicksFragment picksFragment) {
       this.filepicker = picksFragment.getFilepicker();
       this.list = picksFragment.getFiles();
       this.appContext = (FilepickerContext) picksFragment.getAppContext();
       this.picksFragment = picksFragment;
   }



   @Override
   public ItemBase onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.i("FPA", "Created view holder");
       FilepickerFilter.FilterSetting setting = FilepickerFilter.getLayoutOption();
       View v;
       ItemBase item;
       switch(setting.getOption()) {
           case FilepickerFilter.GRID :
                   v = LayoutInflater.from(parent.getContext())
                           .inflate(ItemGridSimple.layoutId, parent, false);
                   item = new ItemGridSimple(v);
                   Log.i("Case", "Picks Grid detail");
               break;
           case FilepickerFilter.LIST :
                   v = LayoutInflater.from(parent.getContext())
                           .inflate(ItemListSimple.layoutId, parent, false);
                   item = new ItemListSimple(v);
                   Log.i("Case", "Picks List detail");
               break;
           default :
               v = LayoutInflater.from(parent.getContext())
                       .inflate(ItemListSimple.layoutId, parent, false);
               item = new ItemListSimple(v);
               Log.i("Case", "Picks Default");
       }
       item.setPicksFragment(picksFragment);
       return item;
   }



   @Override
   public void onBindViewHolder(ItemBase holder, int position) {
//        Log.i("FPA", "On bind view holder");
       FilepickerFile item = list.get(position);
       holder.setUpView(item);
   }

   @Override
   public int getItemCount() {
       return list.size();
   }
}
