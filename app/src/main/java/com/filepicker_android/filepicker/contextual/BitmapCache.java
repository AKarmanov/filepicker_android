package com.filepicker_android.filepicker.contextual;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * @author alexander karmanov on 2016-10-14.
 */

public class BitmapCache {

    private LruCache<String, Bitmap> bitmapCache;

    public void setUpCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        bitmapCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            bitmapCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String key) {
        return bitmapCache.get(key);
    }
}
