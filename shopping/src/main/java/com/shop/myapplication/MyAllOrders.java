package com.shop.myapplication;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.list.Global_Final;
import com.bean.list.OrderItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learn.myapplication.AmountView;
import com.utils.list.ParseJsonData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MyAllOrders extends AppCompatActivity {

    private RecyclerView allordersrecycl;
    private List<OrderItem> allorderlist=new ArrayList<>();
    public AllOrderAdapter allorderdata= new AllOrderAdapter();
    private String cust_acct;
    private TextView emptydata;

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
        setContentView(R.layout.activity_my_all_orders);
        Toolbar myallorderbar = (Toolbar) findViewById(R.id.myorderbar);
        setSupportActionBar(myallorderbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        emptydata= (TextView) findViewById(R.id.ordersempty);
        allordersrecycl= (RecyclerView) findViewById(R.id.allorders);
        allordersrecycl.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        cust_acct=getIntent().getStringExtra("cust_acct");
        initData();
        allordersrecycl.setAdapter(allorderdata);
    }
    public void initData(){
        emptydata.setVisibility(View.GONE);
        new Thread(){
            @Override
            public void run() {
                getDataFromServer();
                SystemClock.sleep(500);
                if(allorderlist!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            allorderdata.notifyDataSetChanged();
                        }
                    });
                }
            }
        }.start();
    }
    public void parseOrderData(String json) {
        Gson gson = new Gson();
        allorderlist = gson.fromJson(json, new TypeToken<List<OrderItem>>() {
        }.getType());
    }
    public void getDataFromServer(){
        RequestParams reparam = new RequestParams(Global_Final.requestorderpath);
        reparam.addQueryStringParameter("cust_acct",cust_acct);
        reparam.addQueryStringParameter("orderstatus","已验收");
        x.http().post(reparam, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseOrderData(result);
                if(allorderlist.isEmpty()){
                    emptydata.setText("暂无数据！");
                    emptydata.setVisibility(View.VISIBLE);
                }

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
    public class  AllOrderHold extends  RecyclerView.ViewHolder{
        ImageView itemimage;
        TextView itemname;
        TextView itemfac;
        TextView itempri;
        TextView ordernums;

        public AllOrderHold(View itemView) {
            super(itemView);
            this.itemimage = (ImageView) itemView.findViewById(R.id.itemimage);
            this.itemname = (TextView) itemView.findViewById(R.id.itemname);
            this.itemfac = (TextView) itemView.findViewById(R.id.itemfac);
            this.itempri = (TextView) itemView.findViewById(R.id.itemprice);
            this.ordernums = (TextView) itemView.findViewById(R.id.itemamount);
        }
    }
    public class AllOrderAdapter extends RecyclerView.Adapter<AllOrderHold>{
        public int getItemCount() {
            return allorderlist.size();
        }

        @Override
        public AllOrderHold onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.allorderitem_view, null);
            return new AllOrderHold(view);
        }
        @Override
        public void onBindViewHolder(AllOrderHold holder, int position) {
            x.image().bind(holder.itemimage, allorderlist.get(position).getFactory_log(), new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setFailureDrawableId(R.mipmap.ic_launcher)
                    .setLoadingDrawableId(R.mipmap.ic_launcher)
                    .setUseMemCache(true).build());
            holder.itemname.setText(allorderlist.get(position).getProduct_name());
            holder.itemfac.setText(allorderlist.get(position).getFactory_name());
            holder.itempri.setText("￥"+allorderlist.get(position).getProduct_price());
            holder.ordernums.setText("×"+allorderlist.get(position).getOrder_amount());

        }
    }
}
