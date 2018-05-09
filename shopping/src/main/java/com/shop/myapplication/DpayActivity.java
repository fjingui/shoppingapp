package com.shop.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.list.Global_Final;
import com.bean.list.OrderItem;
import com.utils.list.GetDataFromServer;
import com.utils.list.ParseJsonData;
import com.utils.list.RecyclerViewAdapter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class DpayActivity extends AppCompatActivity {

    private RecyclerView dpayorders;
    private List<OrderItem> dpayorderlist = new ArrayList();
    private String cust_acct;
    private GetDataFromServer gerdpayorders;

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
        setContentView(R.layout.activity_dpay);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dpayorders= (RecyclerView) findViewById(R.id.dpayorders);
        initData();
        initDataFromServer();
    }
    private Handler getdata = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==11){
                dpayorderlist= ParseJsonData.parseFromJson(gerdpayorders.getGetresult(),OrderItem[].class);
                dpayorders.setLayoutManager(new LinearLayoutManager(DpayActivity.this, LinearLayoutManager.VERTICAL,false));
                DpayAdapter dpayAdapter = new DpayAdapter();
                dpayAdapter.setdelListner(new ItemDelListener() {
                    @Override
                    public void itemOnDel(int position) {
                        String cust_order_id = dpayorderlist.get(position).getCust_order_id();
                        delOrderFromServer(Global_Final.deleteorderpath, cust_order_id,cust_acct);
                        SystemClock.sleep(300);
                        initDataFromServer();
                    }
                });
                dpayAdapter.setpayListner(new ItemPayListener() {
                    @Override
                    public void itemPay(int position) {
                        ArrayList<OrderItem> payitems = new ArrayList();
                        payitems.add(dpayorderlist.get(position));
                        Intent intent = new Intent(DpayActivity.this, OrderAcitvity.class);
                        intent.putParcelableArrayListExtra("orderlist",payitems);
                        startActivity(intent);

                    }
                });
                dpayorders.setAdapter(dpayAdapter);
            }
        }
    };
    public void initData(){
        cust_acct = getIntent().getStringExtra("cust_acct");
    }
    public void initDataFromServer(){
        gerdpayorders = new GetDataFromServer(getdata,null,11);
        gerdpayorders.setParam(cust_acct);
        gerdpayorders.setParam2("待付款");
        new Thread(new Runnable() {
            @Override
            public void run() {
                gerdpayorders.getData(Global_Final.requestorderpath);
            }
        }).start();
    }
    public void delOrderFromServer(String delPath, String orderid,String cust_acct){
        RequestParams delorderparm=new RequestParams(delPath);
        delorderparm.addQueryStringParameter("orderid", orderid);
        delorderparm.addQueryStringParameter("cust_acct",cust_acct);
        x.http().post(delorderparm,new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                Toast.makeText(DpayActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onFinished() {
                //   Toast.makeText(getContext(),"删除完成",Toast.LENGTH_SHORT).show();
            }
        });
    }
    class DpayItemHold extends RecyclerView.ViewHolder{
        private TextView orderid;
        private Button deleorder;
        private ImageView orderimage;
        private TextView proname;
        private TextView priceamount;
        private TextView dpaymoney;
        private Button pay;
        private ItemDelListener itemdellisn;
        private ItemPayListener itempaylisn;
        public DpayItemHold(View itemView,ItemDelListener itemdellisner,ItemPayListener itempaylisner) {
            super(itemView);
            this.orderid= (TextView) itemView.findViewById(R.id.dpay_orderid);
            this.deleorder= (Button) itemView.findViewById(R.id.dpay_deleorder);
            this.orderimage= (ImageView) itemView.findViewById(R.id.itemimage);
            this.proname= (TextView) itemView.findViewById(R.id.itemfac);
            this.priceamount= (TextView) itemView.findViewById(R.id.pricecounts);
            this.dpaymoney= (TextView) itemView.findViewById(R.id.dpay_money);
            this.pay= (Button) itemView.findViewById(R.id.dpay_pay);
            this.itemdellisn=itemdellisner;
            this.itempaylisn=itempaylisner;
            deleorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemdellisn!=null){
                        itemdellisn.itemOnDel(getAdapterPosition());
                    }
                }
            });
            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itempaylisn!=null){
                        itempaylisn.itemPay(getAdapterPosition());
                    }
                }
            });
        }
    }
    class DpayAdapter extends RecyclerView.Adapter<DpayItemHold>{

        private ItemDelListener itemdellisn;
        private ItemPayListener itempaylisn;

        public void setdelListner(ItemDelListener itemdellisn){
            this.itemdellisn=itemdellisn;
        }
        public void setpayListner(ItemPayListener itempaylisn){
            this.itempaylisn=itempaylisn;
        }
        @Override
        public DpayItemHold onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=View.inflate(getApplicationContext(),R.layout.orderiterm_dpay,null);
            return new DpayItemHold(view,itemdellisn,itempaylisn);
        }

        @Override
        public void onBindViewHolder(DpayItemHold holder, int position) {
            holder.orderid.setText("订单号："+dpayorderlist.get(position).getCust_order_id());
            x.image().bind(holder.orderimage,dpayorderlist.get(position).getFactory_log(),new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setFailureDrawableId(R.mipmap.ic_launcher)
                    .setLoadingDrawableId(R.mipmap.ic_launcher)
                    .setUseMemCache(true).build());
            holder.proname.setText(dpayorderlist.get(position).getFactory_name());
            holder.priceamount.setText(dpayorderlist.get(position).getProduct_price()+"*"+
            dpayorderlist.get(position).getOrder_amount());
            holder.dpaymoney.setText("应付："+
                    Math.round(dpayorderlist.get(position).getProduct_price()*dpayorderlist.get(position).getOrder_amount()*10)/10);
        }

        @Override
        public int getItemCount() {
            return dpayorderlist.size();
        }
    }
    public interface ItemDelListener{
        void itemOnDel(int position);
    }
    public interface ItemPayListener{
        void itemPay(int position);
    }
}
