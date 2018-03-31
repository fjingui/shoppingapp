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
    private RequestParams param=new RequestParams();

    public GetDataFromServer(Handler loaddatahandler, FrameLayout loadprogress, int mes) {
        this.loaddatahandler = loaddatahandler;
        this.loadprogress = loadprogress;
        this.mes = mes;
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
    public void getData(String url){
        param.setUri(url);
       // param = new RequestParams(url);
        x.http().get(param, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        getresult=result;
                        loaddatahandler.sendEmptyMessage(mes);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        loadprogress.removeAllViews();
                        loadprogress.addView(LoadStateView.showLoadFail(true));
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        loadprogress.removeAllViews();
                        loadprogress.addView(LoadStateView.showLoadFail(true));
                    }

                    @Override
                    public void onFinished() {
                    }

                }
        );
    }
}
