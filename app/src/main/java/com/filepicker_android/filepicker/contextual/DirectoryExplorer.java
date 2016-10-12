package com.filepicker_android.filepicker.contextual;

import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Allows to explore directories on the device
 *
 * @author alexander karmanov on 2016-10-01.
 */

public class DirectoryExplorer {


    private List<String> visitedPaths;

    public DirectoryExplorer() {
        visitedPaths = new ArrayList<>();
        visitedPaths.add(getInitialPath());
    }

    public List<FilepickerFile> getFiles(String path) {
        if (!visitedPaths.contains(path)) {
            visitedPaths.add(path);
        }
        File[] files = getDirectoryContents(path);
        List<FilepickerFile> list = new ArrayList<>();
        for (int i = 0; files!= null && i < files.length; i++) {
            File file = files[i];
            FilepickerFile f = new FilepickerFile();
            f.setName(file.getName());
            f.setPath(file.getPath());
            f.setType(null);
            f.setSize(file.length());
            f.setLastModified(file.lastModified());
            f.setDir(file.isDirectory());
            if (f.isDir()) {
                f.setChildCount(file.listFiles().length);
            }
            list.add(f);
        }
        getMimeTypesForFiles(list);
        return list;
    }

    public List<String> getVisitedPaths() {
        return visitedPaths;
    }

    public void setVisitedPaths(List<String> visitedPaths) {
        this.visitedPaths = visitedPaths;
    }

    public String getLastPath() {
        if (visitedPaths.size() == 0) {
            return null;
        }
        return visitedPaths.get(visitedPaths.size() - 1);
    }

    public String getBackPath() {
        if (visitedPaths.size() == 1) {
            return visitedPaths.get(visitedPaths.size() - 1);
        }
        return visitedPaths.get(visitedPaths.size() - 2);
    }

    public void unlistPaths(String path) {
        Iterator<String> it = visitedPaths.iterator();
        boolean clear = false;
        while (it.hasNext()) {
            String p = it.next();
            if (p.equals(path)){
                clear = true;
            }
            if (clear) {
                it.remove();
            }
        }
        Log.i("S", visitedPaths.toString());
    }

    private void getMimeTypesForFiles(List<FilepickerFile> files) {
        for (FilepickerFile f : files) {
            String name = f.getName();
            String extension = name.substring(name.lastIndexOf(".") + 1);
            f.setType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension));
        }
    }

    private File[] getDirectoryContents(String path) {
        File directory = new File(path);
        return directory.listFiles();
    }

    private String getInitialPath() {
        return Environment.getExternalStorageDirectory().toString();
    }
}
