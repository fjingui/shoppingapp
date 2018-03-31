package com.utils.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shop.myapplication.R;

/**
 * Created by Administrator on 2018/3/30 0030.
 */

public class OrderRecyleViewHold extends RecyclerView.ViewHolder {
    public TextView orderProNumsLabel;
    public TextView subProName;
    public ImageView orderProImg;
    public TextView mainproname;
    public TextView orderProPrice;

    public OrderRecyleViewHold(View itemView) {
        super(itemView);
        orderProNumsLabel= (TextView) itemView.findViewById(R.id.order_pro_nums_label);
        subProName= (TextView) itemView.findViewById(R.id.subname);
        orderProImg= (ImageView) itemView.findViewById(R.id.order_pro_img);
        mainproname= (TextView) itemView.findViewById(R.id.mainname);
        orderProPrice= (TextView) itemView.findViewById(R.id.order_pro_price);
    }
}
