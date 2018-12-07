package com.shop.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bean.list.ApkVersion;
import com.bean.list.Global_Final;
import com.utils.list.HttpPostReqData;
import com.utils.list.ParseJsonData;

import util.UpdateAppUtils;

public class ShoppingAbout extends AppCompatActivity {

    private TextView shopping_version;
    private TextView check_version;
    private ApkVersion apkversion;
    private HttpPostReqData initapkver;
    private Handler versionHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 001){
                apkversion = ParseJsonData.parseObjectJson(initapkver.getResultdata(),ApkVersion.class);
                shopping_version.setText("版本号："+apkversion.getVersion_name());
            }
        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shopping_version = (TextView) findViewById(R.id.shopping_version);
        check_version = (TextView) findViewById(R.id.check_version);
        initVersion();
        check_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateAppUtils.from(ShoppingAbout.this)
                        .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME) //更新检测方式，默认为VersionCode
                        .serverVersionCode(apkversion.getVersion_code())
                        .serverVersionName(apkversion.getVersion_name())
                        .showNotification(true)
                        .apkPath(apkversion.getApk_path())
                        .update();
            }
        });
    }
    public void initVersion(){
        initapkver = new HttpPostReqData(versionHandler,001);
        initapkver.requestData(Global_Final.apkversion);
    }
}
