//package com.filepicker_android.filepicker.pickslist;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.ListFragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.filepicker_android.filepicker.Filepicker;
//import com.filepicker_android.filepicker.contextual.FilepickerContext;
//import com.filepicker_android.filepicker.contextual.FilepickerFile;
//import com.filepicker_android.filepicker.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Shows available picks and allows removal of items
// *
// * @author alexander karmanov on 2016-10-05.
// */
//
//public class PicksListFragment extends ListFragment {
//
//    private Context appContext;
//    private Filepicker filepicker;
//    private FilePicksListAdapter adapter;
//    private List<FilepickerFile> picks;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        filepicker = (Filepicker) getActivity();
//        appContext = getActivity().getApplicationContext();
//        picks = ((FilepickerContext)appContext).getCollection().getPicks();
//        adapter = new FilePicksListAdapter(getActivity(), android.R.layout.simple_list_item_1, picks);
//        setListAdapter(adapter);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = super.onCreateView(inflater, container, savedInstanceState);
//        setHasOptionsMenu(true);
//        return v;
//    }
//
//    private class FilePicksListAdapter extends ArrayAdapter<FilepickerFile> {
//
//        private Context context;
//
//        public FilePicksListAdapter(Context context, int resource, List<FilepickerFile> objects) {
//            super(context, resource, objects);
//            this.context = context;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder = null;
//            FilepickerFile item = getItem(position);
//            View itemView = null;
//
//            LayoutInflater mInflater = (LayoutInflater) context
//                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//            if (convertView == null) {
//                itemView = mInflater.inflate(R.layout.filepicker_picks_list_item, parent, false);
//
//                holder = new ViewHolder();
//                holder.fileName = (TextView)itemView.findViewById(R.id.li_fileName);
//                holder.fileSize = (TextView)itemView.findViewById(R.id.li_fileSize);
//                holder.lastModified = (TextView)itemView.findViewById(R.id.li_lastModified);
//                holder.icon = (TextView)itemView.findViewById(R.id.li_icon);
//                holder.childCount = (TextView)itemView.findViewById(R.id.li_childCount);
//                holder.button = (Button) itemView.findViewById(R.id.removePick);
//                holder.button.setOnClickListener(new ButtonListener());
//                holder.button.setTag(position);
//                itemView.setTag(holder);
//            } else {
//                itemView = convertView;
//                holder = (ViewHolder) itemView.getTag();
//            }
//
//            holder.fileName.setText(item.getName());
//            holder.fileSize.setText(item.getSizeString());
//            holder.lastModified.setText(item.getLastModifiedAsString());
//            if (item.isDir()) {
//                holder.childCount.setVisibility(TextView.VISIBLE);
//                holder.fileSize.setVisibility(TextView.INVISIBLE);
//                holder.childCount.setText(String.format("%s file(s)", item.getChildCount()));
//            }
//            else {
//                holder.childCount.setVisibility(TextView.INVISIBLE);
//                holder.fileSize.setVisibility(TextView.VISIBLE);
//            }
//            holder.icon.setTypeface(((FilepickerContext)appContext).getTypeFaces().get("fontAwesome"));
//            holder.button.setTypeface(((FilepickerContext)appContext).getTypeFaces().get("fontAwesome"));
//            return itemView;
//        }
//
//        private class ViewHolder {
//            TextView fileName;
//            TextView fileSize;
//            TextView icon;
//            TextView lastModified;
//            TextView childCount;
//            Button button;
//        }
//
//        private class ButtonListener implements Button.OnClickListener {
//
//            @Override
//            public void onClick(View view) {
//                int position = (int) view.getTag();
//                Log.i("Removing: ", "View.....");
//                List<FilepickerFile> list = new ArrayList<>();
//                list.add(picks.get(position).deepCopy());
//                filepicker.addRemoveItem(list, false, 0);
//                adapter.notifyDataSetChanged();
//                if (picks.size() == 0) {
//                    filepicker.onBackPressed();
//                }
//            }
//        }
//
//    }
//}
