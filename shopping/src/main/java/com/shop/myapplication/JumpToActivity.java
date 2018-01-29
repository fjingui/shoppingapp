package com.shop.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class JumpToActivity extends AppCompatActivity {

    public static LoginCallback mcallback;
    public static String cust_acct;
    public interface LoginCallback{
        void onlogin();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent,1);
    }

    public static void jumpToLogin(Context cx ,LoginCallback logincallback ){
        mcallback=logincallback;
        Intent intent = new Intent(cx, JumpToActivity.class);
        cx.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
        if(requestCode==1 && resultCode==1 && mcallback!=null){
            this.cust_acct=data.getStringExtra("mphone");
            mcallback.onlogin();
        }
        mcallback = null;
    }
}
