package com.hold.list;

import android.app.Application;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.list.Seller;
import com.learn.myapplication.AmountView;
import com.shop.myapplication.BaseApplication;
import com.shop.myapplication.JumpToActivity;
import com.shop.myapplication.MainActivity;
import com.shop.myapplication.OrderAcitvity;
import com.shop.myapplication.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.Serializable;

import static android.view.View.X;

/**
 * Created by Administrator on 2017/7/8.
 */

public class BottomHold implements View.OnClickListener {
    private View bottomtview;

    private TextView gwctv;
    private TextView gmtv;
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

    public BottomHold() {
        bottomtview = initView();
    }

    public View initView() {
        View view = View.inflate(BaseApplication.getContext(), R.layout.bottom_view, null);
        gwctv = (TextView) view.findViewById(R.id.gwctv);
        gmtv = (TextView) view.findViewById(R.id.gmtv);
        gmtv.setOnClickListener(this);
        gwctv.setOnClickListener(this);

        shopnums = View.inflate(BaseApplication.getContext(), R.layout.shopping_nums, null);
        clkNextbtn = (Button) shopnums.findViewById(R.id.pur_nextbtn);
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

        aview= (AmountView) shopnums.findViewById(R.id.amountview);
        if (seller.getFactory_name().equals("拍卖珍藏")){
            aview.setGoods_storage(1);
        }else {
            aview.setGoods_storage(50);
        }
        ImageView purcharimg = (ImageView) shopnums.findViewById(R.id.purchar_img);
        TextView popmainname = (TextView) shopnums.findViewById(R.id.pop_mainname);
        TextView popsubname = (TextView) shopnums.findViewById(R.id.pop_subname);
        TextView purprice = (TextView) shopnums.findViewById(R.id.purchar_price);

        x.image().bind(purcharimg,seller.getFactory_log(),
                new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                        .setFailureDrawableId(R.mipmap.ic_launcher).setLoadingDrawableId(R.mipmap.ic_launcher)
                        .setUseMemCache(true).build());
        popmainname.setText(seller.getFactory_name());
        popsubname.setText(seller.getProduct_name());
        purprice.setText(seller.getProduct_price()+seller.getPrice_unit());


        PopupWindow popWin = new PopupWindow(shopnums, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT){
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


