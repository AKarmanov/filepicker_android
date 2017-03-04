package com.filepicker_android.filepicker.pickslist;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.filepicker_android.filepicker.Filepicker;
import com.filepicker_android.filepicker.FragmentFilterInterface;
import com.filepicker_android.filepicker.HostFragmentInterface;
import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.RecyclerLayoutUtils;
import com.filepicker_android.filepicker.contextual.DirectoryExplorer;
import com.filepicker_android.filepicker.contextual.FilepickerContext;
import com.filepicker_android.filepicker.contextual.FilepickerFile;
import com.filepicker_android.filepicker.contextual.FilepickerFilter;
import com.filepicker_android.filepicker.pickerlist.BreadCrumbListener;
import com.filepicker_android.filepicker.pickerlist.FilePickerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Show list of currently loaded files
 *
 * @author alexander karmanov on 2016-10-08.
 */

public class FilePicksFragment extends Fragment implements FragmentFilterInterface,
        HostFragmentInterface {

    private List<FilepickerFile> files;
    private FilePicksAdapter adapter;
    private Context appContext;
    private Filepicker filepicker;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerLayoutUtils rlu;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("****", "Attached");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("****", "Created");
        filepicker = (Filepicker) getActivity();
        files = filepicker.getFilepickerContext().getCollection().getPicks();
        FilepickerFilter.sort(files);
        rlu = new RecyclerLayoutUtils();
        adapter = new FilePicksAdapter(getObject());
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("****", "State saved");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.filepicker_picks_container, container, false);
        recycler = (RecyclerView) v.findViewById(R.id.recycler);
        recycler.setAdapter(null);
        configureLayout();
        Log.i("****", "View created");
        setHasOptionsMenu(true);
        filepicker.setTitle("Picked files");
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("****", "Resumed");
        recycler.setAdapter(adapter);
    }


    public FilePicksFragment getObject() {
        return this;
    }


    public List<FilepickerFile> getFiles() {
        return files;
    }

    public FilePicksAdapter getAdapter() {
        return adapter;
    }

    public Context getAppContext() {
        return appContext;
    }

    public Filepicker getFilepicker() {
        return filepicker;
    }

    public void configureLayout() {
        rlu.changeRecyclerLayoutManager(getActivity(), recycler, layoutManager);
        if (adapter != null) {
            adapter.onDetachedFromRecyclerView(recycler);
            adapter = new FilePicksAdapter(getObject());
            recycler.setAdapter(adapter);
        }
    }

    @Override
    public void sortList() {
        FilepickerFilter.sort(files);
        adapter.notifyDataSetChanged();
    }
}
