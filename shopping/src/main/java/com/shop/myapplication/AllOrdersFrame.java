package com.shop.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bean.list.Global_Final;

public class AllOrdersFrame extends BaseOrderFrame {
    private String title = "全部订单";
    private String status ="'待修改','待发货','待收货','待支付','已完成'";
    private String path = Global_Final.requestsalerorders;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOrderStatus(status);
        setOrderPath(path);
        setOrdertitle(title);
        super.initDataFromServer();
    }

    @Override
    public void onResume() {
        super.onResume();
        super.initDataFromServer();
    }
}
