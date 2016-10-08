package com.filepicker_android.filepicker;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.filepicker_android.filepicker.pickerlist.FilePickerFragment;

/**
 * Manages recycler layout type selection
 *
 * @author alexander karmanov on 2016-10-08.
 */

public class RecyclerLayoutUtils {

    public static final int SPAN_COUNT = 2;
    public enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    public LayoutManagerType currentLayoutManagerType;

    public void setRecyclerLayoutManager(Context context,
                                             RecyclerView recycler,
                                             RecyclerView.LayoutManager layoutManager,
                                             LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (recycler.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recycler.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                layoutManager = new GridLayoutManager(context, SPAN_COUNT);
                currentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                layoutManager = new LinearLayoutManager(context);
                currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                layoutManager = new LinearLayoutManager(context);
                currentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        recycler.setLayoutManager(layoutManager);
        recycler.scrollToPosition(scrollPosition);
    }

    public LayoutManagerType getCurrentLayoutManagerType() {
        return currentLayoutManagerType;
    }

    public void setCurrentLayoutManagerType(LayoutManagerType currentLayoutManagerType) {
        this.currentLayoutManagerType = currentLayoutManagerType;
    }
}
