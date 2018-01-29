package com.shop.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.list.CustInfo;
import com.bean.list.Global_Final;
import com.bean.list.OrderInfo;
import com.bean.list.ProductInfo;
import com.bean.list.SaleOrder;
import com.bean.list.Seller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learn.myapplication.AmountView;
import com.utils.list.HttpPostData;
import com.utils.list.ParseJsonData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
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
    @ViewInject(R.id.order_pro_img)
    private ImageView orderProImg;
    @ViewInject(R.id.order_pro_name)
    private TextView orderProName;
    @ViewInject(R.id.order_pro_price)
    private TextView orderProPrice;
    @ViewInject(R.id.amountview02)
    private AmountView orderProNums;
    @ViewInject(R.id.order_pro_nums_label)
    private TextView orderProNumsLabel;
    @ViewInject(R.id.orderTotal)
    private TextView ordertotal;
    @ViewInject(R.id.pur_nextbtn)
    private Button payBtn;
    private Seller smpSeller;
  //  private int amValue;
 //   private SaleOrder saleOrder=new SaleOrder();
   // private String saleOrderJson;
    private CustInfo custinfo=new CustInfo();
    private String custinfojson;
    private OrderInfo orderinfo;
    private String orderinfojson;
    private String cust_acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_acitvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        x.view().inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        smpSeller = (Seller) intent.getSerializableExtra("smpseller");
        orderinfo= (OrderInfo) intent.getSerializableExtra("orderinfo");
        cust_acct=intent.getStringExtra("cust_acct");
        initViewdata();
        parseOrder();

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
                    HttpPostData.PostData(Global_Final.neworderpath,orderinfojson);
                    HttpPostData.PostData(Global_Final.newcustpath,custinfojson);
                }

            }
        });
    }
    public void initViewdata(){
       requestCustInfo();
        SystemClock.sleep(500);
        custaddr.setText(custinfo.getCust_address());
        custname.setText(custinfo.getCust_name());
        custnbr.setText(custinfo.getCust_contact_nbr());
        x.image().bind(orderProImg,smpSeller.getFactory_log(),new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.mipmap.ic_launcher).setLoadingDrawableId(R.mipmap.ic_launcher)
                .setUseMemCache(true).build());
        orderProName.setText(smpSeller.getProduct_name());
        orderProPrice.setText("￥"+smpSeller.getProduct_price()+smpSeller.getPrice_unit());
       orderProNumsLabel.setText("×"+orderinfo.getOrder_amount());
        if (smpSeller.getFactory_name().equals("拍卖珍藏")){
            orderProNums.setGoods_storage(1);
        }else {
            orderProNums.setGoods_storage(50);
        }
        orderProNums.getEtAmount().setText(orderinfo.getOrder_amount()+"");
        DecimalFormat decf= new DecimalFormat(".00");
        ordertotal.setText("共"+orderinfo.getOrder_amount()+"件商品，"+"总金额￥"
                +decf.format(smpSeller.getProduct_price()*orderinfo.getOrder_amount())+"元");
    }

    public  void parseOrder() {
        custinfo.setCust_address((String) custaddr.getText());
        custinfo.setCust_contact_nbr(custnbr.getText()+"");
        custinfo.setCust_name((String) custname.getText());
        orderinfo.setOrder_status("预订");
        orderinfo.setOrder_money(Math.round(smpSeller.getProduct_price()*orderinfo.getOrder_amount()*100)/100);
        Gson gson = new Gson();
        custinfojson=gson.toJson(custinfo);
       // orderinfojson=gson.toJson(orderinfo);
        orderinfojson= ParseJsonData.parseToJson(orderinfo);
    }
    public void parseCustJson(String json) {
        Gson focusjson= new Gson();
        custinfo=focusjson.fromJson(json,new TypeToken<CustInfo>(){}.getType());
    }
    public void requestCustInfo(){
        RequestParams reqcust = new RequestParams(Global_Final.requestcustpath);
        reqcust.addQueryStringParameter("cust_acct",cust_acct);
        x.http().get(reqcust,new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                parseCustJson(result);
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
}
