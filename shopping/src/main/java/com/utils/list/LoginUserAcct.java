package com.utils.list;

import com.bean.list.*;
import com.bean.list.UserAcct;
import com.shop.myapplication.MainActivity;
import com.utils.list.ParseJsonData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2018/9/14 0014.
 */

public class LoginUserAcct {

    public static com.bean.list.UserAcct user;

    public synchronized static com.bean.list.UserAcct getUser(){
        if(user == null){
            user = new UserAcct();
        }
        return user;
    }


    public static void getUserAcct(String cust_acct){
        RequestParams requser = new RequestParams(Global_Final.qureyacct);
        requser.addQueryStringParameter("cust_acct",cust_acct);
        x.http().post(requser, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                user = ParseJsonData.parseObjectJson(result, com.bean.list.UserAcct.class);
                MainActivity.cust_acct=user.getCust_acct();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
}
