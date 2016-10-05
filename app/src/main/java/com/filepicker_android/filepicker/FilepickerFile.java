package com.filepicker_android.filepicker;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

/**
 * Picked file abstraction
 *
 * @author alexander karmanov on 2016-10-01.
 */

public class FilepickerFile implements Parcelable {

    private String name;
    private String path;
    private String type;
    private int childCount;
    private long size;
    private long lastModified;
    private boolean isDir;

    public FilepickerFile() {}

    public FilepickerFile(String name, String path, String type, long size, long lastModified, boolean isDir) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.size = size;
        this.lastModified = lastModified;
        this.isDir = isDir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public String getLastModifiedAsString() {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(getLastModified());
        return DateFormat.format("dd-MMM-yyyy", cal).toString();
    }

    public String getSizeString() {
        float s;
        float i;
        int step = 1000;
        String unit = "";
        if ((i = ((float) size / step)) >= 1) {
            s = i;
            unit = " KB";
        }
        else {
            unit = " bytes";
            return Integer.toString((int)size) + unit;
        }
        if ((i = i / step) >= 1) {
            s = i;
            unit = " MB";
        }
        if ((i = i / step) >= 1) {
            s = i;
            unit = " GB";
        }
        return String.format(Locale.ENGLISH, "%.1f %s", s, unit);
    }


    @Override
    public String toString() {
        return "FilepickerFile{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", type='" + type + '\'' +
                ", childCount=" + childCount +
                ", size=" + size +
                ", lastModified=" + lastModified +
                ", isDir=" + isDir +
                '}';
    }

    protected FilepickerFile(Parcel in) {
        name = in.readString();
        path = in.readString();
        type = in.readString();
        childCount = in.readInt();
        size = in.readLong();
        lastModified = in.readLong();
        isDir = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(type);
        dest.writeInt(childCount);
        dest.writeLong(size);
        dest.writeLong(lastModified);
        dest.writeByte((byte) (isDir ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FilepickerFile> CREATOR = new Parcelable.Creator<FilepickerFile>() {
        @Override
        public FilepickerFile createFromParcel(Parcel in) {
            return new FilepickerFile(in);
        }

        @Override
        public FilepickerFile[] newArray(int size) {
            return new FilepickerFile[size];
        }
    };
}
