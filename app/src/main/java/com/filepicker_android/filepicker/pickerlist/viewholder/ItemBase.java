package com.filepicker_android.filepicker.pickerlist.viewholder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.contextual.FilepickerConfig;
import com.filepicker_android.filepicker.contextual.FilepickerContext;
import com.filepicker_android.filepicker.contextual.FilepickerFile;
import com.filepicker_android.filepicker.pickerlist.FilePickerAdapter;
import com.filepicker_android.filepicker.pickerlist.FilePickerFragment;
import com.filepicker_android.filepicker.viewholder.CommonBase;

/**
 * List item
 *
 * @author alexander karmanov on 2016-10-08.
 */

public class ItemBase extends CommonBase implements  View.OnClickListener {

    protected final TextView fileName;
    protected final TextView icon;
    protected final TextView fileSizeOrCount;
    protected final TextView lastModified;
    protected final Button pickButton;

    public ItemBase(View itemView) {
        super(itemView);
        this.fileName = (TextView)itemView.findViewById(R.id.li_fileName);
        this.lastModified = (TextView)itemView.findViewById(R.id.li_lastModified);
        this.icon = (TextView)itemView.findViewById(R.id.li_icon);
        this.fileSizeOrCount = (TextView) itemView.findViewById(R.id.li_fileSizeOrCount);
        this.pickButton = (Button) itemView.findViewById(R.id.li_pickButton);
        this.pickButton.setOnClickListener(new PickButtonListener());
        itemView.setOnClickListener(this);
    }

    public TextView getFileName() {
        return fileName;
    }

    public TextView getIcon() {
        return icon;
    }

    public TextView getLastModified() {
        return lastModified;
    }

    public Button getPickButton() {
        return pickButton;
    }

    public TextView getFileSizeOrCount() {
        return fileSizeOrCount;
    }

    @Override
    public void setUpView(FilepickerFile item) {
        FilepickerContext appContext = (FilepickerContext) pickerFragment.getAppContext();

        fileName.setText(item.getName());
        if (!appContext.fileSelectable(item)) {
            pickButton.setVisibility(Button.INVISIBLE);
        }
        else {
            pickButton.setVisibility(Button.VISIBLE);
        }

        if (item.isDir()) {
            icon.setText(R.string.icon_folder);
            imageView.setImageBitmap(null);
            if (fileSizeOrCount != null) {
                fileSizeOrCount.setText(String.format("%s file(s)", item.getChildCount()));
            }
        }
        else {
            if (isImage(item)) {
                loadImage(item);
            }
            else {
                icon.setText(R.string.icon_file);
            }
        }
        icon.setTypeface(appContext.getTypeFaces().get("fontAwesome"));
        pickButton.setTypeface(appContext.getTypeFaces().get("fontAwesome"));
    }

    @Override
    public void onClick(View view) {
        String path = pickerFragment.getFiles().get(getAdapterPosition()).getPath();
        pickerFragment.navigateToPath(path);
    }

    private class PickButtonListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (pickerFragment.getFilepicker().addRemoveItem(pickerFragment.getFiles(), true, position)) {
                FilePickerAdapter adapter = pickerFragment.getAdapter();
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
            }
        }
    }
}
