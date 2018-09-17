package com.hold.list;

import android.view.View;
import android.widget.TextView;

import com.bean.list.WithDrawAcctBean;
import com.shop.myapplication.BaseApplication;
import com.shop.myapplication.R;

/**
 * Created by Administrator on 2018/9/5 0005.
 */

public class WithDrawAcctHold {
    private View withaccview;
    private TextView zfbacc;
    private TextView zfbname;

    public WithDrawAcctHold(){
        withaccview = initView();
    }
    public View initView(){
        View view = View.inflate(BaseApplication.getContext(), R.layout.withaccthold, null);
        zfbacc = view.findViewById(R.id.zfb_acc);
        zfbname = view.findViewById(R.id.zfb_name);
        return view;
    }
    public void setData(WithDrawAcctBean wdab){
        zfbacc.setText(wdab.getTx_account());
        zfbname.setText(wdab.getTx_account_name());
    }

    public View getWithaccview() {
        return withaccview;
    }
}
