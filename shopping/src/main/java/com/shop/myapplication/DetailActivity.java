package com.shop.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bean.list.Global_Final;
import com.bean.list.OrderInfo;
import com.bean.list.Seller;
import com.google.gson.Gson;
import com.hold.list.BottomHold;
import com.hold.list.DetailTextHold;
import com.hold.list.ImagesHold;
import com.hold.list.LogoHold;
import com.utils.list.HttpPostData;
import com.utils.list.ParseJsonData;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class DetailActivity extends Activity {

    @ViewInject(R.id.bottommenu)
    private FrameLayout bottomlayout;
    @ViewInject(R.id.logoshop)
    private FrameLayout logolayout;
    @ViewInject(R.id.detailtext)
    private FrameLayout detaillayout;
    @ViewInject(R.id.imageslist)
    private NestedScrollView imagelayout;
    @ViewInject(R.id.coverview)
    private View cover;
    private Seller seller;
    private DetailTextHold detailTextHold = new DetailTextHold();
    private BottomHold bottomHold=new BottomHold();
    private ImagesHold imagesHold = new ImagesHold();
    private LogoHold logo = new LogoHold();
    private OrderInfo orderInfo;
    private String carorderjson;
    private String cust_acct;
    //  private AmountView amountView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
        x.view().inject(this);
        initData();
        setViews();
        setBtnClick();
    }
    public void initData(){
        Intent intent = getIntent();
        seller = (Seller) intent.getSerializableExtra("detailseller");
        cust_acct=intent.getStringExtra("cust_acct");
        logo.setData(seller);
        detailTextHold.setData(seller);
        bottomHold.setData(seller);
        bottomHold.setCoverView(cover);
        imagesHold.setData(seller);
    }
    public void setViews(){
        logolayout.addView(logo.getLogoview());
        detaillayout.addView(detailTextHold.getDetailtextview());
        bottomlayout.bringToFront();
        bottomlayout.addView(bottomHold.getBottomtview());
        imagelayout.addView(imagesHold.getImagesView());
    }
    public void setBtnClick(){
        bottomHold.getClkNextbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cust_acct!=null) {
                    parseOrderCar();
                    if (bottomHold.getBtnflag() == 1) { //加入购物车
                        HttpPostData.PostData(Global_Final.neworderpath, carorderjson);
                    }
                    if (bottomHold.getBtnflag() == 2) { //立即购买
                        Intent intent = new Intent(BaseApplication.getContext(), OrderAcitvity.class);
                        intent.putExtra("smpseller", seller);
                        intent.putExtra("orderinfo", orderInfo);
                        intent.putExtra("cust_acct",cust_acct);
                        startActivity(intent);
                    }
                    bottomHold.getPouwindow().dismiss();
                }
                if(cust_acct==null){
                    JumpToActivity.jumpToLogin(DetailActivity.this, new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct=JumpToActivity.cust_acct;
                            Intent acctintent = new Intent();
                            acctintent.putExtra("cust_acct",cust_acct);
                            setResult(2,acctintent);
                        }
                    });
                }
            }
        });
    }
    public void parseOrderCar(){
        orderInfo =new OrderInfo();
        orderInfo.setCust_acct(cust_acct );
        orderInfo.setProduct_id(seller.getProduct_id());
        orderInfo.setOrder_status("购物车");
        orderInfo.setOrder_amount(bottomHold.getAview().getAmount());
        orderInfo.setOrder_money(Math.round(seller.getProduct_price()* orderInfo.getOrder_amount()*100)/100);
//        Gson gson = new Gson();
//        carorderjson=gson.toJson(orderInfo);
        carorderjson= ParseJsonData.parseToJson(orderInfo);
    }

}
