package com.filepicker_android.filepicker;

import android.app.Application;
import android.graphics.Typeface;

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
}
