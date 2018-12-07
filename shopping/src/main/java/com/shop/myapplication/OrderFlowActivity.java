package com.shop.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.base.bj.paysdk.domain.TrPayResult;
import com.base.bj.paysdk.listener.PayResultListener;
import com.base.bj.paysdk.utils.TrPay;
import com.bean.list.CustInfo;
import com.bean.list.Global_Final;
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

public class OrderFlowActivity extends AppCompatActivity implements View.OnClickListener{

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
    @ViewInject(R.id.statusgroup)
    private RadioGroup statusgrop;
    @ViewInject(R.id.statuswrite)
    private RadioButton statuswrite;
    @ViewInject(R.id.statuspay)
    private RadioButton statuspay;
    @ViewInject(R.id.statussend)
    private RadioButton statussend;
    @ViewInject(R.id.statusaccept)
    private RadioButton statusaccept;
    @ViewInject(R.id.tradehint)
    private TextView tradehint;
    private LinearLayout btnView;
    private ImageView updateaddrico;
    private int proamount = 0;
    private float totalmoney = 0;
    private CustInfo custinfo = new CustInfo();
    private List<OrderItem> orderlist = new ArrayList();
    private RecyclerView orderitems;
    private OrderFlowActivity.OrderRecyleViewAdapter orderitemsadapter;
    private String custinfojson;
    private String cust_acct = "";
    private GetDataFromServer datafromserv;
    private String paycustorderid;
    private DecimalFormat decf = new DecimalFormat(".0");
    private Boolean ifeditable = false;
    private TextView curflowtext;
    private final static String channel = "360";//应用商店渠道名(如：360，小米、华为)
    private final static String appkey = "55388a820c3644aa8eaef76f9f89ecdb";

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
        setContentView(R.layout.activity_order_flow);
        orderitems = (RecyclerView) findViewById(R.id.orderitemlist);
        payBtn = (Button) findViewById(R.id.pur_nextbtn);
        curflowtext = (TextView) findViewById(R.id.curflowtext);
        btnView = (LinearLayout) findViewById(R.id.btncertify);
        updateaddrico = (ImageView) findViewById(R.id.updateaddrico);
        updateaddrico.setVisibility(View.GONE);
        x.view().inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();

        getDataFromServer();

