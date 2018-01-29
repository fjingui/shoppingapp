package com.learn.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/4/27/027.
 */

public class NewListView extends ListView {
    public NewListView(Context context) {
        super(context);
    }

    public NewListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newheight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, newheight);
    }
}
