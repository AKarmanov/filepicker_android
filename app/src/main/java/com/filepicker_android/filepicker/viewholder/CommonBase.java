package com.filepicker_android.filepicker.viewholder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.filepicker_android.filepicker.HostFragmentInterface;
import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.contextual.FilepickerConfig;
import com.filepicker_android.filepicker.contextual.FilepickerFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author alexander karmanov on 2016-10-14.
 */

public class CommonBase extends RecyclerView.ViewHolder {

    protected HostFragmentInterface hostFragment;
    protected final ImageView imageView;
    protected AsyncTask<FilepickerFile, Integer, Bitmap> imageLoadTask;
    protected boolean loadingImage = false;


    public CommonBase(View itemView) {
        super(itemView);
        this.imageView = (ImageView) itemView.findViewById(R.id.li_image);
    }

    public void setHostFragment(HostFragmentInterface hostFragment) {
        this.hostFragment = hostFragment;
    }

    public void setUpView(FilepickerFile item) {}

    public String getAdjustedTitle(String title) {
        int len = title.length();
        if (len > 17) {
            String newTitle = title.substring(0, 10);
            return newTitle.concat("...").concat(title.substring(len-5, len));

        }
        return title;
    }

    protected void loadImage(FilepickerFile item) {
        imageView.setVisibility(View.INVISIBLE);
        Bitmap map = hostFragment.getFilepicker().getFilepickerContext()
                .getBitmapCache()
                .getBitmapFromCache(item.getPath());
        if (map != null) {
            imageView.setImageBitmap(map);
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            if (loadingImage) {
                imageLoadTask.cancel(true);
            }
            imageLoadTask = new LoadImageTask().execute(item);
            loadingImage = true;
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
            Bitmap mutableBitmap = getResizedBitmap(
                    bm,
                    hostFragment.getFilepicker().getFilepickerContext().getConfig().getMaxImageSize()
            );
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            mutableBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            Bitmap map = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
            hostFragment.getFilepicker().getFilepickerContext().getBitmapCache().addBitmapToCache(path, map);
            return map;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            loadingImage = false;
            imageView.setVisibility(View.VISIBLE);
        }

        private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
            int width = image.getWidth();
            int height = image.getHeight();

            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 1) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }
            return Bitmap.createScaledBitmap(image, width, height, true);
        }
    }
}
