package com.filepicker_android.filepicker.contextual;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import java.util.Map;

/**
 * @author alexander karmanov on 2016-10-04.
 */

public class FilepickerContext {

    private FilepickerConfig config;
    private FilepickerCollection collection;
    private Map<String, Typeface> typeFaces;
    private DirectoryExplorer directoryExplorer;
    private BitmapCache bitmapCache;
    private Context applicationContext;

    public FilepickerContext(Context applicationCtx) {
        this.applicationContext = applicationCtx;
        make();
    }

    public FilepickerConfig getConfig() {
        return config;
    }

    public void setConfig(FilepickerConfig config) {
        this.config = config;
    }

    public FilepickerCollection getCollection() {
        return collection;
    }

    public Map<String, Typeface> getTypeFaces() {
        return typeFaces;
    }

    public DirectoryExplorer getDirectoryExplorer() {
        return directoryExplorer;
    }

    public BitmapCache getBitmapCache() {
        return bitmapCache;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public void make() {
        collection = new FilepickerCollection();
        typeFaces = new TypeFaces(applicationContext).getTypefaces();
        config = new FilepickerConfig();
        directoryExplorer = new DirectoryExplorer();
        bitmapCache = new BitmapCache();
        bitmapCache.setUpCache();
    }

    public boolean fileSelectable(FilepickerFile file) {
        String type = file.getType();
        if (config.getPickTypes().contains(FilepickerConfig.ANY)
                && !config.getDontPickTypes().contains(FilepickerConfig.ANY)) {
            return true;
        }
        else if (config.getDontPickTypes().contains(FilepickerConfig.ANY)){
            return false;
        }

        if (config.getDontPickTypes().contains(type)) {
            return false;
        }
        else {
            return true;
        }
    }
}
