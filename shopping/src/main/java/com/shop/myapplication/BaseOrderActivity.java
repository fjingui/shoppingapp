package com.shop.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class BaseOrderActivity extends AppCompatActivity {

    private RecyclerView dpayorders;
    private List<OrderItem> dpayorderlist = new ArrayList();
    private String cust_acct;
    private GetDataFromServer getorders;
    private TextView emptydata;
    private TextView ordertitle;
    private String order_status;
    private String orderpath;
    private final String status1 = "已验收";
    private final String status2 = "待收货";
    private final String status3 = "待付款";

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
        setContentView(R.layout.activity_base_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dpayorders= (RecyclerView) findViewById(R.id.dpayorders);
        ordertitle = (TextView) findViewById(R.id.ordertitle);
        emptydata = (TextView) findViewById(R.id.ordersempty);
    }
    private Handler getdata = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==11 && getorders.getGetresult()!=null){
                dpayorderlist= ParseJsonData.parseFromJson(getorders.getGetresult(),OrderItem[].class);
                dpayorders.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
                DpayAdapter dpayAdapter = new DpayAdapter();
                dpayAdapter.setdelListner(new ItemDelListener() {
                    @Override
                    public void itemOnDel(int position) {
                        if(dpayorderlist.get(position).getOrder_status().equals(status3)) {
                            String cust_order_id = dpayorderlist.get(position).getCust_order_id();
                            delOrderFromServer(Global_Final.deleteorderpath, cust_order_id, cust_acct);
                            SystemClock.sleep(300);
                            initDataFromServer();
                        }
                    }
                });
                dpayAdapter.setpayListner(new ItemPayListener() {

                    @Override
                    public void itemPay(int position) {
                        ArrayList<OrderItem> payitems = new ArrayList();
                        payitems.add(dpayorderlist.get(position));
                        if(dpayorderlist.get(position).getOrder_status().equals(status1)
                                || dpayorderlist.get(position).getOrder_status().equals(status3)) {
                            Intent intent = new Intent(getApplicationContext(), OrderAcitvity.class);
                            intent.putParcelableArrayListExtra("orderlist", payitems);
                            startActivity(intent);
                        }
                    }
                });
                dpayorders.setAdapter(dpayAdapter);
            }else{
                emptydata.setText("暂无数据！");
                emptydata.setVisibility(View.VISIBLE);
            }
        }
    };
    public void setOrderStatus(String setstatus){
        this.order_status = setstatus;
    }
    public void setOrderPath(String setpath){
        this.orderpath = setpath;
    }
    public void setOrdertitle(String title) {
        ordertitle.setText(title);
    }
    public void initDataFromServer(){
        emptydata.setVisibility(View.GONE);
        cust_acct = getIntent().getStringExtra("cust_acct");
        getorders = new GetDataFromServer(getdata,null,11);
        getorders.setParam(cust_acct);
        getorders.setParam2(order_status);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getorders.getData(orderpath);
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
                Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
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
        private Button payorder;
        private ImageView orderimage;
        private TextView proname;
        private TextView priceamount;
        private TextView itemstatus;
        private TextView dpaymoney;
        private Button del;
        private ItemDelListener itemdellisn;
        private ItemPayListener itempaylisn;
        public DpayItemHold(View itemView,ItemDelListener itemdellisner,ItemPayListener itempaylisner) {
            super(itemView);
            this.orderid= (TextView) itemView.findViewById(R.id.myorder_orderid);
            this.payorder= (Button) itemView.findViewById(R.id.myorder_opertype);
            this.orderimage= (ImageView) itemView.findViewById(R.id.itemimage);
            this.proname= (TextView) itemView.findViewById(R.id.itemfac);
            this.priceamount= (TextView) itemView.findViewById(R.id.pricecounts);
            itemstatus = (TextView) itemView.findViewById(R.id.item_status);
            this.dpaymoney= (TextView) itemView.findViewById(R.id.myorder_totalmoney);
            this.del= (Button) itemView.findViewById(R.id.myorder_operate);
            this.itemdellisn=itemdellisner;
            this.itempaylisn=itempaylisner;
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemdellisn!=null){
                        itemdellisn.itemOnDel(getAdapterPosition());
                    }
                }
            });
            payorder.setOnClickListener(new View.OnClickListener() {
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
            View view=View.inflate(getApplicationContext(),R.layout.orderitemhold_myorder,null);
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
            if(dpayorderlist.get(position).getOrder_status().equals(status1)){
                holder.payorder.setText("再次购买");
                holder.del.setText("查看物流");
            }else if (dpayorderlist.get(position).getOrder_status().equals(status2)){
                holder.payorder.setText("确认收货");
                holder.del.setText("查看物流");
            }else {
                holder.payorder.setText("立即付款");
                holder.del.setText("取消订单");
            }
            holder.proname.setText(dpayorderlist.get(position).getFactory_name());
            holder.priceamount.setText(dpayorderlist.get(position).getProduct_price()+"*"+
                    dpayorderlist.get(position).getOrder_amount());
            holder.itemstatus.setText(dpayorderlist.get(position).getOrder_status());
            holder.dpaymoney.setText("总价："+dpayorderlist.get(position).getOrder_money());
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
