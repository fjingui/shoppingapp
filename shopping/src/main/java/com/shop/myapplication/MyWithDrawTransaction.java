package com.shop.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.list.ChrgDetail;
import com.bean.list.Global_Final;
import com.utils.list.FocusItemClickListen;
import com.utils.list.GetDataFromServer;
import com.utils.list.ParseJsonData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyWithDrawTransaction extends AppCompatActivity {

    private RecyclerView withdrawrecy;
    private List<ChrgDetail> withdrawlist = new ArrayList();
    private WithTransAdapter withtranadp;
    private TextView detailnone;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm");
    private GetDataFromServer withtransfromserv;
    private String cust_acct;
    private Handler fromservhandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            detailnone.setVisibility(View.GONE);
            if (msg.what == 1 && !TextUtils.isEmpty(withtransfromserv.getGetresult())){
                withdrawlist = ParseJsonData.parseFromJson(withtransfromserv.getGetresult(),ChrgDetail[].class);
                withtranadp = new WithTransAdapter();
                withdrawrecy.setLayoutManager(new LinearLayoutManager(MyWithDrawTransaction.this,LinearLayoutManager.VERTICAL,false));
                withdrawrecy.setAdapter(withtranadp);
                withtranadp.setSethandlestate(new FocusItemClickListen() {
                    @Override
                    public void itemOnclick(int position) {
                        setActionState(position);
                    }
                });

            }else {
                detailnone.setVisibility(View.VISIBLE);
            }
        }
    };

    public void setActionState(int index) {
        RequestParams setreq = new RequestParams(Global_Final.withperform_update);
        setreq.addQueryStringParameter("account_detail_id",withdrawlist.get(index).getAccount_detail_id()+"");
        x.http().post(setreq, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MyWithDrawTransaction.this,result,Toast.LENGTH_LONG).show();
                finish();
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
        setContentView(R.layout.activity_my_with_draw_transaction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        withdrawrecy = (RecyclerView) findViewById(R.id.withdrawrecy);
        detailnone = (TextView) findViewById(R.id.detailnone);
        initData();
    }
    public void initData(){
        cust_acct = getIntent().getStringExtra("cust_acct");
        withtransfromserv = new GetDataFromServer(fromservhandle,null,1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                withtransfromserv.getData(Global_Final.withperform_get);
            }
        }).start();
    }
    class withtransHold extends RecyclerView.ViewHolder {
        public TextView operdate;
        public TextView opertype;
        public TextView opermoney;
        public TextView accbalance;
        private TextView action;
        private FocusItemClickListen settranstate;

        public withtransHold(View itemView,FocusItemClickListen transtate) {
            super(itemView);
            operdate = itemView.findViewById(R.id.oper_date);
            opertype =  itemView.findViewById(R.id.oper_type);
            opermoney = itemView.findViewById(R.id.oper_money);
            accbalance = itemView.findViewById(R.id.acc_balance);
            action = itemView.findViewById(R.id.tran_action);
            action.setVisibility(View.VISIBLE);
            this.settranstate = transtate;
            action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(settranstate!=null){
                        settranstate.itemOnclick(getAdapterPosition());
                    }
                }
            });
        }

    }
    class WithTransAdapter extends RecyclerView.Adapter<withtransHold>{

        private FocusItemClickListen sethandlestate;
        public void setSethandlestate(FocusItemClickListen sethandlestate) {
            this.sethandlestate = sethandlestate;
        }

        @Override
        public withtransHold onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(MyWithDrawTransaction.this).inflate(R.layout.chrg_detail_hold,parent,false);
            //View inflate = View.inflate(ChargeDetail.this, R.layout.chrg_detail_hold, null);
            return new withtransHold(inflate,sethandlestate);
        }

        @Override
        public void onBindViewHolder(withtransHold holder, int position) {
            holder.operdate.setText(withdrawlist.get(position).getPay_date().substring(0,10));
            holder.opertype.setText(withdrawlist.get(position).getPay_type());
            holder.opermoney.setText(withdrawlist.get(position).getPay_money()+"");
            holder.accbalance.setText(withdrawlist.get(position).getBalance()+"");
            holder.action.setText(withdrawlist.get(position).getAction());
        }

        @Override
        public int getItemCount() {
            return withdrawlist.size();
        }
    }
}
