package com.utils.list;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class RemoveParent {
    public static void removeParent(View view){
        ViewParent parent = view.getParent();
        if(parent instanceof ViewGroup){
            ((ViewGroup) parent).removeView(view);
        }
    }
}
