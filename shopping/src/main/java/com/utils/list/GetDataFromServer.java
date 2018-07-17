package com.utils.list;

import android.os.Handler;
import android.widget.FrameLayout;

import com.bean.list.Global_Final;
import com.bean.list.Seller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hold.list.LoadStateView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class GetDataFromServer {
    private FrameLayout loadprogress;
    private Handler loaddatahandler;
    private int mes;
    private String getresult;
    private String state;
    private LoadStateView loaddatastate=new LoadStateView();
    private RequestParams param=new RequestParams();

    public GetDataFromServer(Handler loaddatahandler, FrameLayout loadprogress, int mes) {
        this.loaddatahandler = loaddatahandler;
        this.loadprogress = loadprogress;
        this.mes = mes;
    }

    public GetDataFromServer() {
    }

    public String getState() {
        return state;
    }

    public String getGetresult() {
        return getresult;
    }

    public void setParam(String addstr){
        this.param.addQueryStringParameter("cust_acct",addstr);
    }
    public void setParam2(String str){
        this.param.addQueryStringParameter("orderstatus",str);
    }
    public void setParam3(String proname){this.param.addQueryStringParameter("product_name",proname);}
    public void setParam4(String facid){this.param.addQueryStringParameter("factory_id",facid);}
    public void setParam5(int product_id){this.param.addQueryStringParameter("product_id",product_id+"");}
    public void delData(String url){
        param.setUri(url);
        x.http().post(param, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                getresult=result;
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
    public void getData(String url){
        param.setUri(url);
       // param = new RequestParams(url);
        x.http().get(param, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        state="success";
                        getresult=result;
                        loaddatahandler.sendEmptyMessage(mes);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        state="error";
                        loadprogress.removeAllViews();
                        loadprogress.addView(loaddatastate.showLoadFail(true));
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        state="cancel";
                        loadprogress.removeAllViews();
                        loadprogress.addView(loaddatastate.showLoadFail(true));
                    }

                    @Override
                    public void onFinished() {
                    }

                }
        );
    }
}
