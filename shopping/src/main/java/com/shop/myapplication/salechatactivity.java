package com.shop.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.easemob.easeui.controller.EaseUI;
import com.easemob.easeui.domain.EaseUser;
import com.easemob.easeui.ui.EaseChatFragment;

public class SaleChatActivity extends AppCompatActivity {

    private EaseUI easeui;
    private com.bean.list.UserAcct user;
    private String cust_acct;
    private EaseUser easeuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salechatactivity);
        getSupportActionBar().hide();
        EaseChatFragment chatFragment = new EaseChatFragment();

        //传入参数
        chatFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().add(R.id.mychat_container, chatFragment).commit();

    }

}
