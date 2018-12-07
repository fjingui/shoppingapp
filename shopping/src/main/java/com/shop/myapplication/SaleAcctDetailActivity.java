package com.shop.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bean.list.Global_Final;
import com.bean.list.WithDrawAcctBean;
import com.utils.list.GetDataFromServer;
import com.utils.list.ParseJsonData;

public class SaleAcctDetailActivity extends AppCompatActivity {

    private TextView acctmoney;
    private Button chrgdetail;
    private Button withdrawbtn;
    private Button withdrawaccont;
    private Button withdrawdetail;
    private View withdrawtraction;
    private String cust_acct;
    private GetDataFromServer getbalance;
    private GetDataFromServer getaccfromserv;
    private WithDrawAcctBean wdab;
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
        setContentView(R.layout.activity_sale_acct_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cust_acct = getIntent().getStringExtra("cust_acct");
        acctmoney = (TextView) findViewById(R.id.acct_money);
        chrgdetail = (Button) findViewById(R.id.chrgdetail);
        withdrawaccont = (Button) findViewById(R.id.withdraw_accout);
        withdrawbtn = (Button) findViewById(R.id.withdraw);
        withdrawdetail = (Button) findViewById(R.id.withdrawdetail);
        withdrawtraction = findViewById(R.id.withdrawtraction);
        if(TextUtils.equals(cust_acct,"18956662004")){
            withdrawtraction.setVisibility(View.VISIBLE);
        }
        chrgdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaleAcctDetailActivity.this, ChargeDetail.class);
                intent.putExtra("cust_acct",cust_acct);
                startActivity(intent);
            }
        });

        withdrawaccont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SaleAcctDetailActivity.this, WithdrawAcc.class);
                intent.putExtra("cust_acct",cust_acct);
                startActivity(intent);
            }
        });

        withdrawdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SaleAcctDetailActivity.this, MyWithDrawTransaction.class);
                intent.putExtra("cust_acct",cust_acct);
                startActivity(intent);
            }
        });
        getBalance();
    }
    private Handler balancehandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 111){
                acctmoney.setText("￥"+getbalance.getGetresult());
            }
            if(msg.what == 222) {
                wdab = ParseJsonData.parseObjectJson(getaccfromserv.getGetresult(),WithDrawAcctBean.class);
                withdrawbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!TextUtils.isEmpty(wdab.getTx_account())) {
                            Intent intent = new Intent(SaleAcctDetailActivity.this, IWantWithDraw.class);
                            intent.putExtra("cust_acct", cust_acct);
                            startActivity(intent);
                        }else{
                            popDialog();
                        }
                    }
                });
            }
        }
    };
    public void getBalance(){
        cust_acct = getIntent().getStringExtra("cust_acct");
        getbalance = new GetDataFromServer(balancehandle,null,111);
        getbalance.setParam(cust_acct);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getbalance.getData(Global_Final.accountbalance);
            }
        }).start();
        getaccfromserv = new GetDataFromServer(balancehandle,null,222);
        getaccfromserv.setParam(cust_acct);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getaccfromserv.getData(Global_Final.withdraw_get);
            }
        }).start();
    }
    public void popDialog(){
        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(SaleAcctDetailActivity.this);
        //    设置Title的图标
        builder.setIcon(R.drawable.ic_launcher);
        //    设置Title的内容
        builder.setTitle("弹出提示框");
        //    设置Content来显示一个信息
        builder.setMessage("请先添加提现账户!");
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
    }
}
