package com.learn.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/10/15 0015.
 */

public class LoginBroadReceiver extends BroadcastReceiver {
    public String cust_acct;
    @Override
    public void onReceive(Context context, Intent intent) {
        cust_acct = intent.getStringExtra("cust_acct");
    }
}
