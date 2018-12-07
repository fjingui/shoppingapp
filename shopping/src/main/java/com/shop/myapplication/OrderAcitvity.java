package com.shop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.list.CustInfo;
import com.bean.list.Global_Final;
import com.bean.list.OrderInfo;
import com.bean.list.OrderItem;
import com.bean.list.OrderStatus;
import com.utils.list.GetDataFromServer;
import com.utils.list.HoldItemChangeListener;
import com.utils.list.HttpPostReqData;
import com.utils.list.LoginUserAcct;
import com.utils.list.OrderRecyleViewHold;
import com.utils.list.ParseJsonData;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderAcitvity extends AppCompatActivity {

    @ViewInject(R.id.order_name)
    private TextView custname;
    @ViewInject(R.id.order_addr)
    private TextView custaddr;
    @ViewInject(R.id.order_tel)
    private TextView custnbr;
    @ViewInject(R.id.orderTotal)
    private TextView ordertotal;
    @ViewInject(R.id.pur_nextbtn)
    private Button payBtn;
    @ViewInject(R.id.order_detailinfo)
    private LinearLayout orderdetailinfo;
    private int proamount = 0;
    private float totalmoney = 0;
    private CustInfo custinfo = new CustInfo();
    private OrderInfo orderinfo = new OrderInfo();
    private String orderinfojson;
    private List<OrderItem> orderlist = new ArrayList();
    private RecyclerView orderitems;
    private OrderRecyleViewAdapter orderitemsadapter;
    private String cust_acct = "";
    private GetDataFromServer datafromserv;
    private FrameLayout initframe;
    private View orderlayout;
    private DecimalFormat decf = new DecimalFormat(".0");
    private Boolean ifeditable = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_acitvity);
        initframe = (FrameLayout) findViewById(R.id.initframe);
        orderitems = (RecyclerView) findViewById(R.id.orderitemlist);
        orderlayout = findViewById(R.id.orderlayout);
        payBtn = (Button) findViewById(R.id.pur_nextbtn);
        x.view().inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();

        if(!TextUtils.equals(cust_acct,orderlist.get(0).getSaler_cust_acct())){
            orderdetailinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(OrderAcitvity.this, OrderAddrList.class);
                    startActivity(intent);

                }
            });
        }else{
            orderdetailinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(OrderAcitvity.this,"非购买者本人不能修改!",Toast.LENGTH_SHORT).show();
                }
            });
        }
        getDataFromServer();
        if(!TextUtils.equals(cust_acct,orderlist.get(0).getSaler_cust_acct())){
            payBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(OrderAcitvity.this, OrderFlowActivity.class);
                    //                    回写订单表
                    if(!orderlist.isEmpty()){
                        for (int i=0;i<orderlist.size();i++){
                            if(TextUtils.equals(orderlist.get(i).getOrder_status(),OrderStatus.STATUS_CAR)){
                                HttpPostReqData setcarstaus = new HttpPostReqData();
                                setcarstaus.setParam3(orderlist.get(i).getCust_order_id());
                                setcarstaus.setParam5(OrderStatus.STATUS_WRITE);
                                setcarstaus.postData02(Global_Final.updateorderstatus);
                            }else{
                                orderlist.get(i).setOrder_status(OrderStatus.STATUS_WRITE);
                                setOrderInfo(orderlist.get(i),OrderStatus.STATUS_WRITE);
                                new HttpPostReqData().PostData(Global_Final.neworderpath, orderinfojson);
                            }
                        }
                    }
                    ArrayList<OrderItem> orderlist2= new ArrayList<OrderItem>(orderlist);
                    intent.putParcelableArrayListExtra("orderlist", orderlist2);
                    startActivity(intent);
                }
            });
        }

    }

    public void initData() {
        Intent intent = getIntent();
        cust_acct = LoginUserAcct.user.getCust_acct();
        orderlist = intent.getParcelableArrayListExtra("orderlist");
        if(TextUtils.equals(cust_acct,orderlist.get(0).getSaler_cust_acct())){
            ifeditable =true;
        }
    }
    private Handler getdatahandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1 ) {
                custinfo = ParseJsonData.parseObjectJson(datafromserv.getGetresult(), CustInfo.class);
                if (custinfo != null) {
                    custaddr.setText(custinfo.getCust_address());
                    custname.setText(custinfo.getCust_name());
                    custnbr.setText(custinfo.getCust_contact_nbr());
                    orderitems.setLayoutManager(new LinearLayoutManager(OrderAcitvity.this, LinearLayoutManager.VERTICAL, false));
                    orderitemsadapter = new OrderRecyleViewAdapter();
                    orderitems.setAdapter(orderitemsadapter);
                    orderitemsadapter.setIf_edit(ifeditable);
                    proamount = 0;
                    totalmoney = 0;
                    orderitemsadapter.notifyDataSetChanged();
                    orderitemsadapter.setHtcl(new HoldItemChangeListener() {
                        @Override
                        public void textChange(int i) {
                            proamount = 0;
                            totalmoney = 0;
                            orderitemsadapter.notifyDataSetChanged();
                        }
                    });
                }
                orderlayout.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromServer();
        initData();
    }
    public void getDataFromServer() {
        datafromserv = new GetDataFromServer(getdatahandler, initframe, 1);
        datafromserv.setParam(cust_acct);
        datafromserv.getData(Global_Final.requestcustpath);
        orderlayout.setVisibility(View.GONE);
    }
    public void setOrderInfo(OrderItem order, String ordertype) {
        orderinfo.setCust_acct(cust_acct);
        orderinfo.setCust_order_id(order.getCust_order_id());
        orderinfo.setOrder_amount(order.getOrder_amount());
        orderinfo.setProduct_id(order.getProduct_id());
        orderinfo.setOrder_status(ordertype);
        orderinfo.setOrder_money(order.getProduct_price() * order.getOrder_amount());
        orderinfojson = ParseJsonData.parseToJson(orderinfo);
    }

    class OrderRecyleViewAdapter extends RecyclerView.Adapter<OrderRecyleViewHold> {

        private float itemmoney=0;
        private float itemdiscount=0;
        private HoldItemChangeListener htcl;
        private Boolean if_edit;

        public void setIf_edit(Boolean if_edit) {
            this.if_edit = if_edit;
        }

        public void setHtcl(HoldItemChangeListener htcl) {
            this.htcl = htcl;
        }

        @Override
        public OrderRecyleViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
            View orderview = View.inflate(OrderAcitvity.this, R.layout.orderitem_pay, null);
            return new OrderRecyleViewHold(orderview,htcl,if_edit);
        }

        @Override
        public void onBindViewHolder(OrderRecyleViewHold holder, int position) {
            x.image().bind(holder.orderProImg, orderlist.get(position).getFactory_log(), new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setFailureDrawableId(R.mipmap.ic_launcher).setLoadingDrawableId(R.mipmap.ic_launcher)
                    .setUseMemCache(true).build());
            holder.mainproname.setText(orderlist.get(position).getProduct_name());
            holder.prouser.setText(orderlist.get(position).getFactory_name());
            holder.proaddress.setText(orderlist.get(position).getProduct_unit());
            holder.orderProPrice.setText("￥" + orderlist.get(position).getProduct_price());
            holder.orderProNumsLabel.setText("×" + orderlist.get(position).getOrder_amount());
            itemmoney = orderlist.get(position).getProduct_price() * orderlist.get(position).getOrder_amount();
            itemdiscount = Float.valueOf(holder.orderyunfee.getText().toString())-Float.valueOf(holder.itemkold.getText().toString());
            float itemsum = itemmoney+itemdiscount;
            holder.itemmoneysum.setText(decf.format(itemsum) +"");
            totalmoney += itemsum;
            proamount += orderlist.get(position).getOrder_amount();
            ordertotal.setText("共" + proamount + "件商品，" + "总金额￥" + decf.format(totalmoney) + "元");

        }

        @Override
        public int getItemCount() {
            return orderlist.size();
        }
    }
}
