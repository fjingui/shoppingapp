package com.shop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

/**
 * Created by Administrator on 2017/4/15/015.
 */

public class SearchActivity extends AppCompatActivity {

    @ViewInject(R.id.keysearch)
    SearchView seachview;
    private String cust_acct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.searchview);
        x.view().inject(this);
        cust_acct=getIntent().getStringExtra("cust_acct");
        seachview.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                Message msg = Message.obtain();
                msg.obj=string;
                keyhandler.sendMessage(msg);

            }
        });
        seachview.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
    }
    private Handler keyhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String str = (String) msg.obj;
            Intent intent = new Intent(SearchActivity.this,SearchListActivity.class);
            intent.putExtra("searchkey",str);
            intent.putExtra("cust_acct",cust_acct);
            startActivity(intent);
        }
    };

    }

