package com.filepicker_android.filepicker.actiontab;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.filepicker_android.filepicker.Filepicker;
import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.contextual.FilepickerFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Action tab
 *
 * @author alexander karmanov on 2016-10-09.
 */

public class ActionTabFragment extends Fragment {

    private Filepicker filepicker;
    private Dialog d;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filepicker = (Filepicker) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.filepicker_action_tab, container, false);
        prepareDoneButton(v);
        prepareCancelButton(v);
        prepareDialogButton(v);
        return v;
    }

    private void prepareDoneButton(View v) {
        Button button = (Button) v.findViewById(R.id.donePickingButton);
        button.setTypeface(filepicker.getAppContext().getTypeFaces().get("fontAwesome"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filepicker.donePicking();
            }
        });
    }

    private void prepareCancelButton(View v) {
        Button button = (Button) v.findViewById(R.id.cancelPickingButton);
        button.setTypeface(filepicker.getAppContext().getTypeFaces().get("fontAwesome"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filepicker.cancelPicking();
            }
        });
    }

    private void prepareDialogButton(View v) {
        Button button = (Button) v.findViewById(R.id.configureFilterButton);
        button.setTypeface(filepicker.getAppContext().getTypeFaces().get("fontAwesome"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = new Dialog(getContext());
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.filepicker_filter);
                populateLayoutOptions((GridView) d.findViewById(R.id.layoutOptions));
                populateSortOptions((GridView) d.findViewById(R.id.sortOptions));
                d.show();
            }
        });
    }

    private void populateLayoutOptions(GridView v) {
        v.setAdapter(new GridAdapter(FilepickerFilter.layoutOption, FilepickerFilter.FILTER_LAYOUT));
    }

    private void populateSortOptions(GridView v) {
        v.setAdapter(new GridAdapter(FilepickerFilter.sortOption, FilepickerFilter.FILTER_SORT));
    }

    private class GridAdapter extends BaseAdapter {

        private List<FilepickerFilter.FilterSetting> options;
        private String filter;

        public GridAdapter(Map<String, FilepickerFilter.FilterSetting[]> map, String filterType) {
            options = new ArrayList<>();
            filter = filterType;
            Set<String> keys = map.keySet();
            for (String key : keys) {
                FilepickerFilter.FilterSetting[] options = map.get(key);
                for (int i = 0; i < options.length; i++) {
                    this.options.add(options[i]);
                }
            }
        }

        @Override
        public int getCount() {
            return options.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Button b = new Button(getContext());
            if (convertView == null) {
                b.setText(options.get(position).getOption());
                b.setBackground(null);
            }
            else {
                b = (Button) convertView;
            }

            if (options.get(position).isSet()) {
                b.setTextColor(Color.GREEN);
            }
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filepicker.notifyFilterChange(options.get(position), filter);
                    d.hide();
                }
            });
            return b;
        }
    }
}
