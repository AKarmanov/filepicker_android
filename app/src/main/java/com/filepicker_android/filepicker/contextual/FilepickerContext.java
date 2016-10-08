package com.filepicker_android.filepicker.contextual;

import android.app.Application;
import android.graphics.Typeface;

import com.filepicker_android.filepicker.dirutils.FilepickerFile;

import java.util.List;
import java.util.Map;

/**
 * @author alexander karmanov on 2016-10-04.
 */

public class FilepickerContext extends Application {

    private FilepickerCollection collection;
    private Map<String, Typeface> typeFaces;

    public FilepickerContext() {}

    public FilepickerCollection getCollection() {
        return collection;
    }

    public Map<String, Typeface> getTypeFaces() {
        return typeFaces;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        collection = new FilepickerCollection();
        typeFaces = new TypeFaces(getApplicationContext()).getTypefaces();
    }

    public boolean fileSelectable(FilepickerFile file) {
        return true;
    }
}
