package com.filepicker_android.filepicker.actiontab;

import android.app.Dialog;
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
import android.widget.LinearLayout;

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
        button.setTypeface(filepicker.getFilepickerContext().getTypeFaces().get("fontAwesome"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filepicker.donePicking();
            }
        });
    }

    private void prepareCancelButton(View v) {
        Button button = (Button) v.findViewById(R.id.cancelPickingButton);
        button.setTypeface(filepicker.getFilepickerContext().getTypeFaces().get("fontAwesome"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filepicker.cancelPicking();
            }
        });
    }

    private void prepareDialogButton(View v) {
        Button button = (Button) v.findViewById(R.id.configureFilterButton);
        button.setTypeface(filepicker.getFilepickerContext().getTypeFaces().get("fontAwesome"));
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
        private String filterType;
        private DialogIconResolver dr;

        public GridAdapter(Map<String, FilepickerFilter.FilterSetting[]> map, String filterType) {
            //Populates options list while making sure that elements of the same type/option (size,
            // type etc.) are displayed in a column in the resulting grid
            Set<String> keys = map.keySet();
            int numOptions = keys.size();
            int numOfSortOptions = 2;
            options = new ArrayList<>();
            fillToCapacity(options, numOptions * numOfSortOptions);
            this.filterType = filterType;
            int optionStep = -1;
            for (String key : keys) {
                optionStep++;
                FilepickerFilter.FilterSetting[] options = map.get(key);
                for (int i = 0; i < options.length; i++) {
                    this.options.set(optionStep + i * numOptions, options[i]);
                }
            }
            dr = new DialogIconResolver();
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
            LinearLayout l;
            if (convertView == null) {
                l = dr.getLayoutCell(
                        getActivity(),
                        options.get(position),
                        filepicker.getFilepickerContext()
                );
            }
            else {
                l = (LinearLayout) convertView;
            }

            l.findViewWithTag(DialogIconResolver.BUTTON_TAG)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            filepicker.notifyFilterChange(options.get(position), filterType);
                            d.dismiss();
                        }
                    });
            return l;
        }

        private void fillToCapacity(List<FilepickerFilter.FilterSetting> list, int capacity) {
            for (int i = 0; i < capacity; i++) {
                list.add(null);
            }
        }
    }
}
