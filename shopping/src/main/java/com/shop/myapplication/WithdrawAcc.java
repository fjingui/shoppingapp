package com.shop.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bean.list.Global_Final;
import com.bean.list.WithDrawAcctBean;
import com.hold.list.WithDrawAcctHold;
import com.utils.list.GetDataFromServer;
import com.utils.list.ParseJsonData;

import org.w3c.dom.Text;

public class WithdrawAcc extends AppCompatActivity {

    private TextView add_acct;
    private FrameLayout getwithacct;
    private GetDataFromServer getaccfromserv;
    private String cust_acct;
    private WithDrawAcctBean wdab;
    private final static int KO=111;
    private Handler withdrawacc = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 111){
                if(!TextUtils.isEmpty(getaccfromserv.getGetresult())){
                    wdab =ParseJsonData.parseObjectJson(getaccfromserv.getGetresult(),WithDrawAcctBean.class);
                    if(!TextUtils.isEmpty(wdab.getTx_account())){
                        getwithacct.setVisibility(View.VISIBLE);
                        WithDrawAcctHold wdah = new WithDrawAcctHold();
                        wdah.setData(wdab);
                        getwithacct.addView(wdah.getWithaccview());
                    }else{
                        getwithacct.setVisibility(View.GONE);
                    }

                }
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
        setContentView(R.layout.activity_withdraw_acc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        add_acct = (TextView) findViewById(R.id.zfb_add);
        getwithacct = (FrameLayout) findViewById(R.id.get_withacct);
        cust_acct = getIntent().getStringExtra("cust_acct");
        add_acct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WithdrawAcc.this, AddWithDrawAcc.class);
                intent.putExtra("cust_acct",getIntent().getStringExtra("cust_acct"));
                startActivity(intent);
            }
        });
        getAccFromServ();
    }
    public void getAccFromServ(){
        getaccfromserv = new GetDataFromServer(withdrawacc,null,KO);
        getaccfromserv.setParam(cust_acct);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getaccfromserv.getData(Global_Final.withdraw_get);
            }
        }).start();
    }
}
