package com.filepicker_android.filepicker.pickerlist;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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
import com.filepicker_android.filepicker.contextual.FilepickerFile;
import com.filepicker_android.filepicker.contextual.FilepickerFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Show list of currently loaded files
 *
 * @author alexander karmanov on 2016-10-08.
 */

public class FilePickerFragment extends Fragment implements FragmentFilterInterface,
        HostFragmentInterface {

    private static final String SAVED_PATHS = "paths";
    private static final String BACK_PATH = "backPath";
    private static final String FILE_PICKS = "filePicks";
    private DirectoryExplorer de;
    private List<FilepickerFile> files;
    private FilePickerAdapter adapter;
    private Filepicker filepicker;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerLayoutUtils rlu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filepicker = (Filepicker) getActivity();
        de = filepicker.getFilepickerContext().getDirectoryExplorer();
        if (savedInstanceState != null) {
            de.setVisitedPaths(savedInstanceState.getStringArrayList(SAVED_PATHS));
            filepicker.getFilepickerContext().getCollection().setPicks(
                    (List<FilepickerFile>) savedInstanceState.get(FILE_PICKS)
            );
        }
        rlu = new RecyclerLayoutUtils();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList(SAVED_PATHS, (ArrayList<String>) de.getVisitedPaths());
        outState.putString(BACK_PATH, de.getBackPath());
        outState.putParcelableArrayList(
                FILE_PICKS,
                (ArrayList<? extends Parcelable>)
                        filepicker.getFilepickerContext().getCollection().getPicks()
        );
        //super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View filePickerContainer = container.findViewById(R.id.view);
        View v;
        if (filePickerContainer != null) {
            container.removeView(filePickerContainer);
        }
        v = inflater.inflate(R.layout.filepicker_container, container, false);
        recycler = (RecyclerView) v.findViewById(R.id.recycler);
        recycler.setAdapter(null);
        configureLayout();
        setHasOptionsMenu(true);
        filepicker.setTitle(filepicker.getFilepickerContext().getConfig().getTitle());
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        recycler.setAdapter(adapter);
    }

    public void navigateToPath(String path) {
        new GetFilesTask().execute(path);
    }

    public FilePickerFragment getObject() {
        return this;
    }

    private void buildBreadCrumbs() {
        LinearLayout v = (LinearLayout) getView().findViewById(R.id.lc_navigation);
        v.removeAllViews();
        List<String> paths = de.getVisitedPaths();
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            TextView tv = new TextView(filepicker.getApplicationContext());
            tv.setText(" / ");
            tv.setTextColor(Color.DKGRAY);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
            v.addView(tv);

            Button b = new Button(filepicker.getApplicationContext());
            int index = path.lastIndexOf("/") == 0 ? 0 : path.lastIndexOf("/") + 1;
            String p = path.substring(index);
            b.setText(p.equals("0") ? "Home" : p);
            b.setTextColor(Color.DKGRAY);
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
            b.setBackgroundResource(R.drawable.breadcrumb_button);
            b.setOnClickListener(new BreadCrumbListener(path, de, this));
            v.addView(b);
        }
    }

    private class GetFilesTask extends AsyncTask<String, Integer, List<FilepickerFile>> {

        @Override
        protected List<FilepickerFile> doInBackground(String... paths) {
            return de.getFiles(
                    paths[0],
                    filepicker.getFilepickerContext().getCollection().getPicksPaths()
            );
        }

        @Override
        protected void onPostExecute(List<FilepickerFile> filepickerFiles) {
            if (files == null) {
                files = filepickerFiles;
                FilepickerFilter.sort(files);
                adapter = new FilePickerAdapter(getObject());
                recycler.setAdapter(adapter);
            }
            else {
                files.clear();
                files.addAll(filepickerFiles);
                FilepickerFilter.sort(files);
                adapter.notifyDataSetChanged();
            }
            buildBreadCrumbs();
        }
    }

    public List<FilepickerFile> getFiles() {
        return files;
    }

    public FilePickerAdapter getAdapter() {
        return adapter;
    }

    public Context getAppContext() {
        return filepicker.getApplicationContext();
    }

    public Filepicker getFilepicker() {
        return filepicker;
    }

    public void configureLayout() {
        rlu.changeRecyclerLayoutManager(getActivity(), recycler, layoutManager);
        if (adapter != null) {
            adapter = new FilePickerAdapter(getObject());
            recycler.setAdapter(adapter);
        }
        navigateToPath(de.getLastPath());
    }

    @Override
    public void sortList() {
        FilepickerFilter.sort(files);
        adapter.notifyDataSetChanged();
    }
}
