package com.shop.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bean.list.ChrgDetail;
import com.bean.list.Global_Final;
import com.utils.list.GetDataFromServer;
import com.utils.list.ParseJsonData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChargeDetail extends AppCompatActivity {

    private RecyclerView accoperdetail;
    private List<ChrgDetail> detaillist = new ArrayList();
    private ChrgDetailAdapter chrgdtladp;
    private TextView detailnone;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
    private GetDataFromServer detailfromserv;
    private String cust_acct;
    private Handler inithandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            detailnone.setVisibility(View.GONE);
            if (msg.what == 1 && detailfromserv.getGetresult()!=null){
                chrgdtladp = new ChrgDetailAdapter();
                detaillist = ParseJsonData.parseFromJson(detailfromserv.getGetresult(),ChrgDetail[].class);
                accoperdetail.setLayoutManager(new LinearLayoutManager(ChargeDetail.this,LinearLayoutManager.VERTICAL,false));
                accoperdetail.setAdapter(chrgdtladp);
            }else {
                detailnone.setVisibility(View.VISIBLE);
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
        setContentView(R.layout.activity_charge_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        accoperdetail = (RecyclerView) findViewById(R.id.detaillist);
        detailnone = (TextView) findViewById(R.id.detailnone);
        initData();
    }
    public void initData(){
        cust_acct = getIntent().getStringExtra("cust_acct");
        detailfromserv = new GetDataFromServer(inithandle,null,1);
        detailfromserv.setParam(cust_acct);
        new Thread(new Runnable() {
            @Override
            public void run() {
                detailfromserv.getData(Global_Final.accountdetail);
            }
        }).start();
    }
    class ChrgDetailHold extends RecyclerView.ViewHolder {
        public TextView operdate;
        public TextView opertype;
        public TextView opermoney;
        public TextView accbalance;

        public ChrgDetailHold(View itemView) {
            super(itemView);
            operdate = (TextView) itemView.findViewById(R.id.oper_date);
            opertype = (TextView) itemView.findViewById(R.id.oper_type);
            opermoney = (TextView) itemView.findViewById(R.id.oper_money);
            accbalance = (TextView) itemView.findViewById(R.id.acc_balance);

        }
    }
    class ChrgDetailAdapter extends RecyclerView.Adapter<ChrgDetailHold>{

        @Override
        public ChrgDetailHold onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(ChargeDetail.this).inflate(R.layout.chrg_detail_hold,parent,false);
            //View inflate = View.inflate(ChargeDetail.this, R.layout.chrg_detail_hold, null);
            return new ChrgDetailHold(inflate);
        }

        @Override
        public void onBindViewHolder(ChrgDetailHold holder, int position) {
            holder.operdate.setText(detaillist.get(position).getPay_date().substring(0,10));
            holder.opertype.setText(detaillist.get(position).getPay_type());
            holder.opermoney.setText(detaillist.get(position).getPay_money()+"");
            holder.accbalance.setText(detaillist.get(position).getBalance()+"");
        }

        @Override
        public int getItemCount() {
            return detaillist.size();
        }
    }
}
