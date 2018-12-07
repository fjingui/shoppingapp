package com.shop.myapplication;

import android.os.Bundle;

import com.bean.list.Global_Final;

public class GettedActivity extends BaseOrderActivity {

    private String title = "待收货订单";
    private String status ="'已完成'";
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
