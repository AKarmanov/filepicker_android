package com.filepicker_android.filepicker;

import com.filepicker_android.filepicker.contextual.FilepickerFile;

import java.util.List;

/**
 * @author alexander karmanov on 2016-10-05.
 */

public interface FragmentToActivityInterface {
    boolean addRemoveItem(List<FilepickerFile> files, boolean add, int position);
    void transitionFragment(int switchCase);
}
