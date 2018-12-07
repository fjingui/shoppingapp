package com.utils.list;

import com.bean.list.Global_Final;
import com.bean.list.UserAcct;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2018/9/14 0014.
 */

public class LoginUserAcct {

    public static com.bean.list.UserAcct user;

    public synchronized static void getUser(){
        if(user == null){
            user = new UserAcct();
        }
    }


    public static void getUserAcct(String cust_acct){
        RequestParams requser = new RequestParams(Global_Final.qureyacct);
        requser.addQueryStringParameter("cust_acct",cust_acct);
        x.http().post(requser, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                user = ParseJsonData.parseObjectJson(result, com.bean.list.UserAcct.class);
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
