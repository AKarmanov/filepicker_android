package com.filepicker_android.filepicker;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author alexander karmanov on 2016-10-01.
 */
public class TypeFaces {
    
    public Map<String, Typeface> typefaces;

    public TypeFaces(Context context) {
        Typeface font = Typeface.createFromAsset( context.getAssets(), "fontawesome-webfont.ttf" );
        typefaces =  new Hashtable<>();
        typefaces.put("fontAwesome", font);
    }

    public Map<String, Typeface> getTypefaces() {
        return typefaces;
    }
}
