package com.shop.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bean.list.Global_Final;

public class DpayFrame extends BaseOrderFrame {

    private String title = "待付款订单";
    private String status ="'待支付'";
    private String path = Global_Final.requestsalerorders;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOrderStatus(status);
        setOrderPath(path);
        setOrdertitle(title);
        super.initDataFromServer();
    }
}