        TrPay.getInstance(this).initPaySdk(appkey, channel);
    }

    public void initData() {
        Intent intent = getIntent();
        cust_acct = LoginUserAcct.user.getCust_acct();
        orderlist = intent.getParcelableArrayListExtra("orderlist");
        String status = orderlist.get(0).getOrder_status();
        changeByStatus(status);
        if(TextUtils.equals(cust_acct,orderlist.get(0).getSaler_cust_acct())){
            ifeditable =true;
        }
        paycustorderid = orderlist.get(0).getCust_order_id();
    }
    public void changeByStatus(String stas){
        switch (stas){
            case OrderStatus.STATUS_WRITE :
                curflowtext.setText("下单成功。需等待卖家修改运费、优惠后，"+"\n"+"方可支付。可在我的订单查看详情。");
                statuswrite.setChecked(true);
                statuswrite.setTextColor(Color.WHITE);
                tradehint.setText("下单成功！等待卖家修改运费。");
                if(TextUtils.equals(cust_acct,orderlist.get(0).getSaler_cust_acct())){
                    payBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setExpDiscChrg();
                            setOrderStatus(OrderStatus.STATUS_PAY);
                            SystemClock.sleep(3);
                            finish();
                        }
                    });
                }else {
                    ifeditable =false;
                    btnView.setVisibility(View.GONE);
                }
                break;
            case OrderStatus.STATUS_PAY :
                curflowtext.setText("卖家已修改运费、优惠，确认无误后请支付。");
                statuspay.setChecked(true);
                statuspay.setTextColor(Color.WHITE);
                payBtn.setText("待支付");
                setRadioDraw(statuswrite);
                if(!TextUtils.equals(cust_acct,orderlist.get(0).getSaler_cust_acct())){
                    payBtn.setOnClickListener(OrderFlowActivity.this);
                }else {
                    ifeditable =false;
                    btnView.setVisibility(View.GONE);
                }
                break;
            case OrderStatus.STATUS_SEND :
                curflowtext.setText("已支付，请等待卖家发货。");
                statussend.setChecked(true);
                statussend.setTextColor(Color.WHITE);
                setRadioDraw(statuswrite,statuspay);
                tradehint.setText("请等待卖家发货!");
                if(TextUtils.equals(cust_acct,orderlist.get(0).getSaler_cust_acct())){
                    ifeditable =false;
                    payBtn.setText("确认发货");
                    payBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setOrderStatus(OrderStatus.STATUS_ACCEPT);
                            SystemClock.sleep(3);
                            finish();
                        }
                    });
                }else {
                    btnView.setVisibility(View.GONE);
                }
                break;
            case OrderStatus.STATUS_ACCEPT :
                curflowtext.setText("卖家已发货，确认收货后，将直接打款给卖家，请务必谨慎确认。");
                statusaccept.setChecked(true);
                statusaccept.setTextColor(Color.WHITE);
                ordertotal.setVisibility(View.GONE);
                setRadioDraw(statuswrite,statuspay,statussend);
                tradehint.setText("发货七天后，默认已收到货。");
                if(TextUtils.equals(cust_acct,orderlist.get(0).getSaler_cust_acct())){
                    ifeditable =false;
                    btnView.setVisibility(View.GONE);
                }else {
                    payBtn.setText("确认收货");
                    payBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setExpDiscChrg();
                            setOrderStatus(OrderStatus.STATUS_OVER);
                            SystemClock.sleep(3);
                            finish();
                        }
                    });

                }
                break;
            case OrderStatus.STATUS_OVER :
                curflowtext.setText("订单已完成。");
                setRadioDraw(statuswrite,statuspay,statussend,statusaccept);
                ifeditable =false;
                statusgrop.setVisibility(View.GONE);
                ordertotal.setVisibility(View.GONE);
                payBtn.setText("我要评价");
                break;
        }
    }

    public void setRadioDraw(RadioButton... rb){
        for (int i=0;i<rb.length;i++){
            Drawable circle = getResources().getDrawable(R.drawable.circlesmall,null);
            circle.setBounds(0,0,circle.getMinimumWidth(),circle.getMinimumHeight());
            rb[i].setCompoundDrawables(null,circle,null,null);
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
                    orderitems.setLayoutManager(new LinearLayoutManager(OrderFlowActivity.this, LinearLayoutManager.VERTICAL, false));
                    orderitemsadapter = new OrderFlowActivity.OrderRecyleViewAdapter();
                    orderitems.setAdapter(orderitemsadapter);
                    orderitemsadapter.setIf_edit(ifeditable);
                    orderitemsadapter.setHtcl(new HoldItemChangeListener() {
                        @Override
                        public void textChange(int i) {
                            proamount = 0;
                            totalmoney = 0;
                            orderitemsadapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }
    };
    public void setExpDiscChrg(){
            View child = orderitems.getLayoutManager().getChildAt(0);
            EditText child_child01 = child.findViewById(R.id.order_yunfee_value);
            EditText child_child02 = child.findViewById(R.id.itemkold);
            orderlist.get(0).setExpress_chrg(Float.valueOf(child_child01.getText().toString()));
            orderlist.get(0).setDiscount_chrg(Float.valueOf(child_child02.getText().toString()));
            orderlist.get(0).setOrder_money(totalmoney);
            HttpPostReqData setExpDis = new HttpPostReqData();
            setExpDis.setParam3(orderlist.get(0).getCust_order_id());
            setExpDis.setParam1(orderlist.get(0).getExpress_chrg());
            setExpDis.setParam2(orderlist.get(0).getDiscount_chrg());
            setExpDis.setParam4(totalmoney);
            setExpDis.postData02(Global_Final.updateorderchrg);
    }
    public void setOrderStatus(String staus){
        HttpPostReqData setorderstaus = new HttpPostReqData();
        for (int i=0;i<orderlist.size();i++){
            setorderstaus.setParam3(orderlist.get(i).getCust_order_id());
            setorderstaus.setParam5(staus);
            setorderstaus.postData02(Global_Final.updateorderstatus);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromServer();
    }

    public void resetCustInfo() {
        custinfo.setCust_address(custaddr.getText() + "");
        custinfo.setCust_contact_nbr(custnbr.getText() + "");
        custinfo.setCust_name(custname.getText() + "");
        custinfojson = ParseJsonData.parseToJson(custinfo);
    }

    public void getDataFromServer() {
        datafromserv = new GetDataFromServer(getdatahandler, null, 1);
        datafromserv.setParam(cust_acct);
        new Thread(new Runnable() {
            @Override
            public void run() {
                datafromserv.getData(Global_Final.requestcustpath);
            }
        }).start();
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
            Toast.makeText(OrderFlowActivity.this, "金额不能小于1分！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (custaddr.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "收货地址不能为空", Toast.LENGTH_SHORT).show();
        } else if (custname.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "收货人不能为空", Toast.LENGTH_SHORT).show();
        } else if (custnbr.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "联系电话不能为空", Toast.LENGTH_SHORT).show();
        } else if(totalmoney <= 0){
            payBtn.setError("总金额不能小于0");
            View error = payBtn;
            error.requestFocus();
        } else {
            resetCustInfo();
            new HttpPostReqData().PostData(Global_Final.newcustpath, custinfojson);
            TrPay.getInstance(OrderFlowActivity.this).callPay(tradename, outtradeno, amount, backparams, notifyurl, userid, new PayResultListener() {
                @Override
                public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                    if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                        TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                        for (int i = 0; i < orderlist.size(); i++) {
                            setOrderStatus(OrderStatus.STATUS_SEND);
                        }
                        Toast.makeText(OrderFlowActivity.this, "支付成功，等待商家发货", Toast.LENGTH_LONG).show();
                        //支付成功逻辑处理
                    } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                        for (int i = 0; i < orderlist.size(); i++) {
                            Toast.makeText(OrderFlowActivity.this, "支付失败，请稍后重新尝试", Toast.LENGTH_LONG).show();
                            //支付失败逻辑处理
                        }
                    }
                }
            });

        }
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
            View orderview = View.inflate(OrderFlowActivity.this, R.layout.orderitem_pay, null);
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
