package com.shop.myapplication;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.easeui.controller.EaseUI;
import com.easemob.easeui.domain.EaseUser;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.utils.list.GetUserAcct;
import com.utils.list.LoginUserAcct;
import com.utils.list.MyEaseSpUtils;

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
        mcontext = getApplicationContext();
//        the following line is important
        Fresco.initialize(getApplicationContext());

        MyEaseSpUtils.init(this);
        LoginUserAcct.getUser();
        EaseUI.getInstance().init(this);
        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
        EMChat.getInstance().init(this);
        //   EMClient.getInstance().setDebugMode(true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public EaseUser getUserInfo(String username) {

        EaseUser user = new EaseUser(username);
        GetUserAcct gua = new GetUserAcct(user, username);

        if (username.equals(EMChatManager.getInstance().getCurrentUser())) {
            user.setNick(LoginUserAcct.user.getAcct_name());
        } else {
            if (TextUtils.isEmpty(MyEaseSpUtils.getString(username, ""))) {
                gua.getUserAcct();
            } else {
                user.setNick(MyEaseSpUtils.getString(username, ""));
            }

        }
        return user;
    }
}
