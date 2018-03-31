package com.learn.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.shop.myapplication.R;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/17.
 */

public class AmountView extends LinearLayout implements View.OnClickListener, TextWatcher,Serializable {

    private int amount = 1;
    private int goods_storage;
    private OnAmountChangeListener amlistner;

    private Button btnIncrease;
    private Button btnDecrease;
    private EditText etAmount;
//    private View amount_view;

//    public View getAmount_view() {
//        return amount_view;
//    }


    public int getAmount() {
        return amount;
    }

    public EditText getEtAmount() {
        return etAmount;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    public AmountView(Context context) {
        super(context);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_amount, this);
        btnDecrease = (Button) findViewById(R.id.btnDecrease);
        btnIncrease = (Button) findViewById(R.id.btnIncrease);
        etAmount = (EditText) findViewById(R.id.etAmount);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        etAmount.addTextChangedListener(this);

        TypedArray obStyAtt = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnwidth = obStyAtt.getDimensionPixelSize(R.styleable.AmountView_btnWidth, 30);
        int edwidth = obStyAtt.getDimensionPixelSize(R.styleable.AmountView_edWidth, 50);
        int btntxtsize = obStyAtt.getDimensionPixelSize(R.styleable.AmountView_btnTxtSize, 5);
        int edtxtsize = obStyAtt.getDimensionPixelSize(R.styleable.AmountView_txtSize, 5);
        obStyAtt.recycle();

        LayoutParams btnparm = new LayoutParams(btnwidth, ViewGroup.LayoutParams.MATCH_PARENT);
        btnIncrease.setLayoutParams(btnparm);
        btnDecrease.setLayoutParams(btnparm);

        LayoutParams edparm = new LayoutParams(edwidth, ViewGroup.LayoutParams.MATCH_PARENT);
        etAmount.setLayoutParams(edparm);

        btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX,btntxtsize);
        btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX,btntxtsize);
        etAmount.setTextSize(edtxtsize);
    }

    @Override
    public void onClick(View v) {

        int i=v.getId();
        if(i==R.id.btnDecrease){
            if(amount>1) amount--;
            etAmount.setText(amount+"");
        } else if (i == R.id.btnIncrease) {
            if(amount < goods_storage) amount++;
            etAmount.setText(amount+"");
        }
        etAmount.clearFocus();
    }

    public void setOnAmountChangeListener(OnAmountChangeListener alistener) {this.amlistner=alistener;}
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString().isEmpty()) return;
        amount=Integer.valueOf(s.toString());
        if(amount>goods_storage) {
            amount = goods_storage;
            etAmount.setText(goods_storage + "");
            return;
        }
        if (amlistner!=null) {
            amlistner.amountChangeListener(this,amount);
        }
    }

    public interface OnAmountChangeListener {
        void amountChangeListener(View view, int amount);
    }
}
