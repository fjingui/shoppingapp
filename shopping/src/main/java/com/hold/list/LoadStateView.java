package com.hold.list;

import android.icu.text.MessagePattern;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shop.myapplication.BaseApplication;
import com.shop.myapplication.R;

/**
 * Created by Administrator on 2018/1/9 0009.
 */

public class LoadStateView {
    private  View loadview = View.inflate(BaseApplication.getContext(), R.layout.load_state_view, null);
    private  View loading = loadview.findViewById(R.id.loading);
    private  View loadfail = loadview.findViewById(R.id.loadfailview);
    private Button freshbtn = (Button) loadview.findViewById(R.id.freshbtn);
    private TextView showinfo = (TextView) loadview.findViewById(R.id.showinfo);
    public  View showLoading(boolean show){
        loading.setVisibility(show ? View.VISIBLE:View.GONE);
        loadfail.setVisibility(show ? View.GONE:View.VISIBLE);
        return loadview;
    }
    public  View showLoadFail(boolean show){
        loadfail.setVisibility(show ? View.VISIBLE:View.GONE);
        loading.setVisibility(show ? View.GONE:View.VISIBLE);
        return loadview;
    }
    public void cancelViews(){
        loadview.setVisibility(View.GONE);
    }

    public Button getFreshbtn() {
        return freshbtn;
    }

    public TextView getShowinfo() {
        return showinfo;
    }
}
