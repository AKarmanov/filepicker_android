package com.filepicker_android.filepicker.pickerlist.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.pickerlist.FilePickerAdapter;
import com.filepicker_android.filepicker.pickerlist.FilePickerFragment;

/**
 * List item
 *
 * @author alexander karmanov on 2016-10-08.
 */

public class Item extends RecyclerView.ViewHolder {

    private final TextView fileName;
    private final TextView fileSize;
    private final TextView icon;
    private final TextView lastModified;
    private final TextView childCount;
    private final Button pickButton;

    private FilePickerFragment pickerFragment;

    public Item(View itemView) {
        super(itemView);
        this.fileName = (TextView)itemView.findViewById(R.id.li_fileName);
        this.fileSize = (TextView)itemView.findViewById(R.id.li_fileSize);
        this.lastModified = (TextView)itemView.findViewById(R.id.li_lastModified);
        this.icon = (TextView)itemView.findViewById(R.id.li_icon);
        this.childCount = (TextView)itemView.findViewById(R.id.li_childCount);
        this.pickButton = (Button) itemView.findViewById(R.id.li_pickButton);
        this.pickButton.setOnClickListener(new PickButtonListener());
        itemView.setOnClickListener(new ItemClickListener());
    }

    public TextView getFileName() {
        return fileName;
    }

    public TextView getFileSize() {
        return fileSize;
    }

    public TextView getIcon() {
        return icon;
    }

    public TextView getLastModified() {
        return lastModified;
    }

    public TextView getChildCount() {
        return childCount;
    }

    public Button getPickButton() {
        return pickButton;
    }

    public void setPickerFragment(FilePickerFragment pickerFragment) {
        this.pickerFragment = pickerFragment;
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

    private class ItemClickListener implements View.OnClickListener {
        private AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.2f);
        @Override
        public void onClick(View view) {
            view.startAnimation(alphaAnimation);
            String path = pickerFragment.getFiles().get(getAdapterPosition()).getPath();
            pickerFragment.navigateToPath(path);
        }
    }
}
