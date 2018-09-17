package com.shop.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bean.list.Global_Final;

public class DreceiveActivity extends BaseOrderActivity {

    private String title = "待收货订单";
    private String status ="'待收货'";
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
