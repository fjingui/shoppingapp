package com.bean.list;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/6/9.
 */

public class SpaceItem extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItem(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.left=space;
        outRect.right=space;
        outRect.bottom=space;
        if(parent.getChildAdapterPosition(view)==0) {
            outRect.top=space;
        }

    }
}
