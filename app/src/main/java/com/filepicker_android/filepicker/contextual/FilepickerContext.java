package com.filepicker_android.filepicker.contextual;

import android.app.Application;
import android.graphics.Typeface;

import java.util.Map;

/**
 * @author alexander karmanov on 2016-10-04.
 */

public class FilepickerContext extends Application {

    private FilepickerConfig config;
    private FilepickerCollection collection;
    private Map<String, Typeface> typeFaces;
    private DirectoryExplorer directoryExplorer;
    private BitmapCache bitmapCache;

    public FilepickerContext() {}

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

    @Override
    public void onCreate() {
        super.onCreate();
        collection = new FilepickerCollection();
        typeFaces = new TypeFaces(getApplicationContext()).getTypefaces();
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

        if (config.getPickTypes().contains(type) && !config.getDontPickTypes().contains(type)) {
            return true;
        }
        else {
            return false;
        }
    }
}
