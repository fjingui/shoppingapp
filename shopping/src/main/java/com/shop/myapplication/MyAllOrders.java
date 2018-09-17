package com.shop.myapplication;

import android.os.Bundle;

import com.bean.list.Global_Final;

public class MyAllOrders extends BaseOrderActivity {

    //剔除购物车订单
    private String title = "全部订单";
    private String status ="'待收货','待付款','已验收'";
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
