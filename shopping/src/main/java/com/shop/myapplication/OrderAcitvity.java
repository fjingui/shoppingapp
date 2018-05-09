package com.shop.myapplication;

import android.app.Activity;
import android.content.Context;
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

import com.base.bj.paysdk.domain.TrPayResult;
import com.base.bj.paysdk.listener.PayResultListener;
import com.base.bj.paysdk.utils.TrPay;
import com.bean.list.CustInfo;
import com.bean.list.Global_Final;
import com.bean.list.OrderInfo;
import com.bean.list.OrderItem;
import com.utils.list.GetDataFromServer;
import com.utils.list.HttpPostData;
import com.utils.list.OrderRecyleViewHold;
import com.utils.list.ParseJsonData;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderAcitvity extends AppCompatActivity implements View.OnClickListener{

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
    private OrderInfo orderinfo=new OrderInfo();
    private String orderinfojson;
    private String cust_acct="";
    private GetDataFromServer datafromserv;
    private FrameLayout initframe;
    private View orderlayout;
    private String paycustorderid;
    private final static String channel = "360";//应用商店渠道名(如：360，小米、华为)
    private final static String appkey = "55388a820c3644aa8eaef76f9f89ecdb";

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
        payBtn= (Button) findViewById(R.id.pur_nextbtn);
        setSupportActionBar(toolbar);
        x.view().inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();
        setOrderMonety();
        getDataFromServer();
        payBtn.setOnClickListener(OrderAcitvity.this);
        TrPay.getInstance(this).initPaySdk(appkey,channel);
    }
    public void initData(){
        Intent intent = getIntent();
        cust_acct = intent.getStringExtra("cust_acct");
        orderlist=intent.getParcelableArrayListExtra("orderlist");
        paycustorderid=orderlist.get(0).getCust_order_id();
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
    public  void resetCustInfo() {
        custinfo.setCust_address(custaddr.getText()+"");
        custinfo.setCust_contact_nbr(custnbr.getText()+"");
        custinfo.setCust_name(custname.getText()+"");
        custinfojson=ParseJsonData.parseToJson(custinfo);
    }
    public void setOrderInfo(OrderItem order,String ordertype){
        orderinfo.setCust_acct(cust_acct);
        orderinfo.setCust_order_id(order.getCust_order_id());
        orderinfo.setOrder_amount(order.getOrder_amount());
        orderinfo.setProduct_id(order.getProduct_id());
        orderinfo.setOrder_status(ordertype);
        orderinfo.setOrder_money(Math.round(totalmoney*100)/100);
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

    @Override
    public void onClick(View v) {
//发起支付所需参数
        String userid = cust_acct;//商户系统用户ID(如：trpay@52yszd.com，商户系统内唯一)
        String outtradeno = String.valueOf(paycustorderid);//商户系统订单号(为便于演示，此处利用UUID生成模拟订单号，商户系统内唯一)
        String backparams = "";//商户系统回调参数
        String notifyurl = "http://101.200.13.92/notify/";//商户系统回调地址
        String tradename = orderlist.get(0).getFactory_name();//商品名称
        if (proamount > 1) {
            tradename = tradename + "等多件";
        }
        Long amount = Long.valueOf(Math.round(totalmoney * 100) / 1 + "");//商品价格（单位：分。如1.5元传150）
        if (amount < 1) {
            Toast.makeText(OrderAcitvity.this, "金额不能小于1分！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (custaddr.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "收货地址不能为空", Toast.LENGTH_SHORT).show();
        } else if (custname.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "收货人不能为空", Toast.LENGTH_SHORT).show();
        } else if (custnbr.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "联系电话不能为空", Toast.LENGTH_SHORT).show();
        } else {
            resetCustInfo();
            HttpPostData.PostData(Global_Final.newcustpath, custinfojson);
            TrPay.getInstance(OrderAcitvity.this).callPay(tradename, outtradeno, amount, backparams, notifyurl, userid, new PayResultListener() {
                @Override
                public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                    if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                        TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                        for (int i = 0; i < orderlist.size(); i++) {
                            String str1 = "已付款";
                            setOrderInfo(orderlist.get(i), str1);
                            HttpPostData.PostData(Global_Final.neworderpath, orderinfojson);
                        }
                        Toast.makeText(OrderAcitvity.this, "支付成功，等待商家发货", Toast.LENGTH_LONG).show();
                        //支付成功逻辑处理
                    } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                        for (int i = 0; i < orderlist.size(); i++) {
                            String str2 = "待付款";
                            setOrderInfo(orderlist.get(i), str2);
                            HttpPostData.PostData(Global_Final.neworderpath, orderinfojson);

                            Toast.makeText(OrderAcitvity.this, "支付失败，请稍后重新尝试", Toast.LENGTH_LONG).show();
                            //支付失败逻辑处理
                        }
                    }
                }
            });

        }
    }
            class OrderRecyleViewAdapter extends RecyclerView.Adapter<OrderRecyleViewHold> {

                @Override
                public OrderRecyleViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
                    View orderview = View.inflate(OrderAcitvity.this, R.layout.orderitem_pay, null);
                    return new OrderRecyleViewHold(orderview);
                }

                @Override
                public void onBindViewHolder(OrderRecyleViewHold holder, int position) {
                    x.image().bind(holder.orderProImg, orderlist.get(position).getFactory_log(), new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                            .setFailureDrawableId(R.mipmap.ic_launcher).setLoadingDrawableId(R.mipmap.ic_launcher)
                            .setUseMemCache(true).build());
                    holder.mainproname.setText(orderlist.get(position).getFactory_name());
                    holder.subProName.setText(orderlist.get(position).getProduct_name());
                    holder.orderProPrice.setText("￥" + orderlist.get(position).getProduct_price());
                    holder.orderProNumsLabel.setText("×" + orderlist.get(position).getOrder_amount());
                }

                @Override
                public int getItemCount() {
                    return orderlist.size();
                }
            }
        }
