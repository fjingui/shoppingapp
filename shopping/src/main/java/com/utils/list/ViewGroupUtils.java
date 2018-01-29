package com.utils.list;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by Administrator on 2017/9/8 0008.
 */

public class ViewGroupUtils {
    public static void removeParent(View view){
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup){
            ViewGroup viewgroup= (ViewGroup) parent;
            viewgroup.removeView(view);
        }
    }
}
