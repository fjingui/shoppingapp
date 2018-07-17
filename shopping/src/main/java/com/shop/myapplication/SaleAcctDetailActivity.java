package com.shop.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SaleAcctDetailActivity extends AppCompatActivity {

    private TextView acctmoney;
    private Button chrgdetail;
    private Button exchgdetail;

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

        acctmoney = (TextView) findViewById(R.id.acct_money);
        chrgdetail = (Button) findViewById(R.id.chrgdetail);
        exchgdetail = (Button) findViewById(R.id.exchgdetail);

        chrgdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaleAcctDetailActivity.this, ChargeDetail.class);
                startActivity(intent);
            }
        });
        exchgdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
