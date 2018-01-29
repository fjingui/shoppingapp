package com.learn.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bean.list.Seller;

/**
 * Created by Administrator on 2017/8/29 0029.
 */

public class OrderBroadcastReceiver extends BroadcastReceiver {
    public int amountvalue;
    public Seller smpseller;
    public int additemflag;
    @Override
    public void onReceive(Context context, Intent intent) {
        amountvalue=intent.getExtras().getInt("amountValue");
        smpseller= (Seller) intent.getExtras().getSerializable("smpSeller");
        additemflag=intent.getIntExtra("clkbtnflag",1);
        System.out.println("收到信息"+additemflag );
    }
}
