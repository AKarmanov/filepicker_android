package com.filepicker_android.filepicker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alexander karmanov on 2016-10-02.
 */
public class FilepickerCollection {
    private List<FilepickerFile> picks;

    public FilepickerCollection() {
        picks = new ArrayList<>();
    }

    public void addFile(FilepickerFile file) {
        picks.add(file);
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

    public ArrayList<FilepickerFile> getPicks() {
        return new ArrayList<>(picks);
    }
}
