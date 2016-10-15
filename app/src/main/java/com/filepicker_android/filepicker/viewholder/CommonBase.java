package com.filepicker_android.filepicker.viewholder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.contextual.FilepickerConfig;
import com.filepicker_android.filepicker.contextual.FilepickerContext;
import com.filepicker_android.filepicker.contextual.FilepickerFile;
import com.filepicker_android.filepicker.pickerlist.FilePickerFragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author alexander karmanov on 2016-10-14.
 */

public class CommonBase extends RecyclerView.ViewHolder {

    protected FilePickerFragment pickerFragment;

    protected final ImageView imageView;

    public CommonBase(View itemView) {
        super(itemView);
        this.imageView = (ImageView) itemView.findViewById(R.id.li_image);
    }

    public void setPickerFragment(FilePickerFragment pickerFragment) {
        this.pickerFragment = pickerFragment;
    }

    public void setUpView(FilepickerFile item) {}

    protected void loadImage(FilepickerFile item) {
        Bitmap map = ((FilepickerContext)pickerFragment.getAppContext())
                .getBitmapCache()
                .getBitmapFromCache(item.getPath());
        if (map != null) {
            imageView.setImageBitmap(map);
        }
        else {
            new LoadImageTask().execute(item);
        }
    }

    protected boolean isImage(FilepickerFile item) {
        String type = item.getType();
        return FilepickerConfig.JPG.equals(type) || FilepickerConfig.PNG.equals(type);
    }

    private class LoadImageTask extends AsyncTask<FilepickerFile, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(FilepickerFile... filepickerFiles) {
            String path = filepickerFiles[0].getPath();
            Bitmap bm = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 10, out);
            Bitmap map = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
            ((FilepickerContext)pickerFragment.getAppContext()).getBitmapCache().addBitmapToCache(path, map);
            return map;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}
