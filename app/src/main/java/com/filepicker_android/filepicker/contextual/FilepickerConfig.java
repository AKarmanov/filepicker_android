package com.filepicker_android.filepicker.contextual;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author alexander karmanov on 2016-10-09.
 */

public class FilepickerConfig implements Parcelable {

    public static final String EXTRA_CONFIG = "extraConfig";

    public static final String EXTRA_PICK_TYPES = "pickTypes";
    public static final String EXTRA_MAX_FILES = "maxFiles";
    public static final String EXTRA_VIEW_MODE = "viewMode"; //List or grid

    public static final String ANY = "any";
    public static final String FOLDER = "folder";
    //TODO replace to mime type
    public static final String PDF = "application/pdf";
    public static final String JPG = "image/jpeg";
    public static final String PNG = "image/png";
    public static final String TEXT = "text/plain";
    public static final String HTML = "text/html";
    public static final String PPS = "application/mspowerpoint";
    public static final String PPT = "application/mspowerpoint";


    public static final String VIEW_MODE_LIST = "list";
    public static final String VIEW_MODE_GRID = "grid";

    private ArrayList<String> pickTypes = new ArrayList<>(
            Arrays.asList(new String[]{
                    ANY
            })
    );

    private ArrayList<String> dontPickTypes = new ArrayList<>();

    private int maxFiles = 10;
    private String viewMode = VIEW_MODE_LIST;

    public ArrayList<String> getPickTypes() {
        return pickTypes;
    }

    public void setPickTypes(ArrayList<String> pickTypes) {
        this.pickTypes = pickTypes;
    }

    public ArrayList<String> getDontPickTypes() {
        return dontPickTypes;
    }

    public void setDontPickTypes(ArrayList<String> dontPickTypes) {
        this.dontPickTypes = dontPickTypes;
    }

    public int getMaxFiles() {
        return maxFiles;
    }

    public void setMaxFiles(int maxFiles) {
        this.maxFiles = maxFiles;
    }

    public String getViewMode() {
        return viewMode;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }

    public FilepickerConfig() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.pickTypes);
        dest.writeStringList(this.dontPickTypes);
        dest.writeInt(this.maxFiles);
        dest.writeString(this.viewMode);
    }

    protected FilepickerConfig(Parcel in) {
        this.pickTypes = in.createStringArrayList();
        this.dontPickTypes = in.createStringArrayList();
        this.maxFiles = in.readInt();
        this.viewMode = in.readString();
    }

    public static final Creator<FilepickerConfig> CREATOR = new Creator<FilepickerConfig>() {
        @Override
        public FilepickerConfig createFromParcel(Parcel source) {
            return new FilepickerConfig(source);
        }

        @Override
        public FilepickerConfig[] newArray(int size) {
            return new FilepickerConfig[size];
        }
    };
}
