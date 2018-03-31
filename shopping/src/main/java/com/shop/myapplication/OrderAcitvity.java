package com.shop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.list.CustInfo;
import com.bean.list.Global_Final;
import com.bean.list.OrderInfo;
import com.bean.list.OrderItem;
import com.bean.list.Seller;
import com.learn.myapplication.AmountView;
import com.utils.list.GetDataFromServer;
import com.utils.list.HttpPostData;
import com.utils.list.OrderRecyleViewHold;
import com.utils.list.ParseJsonData;
import com.utils.list.RecyclerViewAdapter;

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
    private int proamount=0;
    private float totalmoney=0;
    private CustInfo custinfo=new CustInfo();
    private List<OrderItem> orderlist = new ArrayList();
    private RecyclerView orderitems;
    private String custinfojson;
    private OrderInfo orderinfo;
    private String orderinfojson;
    private String cust_acct;
    private GetDataFromServer datafromserv;
    private FrameLayout initframe;
    private View orderlayout;

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
        setContentView(R.layout.activity_order_acitvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initframe = (FrameLayout) findViewById(R.id.initframe);
        orderitems= (RecyclerView) findViewById(R.id.orderitemlist);
        orderlayout=findViewById(R.id.orderlayout);
        setSupportActionBar(toolbar);
        x.view().inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        initData();
        setOrderMonety();
        getDataFromServer();
        payBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (custaddr.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"收货地址不能为空",Toast.LENGTH_SHORT).show();}
                else if (custname.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"收货人不能为空",Toast.LENGTH_SHORT).show();
                }
                else if (custnbr.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"联系电话不能为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    parseOrder();
                    HttpPostData.PostData(Global_Final.neworderpath,orderinfojson);
                    HttpPostData.PostData(Global_Final.newcustpath,custinfojson);
                }

            }
        });
    }
    public void initData(){
        Intent intent = getIntent();
        cust_acct = intent.getStringExtra("cust_acct");
        orderlist=intent.getParcelableArrayListExtra("orderlist");
    }
public void setOrderMonety(){
    for(int i=0;i<orderlist.size();i++){
        proamount+=orderlist.get(i).getOrder_amount();
        totalmoney+=orderlist.get(i).getProduct_price()*orderlist.get(i).getOrder_amount();
    }
}
    private Handler getdatahandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1) {
                custinfo = (CustInfo) ParseJsonData.parseObjectJson(datafromserv.getGetresult(), CustInfo.class);
                custaddr.setText(custinfo.getCust_address());
                custname.setText(custinfo.getCust_name());
                custnbr.setText(custinfo.getCust_contact_nbr());
                orderitems.setLayoutManager(new LinearLayoutManager(OrderAcitvity.this,LinearLayoutManager.VERTICAL,false));
                orderitems.setAdapter(new OrderRecyleViewAdapter());
                DecimalFormat decf= new DecimalFormat(".00");
                ordertotal.setText("共"+proamount+"件商品，"+"总金额￥" +decf.format(totalmoney)+"元");
            }
            orderlayout.setVisibility(View.VISIBLE);
        }
    };
    public  void parseOrder() {
        custinfo.setCust_address(custaddr.getText()+"");
        custinfo.setCust_contact_nbr(custnbr.getText()+"");
        custinfo.setCust_name(custname.getText()+"");
        orderinfo.setOrder_status("预订");
        orderinfo.setOrder_money(Math.round(totalmoney*100)/100);
        custinfojson=ParseJsonData.parseToJson(custinfo);
        orderinfojson= ParseJsonData.parseToJson(orderinfo);
    }
    public void getDataFromServer(){
        datafromserv=new GetDataFromServer(getdatahandler,initframe,1);
        datafromserv.setParam(cust_acct);
        new Thread(new Runnable() {
            @Override
            public void run() {
                datafromserv.getData(Global_Final.requestcustpath);
            }
        }).start();
        orderlayout.setVisibility(View.GONE);
    }
    class OrderRecyleViewAdapter extends RecyclerView.Adapter<OrderRecyleViewHold>{

        @Override
        public OrderRecyleViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
            View orderview = View.inflate(OrderAcitvity.this, R.layout.orderitem_pay,null);
            return new OrderRecyleViewHold(orderview);
        }

        @Override
        public void onBindViewHolder(OrderRecyleViewHold holder, int position) {
            x.image().bind(holder.orderProImg,orderlist.get(position).getFactory_log(),new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setFailureDrawableId(R.mipmap.ic_launcher).setLoadingDrawableId(R.mipmap.ic_launcher)
                    .setUseMemCache(true).build());
            holder.mainproname.setText(orderlist.get(position).getFactory_name());
            holder.subProName.setText(orderlist.get(position).getProduct_name());
            holder.orderProPrice.setText("￥"+orderlist.get(position).getProduct_price());
            holder.orderProNumsLabel.setText("×"+orderlist.get(position).getOrder_amount());
        }

        @Override
        public int getItemCount() {
            return orderlist.size();
        }
    }
}
