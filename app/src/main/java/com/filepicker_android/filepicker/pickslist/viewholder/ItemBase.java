package com.filepicker_android.filepicker.pickslist.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.contextual.FilepickerContext;
import com.filepicker_android.filepicker.contextual.FilepickerFile;
import com.filepicker_android.filepicker.pickslist.FilePicksAdapter;
import com.filepicker_android.filepicker.pickslist.FilePicksFragment;
import com.filepicker_android.filepicker.viewholder.CommonBase;

/**
 * List item
 *
 * @author alexander karmanov on 2016-10-08.
 */

public class ItemBase extends CommonBase {

    protected final TextView fileName;
    protected final TextView icon;
    protected final Button removeButton;

    protected FilePicksFragment picksFragment;

    public ItemBase(View itemView) {
        super(itemView);
        this.fileName = (TextView)itemView.findViewById(R.id.li_fileName);;
        this.icon = (TextView)itemView.findViewById(R.id.li_icon);
        this.removeButton = (Button) itemView.findViewById(R.id.li_removeButton);
        this.removeButton.setOnClickListener(new removeButtonListener());
    }

    public TextView getFileName() {
        return fileName;
    }
    
    public Button getremoveButton() {
        return removeButton;
    }

    public void setPicksFragment(FilePicksFragment picksFragment) {
        this.picksFragment = picksFragment;
    }

    @Override
    public void setUpView(FilepickerFile item) {
        FilepickerContext appContext = (FilepickerContext) picksFragment.getAppContext();
        fileName.setText(item.getName());

        if (item.isDir()) {
            icon.setText(R.string.icon_folder);
            imageView.setVisibility(View.INVISIBLE);
        }
        else {
            if (isImage(item)) {
                loadImage(item);
                imageView.setVisibility(View.VISIBLE);
            }
            else {
                icon.setText(R.string.icon_file);
            }
        }

        icon.setTypeface(appContext.getTypeFaces().get("fontAwesome"));
        removeButton.setTypeface(appContext.getTypeFaces().get("fontAwesome"));
    }


    private class removeButtonListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (picksFragment.getFilepicker().addRemoveItem(picksFragment.getFiles(), false, position)) {
                FilePicksAdapter adapter = picksFragment.getAdapter();
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                if (picksFragment.getFiles().size() < 1) {
                    picksFragment.getFilepicker().onBackPressed();
                }
            }
        }
    }
}
