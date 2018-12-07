package com.shop.myapplication;

import android.os.Bundle;

import com.bean.list.Global_Final;

public class DpayActivity extends BaseOrderActivity {

    private String title = "待付款订单";
    private String status ="'待支付'";
    private String path = Global_Final.requestorderpath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrderStatus(status);
        setOrderPath(path);
        setOrdertitle(title);
        super.initDataFromServer();
    }
}
