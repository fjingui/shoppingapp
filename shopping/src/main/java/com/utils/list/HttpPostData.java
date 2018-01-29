package com.utils.list;

import android.widget.Toast;

import com.shop.myapplication.BaseApplication;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public class HttpPostData {
    public static void PostData(String path,String json){
        RequestParams reqpara = new RequestParams(path);
        reqpara.setAsJsonContent(true);
        reqpara.setBodyContent(json);
        x.http().post(reqpara, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
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
}
