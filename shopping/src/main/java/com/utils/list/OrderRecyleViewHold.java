package com.utils.list;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shop.myapplication.R;

/**
 * Created by Administrator on 2018/3/30 0030.
 */

public class OrderRecyleViewHold extends RecyclerView.ViewHolder {
    public TextView orderProNumsLabel;
    public TextView proaddress;
    public ImageView orderProImg;
    public TextView mainproname;
    public TextView prouser;
    public TextView orderProPrice;
    public TextView itemmoneysum;
    public EditText itemkold;
    public EditText orderyunfee;
    public HoldItemChangeListener htcl;

    public OrderRecyleViewHold(View itemView,HoldItemChangeListener textlisn) {
        super(itemView);
        orderProNumsLabel= itemView.findViewById(R.id.order_pro_nums_label);
        proaddress= itemView.findViewById(R.id.proaddress);
        orderProImg=  itemView.findViewById(R.id.order_pro_img);
        mainproname= itemView.findViewById(R.id.mainname);
        prouser = itemView.findViewById(R.id.prousername);
        orderProPrice=  itemView.findViewById(R.id.order_pro_price);
        itemmoneysum = itemView.findViewById(R.id.itemsum);
        itemkold = itemView.findViewById(R.id.itemkold);
        orderyunfee = itemView.findViewById(R.id.order_yunfee_value);
        htcl = textlisn;

        itemkold.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (htcl != null ){
                    htcl.textChange(getAdapterPosition());
                }
            }
        });
        orderyunfee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (htcl != null ){
                    htcl.textChange(getAdapterPosition());
                }
            }
        });
    }
}
