package com.hold.list;

import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bean.list.Seller;
import com.learn.myapplication.AmountView;
import com.shop.myapplication.BaseApplication;
import com.shop.myapplication.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by Administrator on 2017/7/8.
 */

public class BottomHold implements View.OnClickListener {
    private View bottomtview;

    private TextView gwctv;
    private TextView gmtv;
    private Button chatbtn;
    private Seller seller;
    private View coverView;
    private AmountView aview;
    private Button clkNextbtn;
    private View shopnums;
    private int btnflag=0;
    private PopupWindow pouwindow;

    public PopupWindow getPouwindow() {
        return pouwindow;
    }

    public int getBtnflag() {
        return btnflag;
    }

    public Button getClkNextbtn() {
        return clkNextbtn;
    }
    public View getBottomtview() {
        return bottomtview;
    }

    public AmountView getAview() {
        return aview;
    }

    public Button getChatbtn() {
        return chatbtn;
    }
    public BottomHold() {
        bottomtview = initView();
    }

    public View initView() {
        View view = View.inflate(BaseApplication.getContext(), R.layout.bottom_view, null);
        gwctv = view.findViewById(R.id.gwctv);
        gmtv = view.findViewById(R.id.gmtv);
        chatbtn = view.findViewById(R.id.chat_btn);
        gmtv.setOnClickListener(this);
        gwctv.setOnClickListener(this);

        shopnums = View.inflate(BaseApplication.getContext(), R.layout.shopping_nums, null);
        clkNextbtn =  shopnums.findViewById(R.id.pur_nextbtn);
        return view;
    }

    public void setData(Seller data) {
        this.seller=data;
    }

    public void setCoverView(View coverView) {
        this.coverView = coverView;
        coverView.setVisibility(View.GONE);
    }


    public PopupWindow showPopupWindow(View v) {

        aview= shopnums.findViewById(R.id.amountview);
        if (seller.getFactory_name().equals("拍卖珍藏")){
            aview.setGoods_storage(1);
        }else {
            aview.setGoods_storage(50);
        }
        ImageView purcharimg = shopnums.findViewById(R.id.purchar_img);
        TextView popmainname = shopnums.findViewById(R.id.pop_mainname);
        TextView popsubname =  shopnums.findViewById(R.id.pop_subname);
        TextView proaddr = shopnums.findViewById(R.id.proaddress);
        TextView purprice = shopnums.findViewById(R.id.purchar_price);

        x.image().bind(purcharimg,seller.getFactory_log(),
                new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                        .setFailureDrawableId(R.mipmap.ic_launcher).setLoadingDrawableId(R.mipmap.ic_launcher)
                        .setUseMemCache(true).build());
        popmainname.setText(seller.getProduct_name());
        popsubname.setText(seller.getFactory_name());
        proaddr.setText(seller.getFactory_addr());
        purprice.setText(seller.getProduct_price()+seller.getPrice_unit());

        PopupWindow popWin = new PopupWindow(shopnums, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT){
            @Override
            public void dismiss() {
                coverView.setVisibility(View.GONE);
                super.dismiss();
            }

    };

        popWin.setFocusable(true);
        popWin.setOutsideTouchable(true);
        coverView.setVisibility(View.VISIBLE);
      //  popWin.showAsDropDown(v);
        popWin.showAtLocation(v,Gravity.BOTTOM,0,0);
        return popWin;
    }

    @Override
    public void onClick(View v) {

            if (v == gwctv) {
                btnflag = 1;
            }
            if (v == gmtv) {
                btnflag = 2;
            }
                pouwindow = showPopupWindow(v);
        }

}


