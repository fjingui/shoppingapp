package com.utils.list;

import android.os.Handler;
import android.os.Message;

import com.bean.list.*;
import com.bean.list.UserAcct;
import com.easemob.easeui.domain.EaseUser;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2018/9/14 0014.
 */

public class GetUserAcct {

    public com.bean.list.UserAcct user = new UserAcct();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           if(msg.what == 3333) {
               MyEaseSpUtils.CommitString(cust_acct,user.getAcct_name());
               easeuser.setNick(user.getAcct_name());
           }
        }
    };
    private EaseUser easeuser;
    private String cust_acct;
    public GetUserAcct(EaseUser easeuser, String cust_acct) {
        this.cust_acct = cust_acct;
        this.easeuser = easeuser;
    }

    public UserAcct getUserAcct(){
        RequestParams requser = new RequestParams(Global_Final.qureyacct);
        requser.addQueryStringParameter("cust_acct",cust_acct);
        x.http().post(requser, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                user = ParseJsonData.parseObjectJson(result, com.bean.list.UserAcct.class);
                handler.sendEmptyMessage(3333);
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
        return user;
    }
}
