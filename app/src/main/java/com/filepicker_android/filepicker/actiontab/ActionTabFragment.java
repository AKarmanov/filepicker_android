package com.filepicker_android.filepicker.actiontab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.filepicker_android.filepicker.R;

/**
 * Action tab
 *
 * @author alexander karmanov on 2016-10-09.
 */

public class ActionTabFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.filepicker_action_tab, container, false);
        return v;
    }
}
