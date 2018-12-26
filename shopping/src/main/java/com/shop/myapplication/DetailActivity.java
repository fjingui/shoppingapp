package com.shop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bean.list.Global_Final;
import com.bean.list.OrderInfo;
import com.bean.list.OrderItem;
import com.bean.list.Seller;
import com.easemob.chat.EMChat;
import com.easemob.easeui.EaseConstant;
import com.hold.list.ApproveButton;
import com.hold.list.BottomHold;
import com.hold.list.DetailTextHold;
import com.hold.list.ImagesHold;
import com.hold.list.LogoHold;
import com.utils.list.GeneOrderId;
import com.utils.list.HttpPostReqData;
import com.utils.list.LoginUserAcct;
import com.utils.list.ParseJsonData;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

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
    private DetailTextHold detailTextHold;
    private BottomHold bottomHold;
    private ImagesHold imagesHold ;
    private LogoHold logo ;
    private OrderInfo orderInfo;
    private String carorderjson;
    private String cust_acct;
    private List<OrderItem> orderlist = new ArrayList();
    private OrderItem orderitem;
    private String shoptype="";
    private String getorderid;
    private Button mychatbtn;
    private Button appvchatbtn;
    private String srcflag;
    private ApproveButton appbtn;
    private HttpPostReqData hprd ;
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
        setContentView(R.layout.activity_detail);
        Toolbar detailtoolbar = (Toolbar) findViewById(R.id.detailtoolbar);
        setSupportActionBar(detailtoolbar);
        x.view().inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
        setViews();
    }
    public void initData(){
        Intent intent = getIntent();
        seller = (Seller) intent.getSerializableExtra("detailseller");
        srcflag = intent.getStringExtra("source");
        System.out.println("来自于....."+srcflag);
        cust_acct= LoginUserAcct.user.getCust_acct();
        detailTextHold = new DetailTextHold();
        bottomHold=new BottomHold();
        imagesHold = new ImagesHold();
        logo = new LogoHold();
        mychatbtn = bottomHold.getChatbtn();
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
        if(TextUtils.equals(srcflag,"sale")){
            bottomlayout.removeAllViews();
            bottomlayout.addView(bottomHold.getBottomtview());
            setBtnClick();
        }else{
            bottomlayout.removeAllViews();
            ApproveButton appbtn = new ApproveButton();
            Button appvchatbtn = appbtn.getChatbtn();
            bottomlayout.addView(appbtn.getApprovebtn());
            appvchatbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(EMChat.getInstance().isLoggedIn()) {
                        startChat();
                    }else{
                        Toast.makeText(DetailActivity.this,"请先登录！",Toast.LENGTH_LONG).show();
                    }
                }
            });
            hprd = new HttpPostReqData();
            hprd.setParam6(seller.getProduct_id());
            appbtn.setUpdatestate(hprd);
        }
        imagelayout.addView(imagesHold.getImagesView());
    }
    public void startChat(){
        Intent chatintent = new Intent(DetailActivity.this, SaleChatActivity.class);
        chatintent.putExtra(EaseConstant.EXTRA_USER_ID,seller.getSaler_cust_acct());
        chatintent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        startActivity(chatintent);
    }

    public void setBtnClick(){
        bottomHold.getClkNextbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cust_acct!=null) {
                    getorderid=GeneOrderId.getOrderid();
                    if (bottomHold.getBtnflag() == 1) { //加入购物车
                        shoptype="购物车";
                        parseOrderCar();
                        new HttpPostReqData().PostData(Global_Final.neworderpath, carorderjson);
                    }
                    if (bottomHold.getBtnflag() == 2) { //立即购买
                        shoptype="待确认";
                        Intent intent = new Intent(BaseApplication.getContext(), OrderAcitvity.class);
                        orderitem=new OrderItem();
                        orderitem.setCust_acct(cust_acct);
                        orderitem.setOrder_amount(bottomHold.getAview().getAmount());
                        orderitem.setCust_order_id(getorderid);
                        orderitem.setFactory_log(seller.getFactory_log());
                        orderitem.setFactory_name(seller.getFactory_name());
                        orderitem.setProduct_id(seller.getProduct_id());
                        orderitem.setProduct_name(seller.getProduct_name());
                        orderitem.setProduct_price(seller.getProduct_price());
                        orderitem.setProduct_unit(seller.getProduct_unit());
                        orderitem.setSaler_cust_acct(seller.getSaler_cust_acct());
                        orderlist.clear();
                        orderlist.add(orderitem);
                        ArrayList<OrderItem> orderlist2= new ArrayList<OrderItem>(orderlist);
                        intent.putParcelableArrayListExtra("orderlist", orderlist2);
                        startActivity(intent);
                    }
                    bottomHold.getPouwindow().dismiss();
                }
                if(cust_acct==null){
                    JumpToActivity.jumpToLogin(DetailActivity.this, new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            Intent acctintent = new Intent();
                            cust_acct = LoginUserAcct.user.getCust_acct();
                            setResult(2,acctintent);
                        }
                    });
                }
            }
        });
        mychatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(EMChat.getInstance().isLoggedIn()) {
                   startChat();
               }else{
                   Toast.makeText(DetailActivity.this,"请先登录！",Toast.LENGTH_LONG).show();
               }
            }
        });
    }
    public void parseOrderCar(){
        orderInfo =new OrderInfo();
        orderInfo.setCust_acct(LoginUserAcct.user.getCust_acct() );
        orderInfo.setCust_order_id(getorderid);
        orderInfo.setProduct_id(seller.getProduct_id());
        orderInfo.setOrder_status(shoptype);
        orderInfo.setOrder_amount(bottomHold.getAview().getAmount());
        orderInfo.setProduct_unit(seller.getProduct_unit());
        orderInfo.setOrder_money(Math.round(seller.getProduct_price()* orderInfo.getOrder_amount()*100)/100);
        carorderjson= ParseJsonData.parseToJson(orderInfo);
    }

}
