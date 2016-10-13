package com.filepicker_android.filepicker.actiontab;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.filepicker_android.filepicker.R;
import com.filepicker_android.filepicker.contextual.FilepickerContext;
import com.filepicker_android.filepicker.contextual.FilepickerFilter;


/**
 * @author alexander karmanov on 2016-10-13.
 */

public class DialogIconResolver {

    public static final String BUTTON_TAG = "buttonTag";

    public LinearLayout getLayoutCell(Context activity,
                                      FilepickerFilter.FilterSetting setting,
                                      FilepickerContext fc) {
        LinearLayout l = new LinearLayout(activity);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,
                GridView.LayoutParams.WRAP_CONTENT));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        Button b = new Button(activity);
        b.setLayoutParams(lp);
        b.setText(setting.getIcon());
        b.setTypeface(fc.getTypeFaces().get("fontAwesome"));
        b.setBackgroundColor(ContextCompat.getColor(activity, R.color.transparent));
        b.setTag(BUTTON_TAG);


        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        TextView tv = new TextView(activity);
        tv.setLayoutParams(lp2);
        tv.setText(setting.getText());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
        tv.setGravity(Gravity.CENTER);

        if (setting.isSet()) {
            b.setTextColor(ContextCompat.getColor(activity, R.color.bb_textDark));
            tv.setTextColor(ContextCompat.getColor(activity, R.color.bb_textDark));
        }
        else {
            b.setTextColor(ContextCompat.getColor(activity, R.color.bb_textLight));
            tv.setTextColor(ContextCompat.getColor(activity, R.color.bb_textLight));
        }

        l.addView(b);
        l.addView(tv);
        return l;
    }
}
