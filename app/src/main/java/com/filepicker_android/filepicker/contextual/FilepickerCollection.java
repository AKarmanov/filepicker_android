package com.filepicker_android.filepicker.contextual;

import android.util.Log;

import com.filepicker_android.filepicker.dirutils.FilepickerFile;

import java.util.ArrayList;
import java.util.List;

/**
 * This collection acts as an ordered set and maintained as list do avoid conversion
 *
 * @author alexander karmanov on 2016-10-02.
 */
public class FilepickerCollection {
    private List<FilepickerFile> picks;

    public FilepickerCollection() {
        picks = new ArrayList<>();
    }

    public void addFile(FilepickerFile file) {
        if (!filePicked(file)) {
            picks.add(file);
        }
    }

    public void removeFile(FilepickerFile file) {
        for (int i = 0; i < picks.size(); i++) {
            if (picks.get(i).getPath().equals(file.getPath())) {
                picks.remove(i);
            }
        }
    }

    public int getCollectionSize() {
        return picks.size();
    }

    public List<FilepickerFile> getPicks() {
        return picks;
    }

    public ArrayList<FilepickerFile> copyPicks() {
        return new ArrayList<>(picks);
    }

    public boolean filePicked(FilepickerFile file) {
        for (int i = 0; i < picks.size(); i++) {
            if (picks.get(i).getPath().equals(file.getPath())) {
                Log.i("pick-", picks.get(i).getPath() + " --- " + file.getPath());
                return true;
            }
        }
        return false;
    }
}
