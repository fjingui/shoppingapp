package com.shop.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bean.list.ChrgDetail;
import com.bean.list.Global_Final;
import com.bean.list.WithDrawAcctBean;
import com.utils.list.GetDataFromServer;
import com.utils.list.HttpPostReqData;
import com.utils.list.ParseJsonData;

public class IWantWithDraw extends AppCompatActivity {

    private EditText withmoney;
    private TextView alldraw;
    private TextView zfbacc;
    private TextView zfbname;
    private Button summit;
    private String cust_acct;
    private String balance="0";
    private float remian;
    private ChrgDetail upAccountDetail = new ChrgDetail();
    private String accdetailjson;
    private GetDataFromServer getacctbalance;
    private GetDataFromServer getwithacc;
    private WithDrawAcctBean wdab;
    private static final int KO = 0111;
    private static final int KO2 = 0112;

    private Handler getbalancehdl = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0111){
                balance = getacctbalance.getGetresult();
                withmoney.setHint("账号可提现金额"+balance+"元");
                alldraw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        withmoney.setText(balance);
                    }
                });
            }
            if(msg.what == 0112){
                wdab = ParseJsonData.parseObjectJson(getwithacc.getGetresult(),WithDrawAcctBean.class);
                String a_str = wdab.getTx_account().substring(3,8);
                String a_str02 = wdab.getTx_account();
                String a_str03 = a_str02.replace(a_str,"*****");
                String str_2 = wdab.getTx_account_name().substring(0,1);
                zfbacc.setText(a_str03);
                zfbname.setText(str_2+"***");
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
        setContentView(R.layout.activity_iwant_with_draw);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        withmoney = (EditText) findViewById(R.id.withdrawmoney);
        alldraw = (TextView) findViewById(R.id.allwithdraw);
        zfbacc = (TextView) findViewById(R.id.zfb_acc);
        zfbname = (TextView) findViewById(R.id.zfb_name);
        summit = (Button) findViewById(R.id.submitbtn);
        initData();

        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(withmoney.getText())){
                    withmoney.setError("请输入提现金额");
                    View Error = withmoney;
                    Error.isShown();
                }else if (Float.parseFloat(withmoney.getText().toString()) == 0 || Float.parseFloat(balance)<Float.parseFloat(withmoney.getText().toString())){
                    withmoney.setError("提现金额不足!");
                    View Error = withmoney;
                    Error.isShown();
                }else {
                    remian = Float.parseFloat(balance) -Float.parseFloat(withmoney.getText().toString());
                    setAcctChrgDeail();
                    new HttpPostReqData().PostData(Global_Final.accdtailinsert,accdetailjson);
                    finish();
                }
            }
        });
    }
    public void initData(){
        cust_acct = getIntent().getStringExtra("cust_acct");
        getacctbalance = new GetDataFromServer(getbalancehdl,null,KO);
        getacctbalance.setParam(cust_acct);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getacctbalance.getData(Global_Final.accountbalance);
            }
        }).start();
        getwithacc = new GetDataFromServer(getbalancehdl,null,KO2);
        getwithacc.setParam(cust_acct);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getwithacc.getData(Global_Final.withdraw_get);
            }
        }).start();
    }
    public void setAcctChrgDeail(){
        upAccountDetail.setCust_acct(Long.parseLong(cust_acct));
        upAccountDetail.setPay_money(Float.parseFloat(withmoney.getText().toString()));
        upAccountDetail.setPay_type("提现");
        upAccountDetail.setBalance(remian);
        accdetailjson = ParseJsonData.parseToJson(upAccountDetail);
    }
}
