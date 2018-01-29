package com.shop.myapplication;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

/**
 * Created by Administrator on 2017/4/7/007.
 */

public class BaseApplication extends Application {
    private static Context mcontext;

    public static Context getContext() {
        return mcontext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        xUtils3.0初始化
        x.Ext.init(this);
        mcontext=getApplicationContext();
    }
}
