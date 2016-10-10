//package com.filepicker_android.filepicker.pickerlist;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v4.app.ListFragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.filepicker_android.filepicker.contextual.DirectoryExplorer;
//import com.filepicker_android.filepicker.Filepicker;
//import com.filepicker_android.filepicker.contextual.FilepickerFile;
//import com.filepicker_android.filepicker.R;
//import com.filepicker_android.filepicker.contextual.FilepickerContext;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * List of files to pick from
// *
// * @author alexander karmanov on 2016-10-01.
// */
//
//public class FilePickerListFragment extends ListFragment {
//
//    private static final String SAVED_PATHS = "paths";
//    private DirectoryExplorer de;
//    private List<FilepickerFile> files;
//    private FilePickerListAdapter adapter;
//    private Context appContext;
//    private Filepicker filepicker;
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Log.i("****", "Attached");
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.i("****", "Created");
//        filepicker = (Filepicker) getActivity();
//        appContext = getActivity().getApplicationContext();
//        de = new DirectoryExplorer();
//        if (savedInstanceState != null) {
//            de.setVisitedPaths(savedInstanceState.getStringArrayList(SAVED_PATHS));
//        }
//        navigateToPath(de.getLastPath());
//    }
//
//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        String newPath = files.get(position).getPath();
//        navigateToPath(newPath);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.filepicker_list_container, container, false);
//        Log.i("****", "View created");
//        setHasOptionsMenu(true);
//        return v;
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.i("****", "Paused");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.i("****", "Resumed");
//        buildBreadCrumbs();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.i("****", "Stopped");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.i("****", "Destroyed");
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.i("****", "view Destroyed");
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putStringArrayList(SAVED_PATHS, (ArrayList<String>) de.getVisitedPaths());
//        super.onSaveInstanceState(outState);
//        Log.i("****", "State saved");
//    }
//
//    public void navigateToPath(String path) {
//        new GetFilesTask().execute(path);
//    }
//
//    private void buildBreadCrumbs() {
//        LinearLayout v = (LinearLayout) getView().findViewById(R.id.lc_navigation);
//        v.removeAllViews();
//        List<String> paths = de.getVisitedPaths();
//        for (int i = 0; i < paths.size(); i++) {
//            String path = paths.get(i);
//            if (path != null) {
//                TextView tv = new TextView(appContext);
//                tv.setText(" / ");
//                tv.setTextColor(Color.DKGRAY);
//                v.addView(tv);
//
//                Button b = new Button(appContext);
//                int index = path.lastIndexOf("/") == 0 ? 0 : path.lastIndexOf("/") + 1;
//                b.setText(path.substring(index));
//                b.setTextColor(Color.DKGRAY);
//                b.setBackgroundResource(R.drawable.breadcrumb_button);
//                b.setOnClickListener(new BreadCrumbListener(path, de, this));
//                v.addView(b);
//            }
//            else {
//                Button b = new Button(appContext);
//                b.setText("Home");
//                b.setTextColor(Color.DKGRAY);
//                b.setBackgroundResource(R.drawable.breadcrumb_button);
//                b.setOnClickListener(new BreadCrumbListener(path, de, this));
//                v.addView(b);
//            }
//        }
//    }
//
//    private class FilePickerListAdapter extends ArrayAdapter<FilepickerFile> {
//
//        private Context context;
//
//        public FilePickerListAdapter(Context context, int resource, List<FilepickerFile> objects) {
//            super(context, resource, objects);
//            this.context = context;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            View itemView;
//            FilepickerFile item = getItem(position);
//
//            LayoutInflater mInflater = (LayoutInflater) context
//                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//            if (convertView == null) {
//                itemView = mInflater.inflate(R.layout.filepicker_list_item, parent, false);
//
//                holder = new ViewHolder();
//                holder.fileName = (TextView)itemView.findViewById(R.id.li_fileName);
//                holder.fileSize = (TextView)itemView.findViewById(R.id.li_fileSize);
//                holder.lastModified = (TextView)itemView.findViewById(R.id.li_lastModified);
//                holder.icon = (TextView)itemView.findViewById(R.id.li_icon);
//                holder.childCount = (TextView)itemView.findViewById(R.id.li_childCount);
//                holder.pickButton = (Button) itemView.findViewById(R.id.li_pickButton);
//                itemView.setTag(holder);
//            } else {
//                itemView = convertView;
//                holder = (ViewHolder) itemView.getTag();
//            }
//            updateListItemInfo(holder, position, item);
//            return itemView;
//        }
//
//        private void updateListItemInfo(ViewHolder holder, int position, FilepickerFile item) {
//            holder.pickButton.setOnClickListener(new PickButtonListener(position));
//            holder.fileName.setText(item.getName());
//            holder.fileSize.setText(item.getSizeString());
//            holder.lastModified.setText(item.getLastModifiedAsString());
//            if (item.isDir()) {
//                holder.childCount.setVisibility(TextView.VISIBLE);
//                holder.fileSize.setVisibility(TextView.INVISIBLE);
//                holder.pickButton.setVisibility(CheckBox.INVISIBLE);
//
//                holder.childCount.setText(String.format("%s file(s)", item.getChildCount()));
//            }
//            else {
//                holder.icon.setText(R.string.icon_file);
//                holder.childCount.setVisibility(TextView.INVISIBLE);
//                holder.fileSize.setVisibility(TextView.VISIBLE);
//                holder.pickButton.setVisibility(CheckBox.VISIBLE);
//            }
//
//
//            if (((FilepickerContext)appContext).getCollection().filePicked(item)) {
//                holder.pickButton.setTag(false);
//                holder.pickButton.setText(R.string.icon_minus);
//            }
//            else {
//                holder.pickButton.setTag(true);
//                holder.pickButton.setText(R.string.icon_plus);
//            }
//
//            holder.icon.setTypeface(((FilepickerContext)appContext).getTypeFaces().get("fontAwesome"));
//            holder.pickButton.setTypeface(((FilepickerContext)appContext).getTypeFaces().get("fontAwesome"));
//        }
//
//        private class ViewHolder {
//            TextView fileName;
//            TextView fileSize;
//            TextView icon;
//            TextView lastModified;
//            TextView childCount;
//            Button pickButton;
//        }
//
//        private class PickButtonListener implements Button.OnClickListener {
//            private int position;
//            public PickButtonListener(int position) {
//                this.position = position;
//            }
//            @Override
//            public void onClick(View view) {
//                boolean add = (Boolean) view.getTag();
//                if (add) {
//                    ((Button)view).setText(R.string.icon_minus);
//                }
//                else {
//                    ((Button)view).setText(R.string.icon_plus);
//                }
//                filepicker.addRemoveItem(files, add, position);
//            }
//        }
//
//    }
//
//    private class GetFilesTask extends AsyncTask<String, Integer, List<FilepickerFile>> {
//
//        @Override
//        protected List<FilepickerFile> doInBackground(String... paths) {
//            Log.i("Path", paths[0] == null ? "null": paths[0]);
//            return de.getFiles(paths[0]);
//        }
//
//        @Override
//        protected void onPostExecute(List<FilepickerFile> filepickerFiles) {
//            if (files == null) {
//                files = filepickerFiles;
//                adapter = new FilePickerListAdapter(getActivity(), android.R.layout.simple_list_item_1, files);
//                setListAdapter(adapter);
//            }
//            else {
//                files.clear();
//                files.addAll(filepickerFiles);
//                adapter.notifyDataSetChanged();
//            }
//            buildBreadCrumbs();
//        }
//    }
//}
