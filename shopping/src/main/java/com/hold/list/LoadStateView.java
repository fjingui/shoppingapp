package com.hold.list;

import android.icu.text.MessagePattern;
import android.view.View;

import com.shop.myapplication.BaseApplication;
import com.shop.myapplication.R;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class LoadStateView {
    private static View loadview = View.inflate(BaseApplication.getContext(), R.layout.load_state_view, null);
    private static View loading = loadview.findViewById(R.id.loading);
    private static View loadfail = loadview.findViewById(R.id.loadfailview);
    private static View freshbtn = loadview.findViewById(R.id.freshbtn);
    public static View showLoading(boolean show){
        loading.setVisibility(show ? View.VISIBLE:View.GONE);
        loadfail.setVisibility(show ? View.GONE:View.VISIBLE);
        return loadview;
    }
    public static View showLoadFail(boolean show){
        loadfail.setVisibility(show ? View.VISIBLE:View.GONE);
        loading.setVisibility(show ? View.GONE:View.VISIBLE);
        return loadview;
    }
    public static void cancelViews(){
        loadview.setVisibility(View.GONE);
    }

    public static View getFreshbtn() {
        return freshbtn;
    }
}
