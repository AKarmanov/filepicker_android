package com.filepicker_android.filepicker.contextual;

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

    public List<FilepickerFile> getPicks() {
        return picks;
    }

    public ArrayList<FilepickerFile> copyPicks() {
        return new ArrayList<>(picks);
    }

    public List<String> getPicksPaths() {
        List<String> paths = new ArrayList<>();
        for (FilepickerFile f : picks) {
            paths.add(f.getPath());
        }
        return paths;
    }
}
