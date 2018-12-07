package com.utils.list;

import android.os.Handler;
import android.widget.Toast;

import com.shop.myapplication.BaseApplication;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public class HttpPostReqData {
    private Handler noticehandler;
    int mes;
    private String resultdata;
    RequestParams postparam = new RequestParams();

    public void setParam1(float express_chrg){
        this.postparam.addQueryStringParameter("express_chrg", express_chrg + "");
    }
    public void setParam2(float discount_chrg){
        this.postparam.addQueryStringParameter("discount_chrg", discount_chrg + "");
    }
    public void setParam3(String cust_order_id){
        this.postparam.addQueryStringParameter("cust_order_id", cust_order_id);
    }
    public void setParam4(float totalmoney){
        this.postparam.addQueryStringParameter("order_money", totalmoney + "");
    }
    public void setParam5(String order_status){
        this.postparam.addQueryStringParameter("order_status", order_status );
    }

    public HttpPostReqData(Handler noticehandler, int mes) {
        this.noticehandler = noticehandler;
        this.mes = mes;
    }

    public HttpPostReqData() {

    }

    public void requestData(String path){
        RequestParams reqpara = new RequestParams(path);
        x.http().post(reqpara, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                resultdata = result;
                noticehandler.sendEmptyMessage(mes);
              //  Toast.makeText(BaseApplication.getContext(),"成功",Toast.LENGTH_SHORT).show();
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

    public void PostData(String path, String json){
        RequestParams reqpara = new RequestParams(path);
        reqpara.setAsJsonContent(true);
        reqpara.setBodyContent(json);
        x.http().post(reqpara, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                noticehandler.sendEmptyMessage(mes);
                Toast.makeText(BaseApplication.getContext(),"成功",Toast.LENGTH_SHORT).show();
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
    public void postData02(String path){
        postparam.setUri(path);
        x.http().post(postparam, new Callback.CommonCallback<String>() {
            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String result) {
                Toast.makeText(BaseApplication.getContext(),"提交成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getResultdata() {
        return resultdata;
    }
}
