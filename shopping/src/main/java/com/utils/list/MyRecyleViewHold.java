package com.utils.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shop.myapplication.R;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class MyRecyleViewHold extends RecyclerView.ViewHolder {
    public TextView shopaddr;
    public TextView shopdesc;
    public ImageView shopim;
    public TextView shopname;
    public TextView shopprice;
    public ItemClickListener itemclicklsner;

    public MyRecyleViewHold(View itemView, ItemClickListener clicklsner) {
        super(itemView);
        shopaddr = (TextView) itemView.findViewById(R.id.shop_addr);
        shopdesc = (TextView) itemView.findViewById(R.id.shop_desc);
        shopim = (ImageView) itemView.findViewById(R.id.shop_im);
        shopname = (TextView) itemView.findViewById(R.id.shop_name);
        shopprice = (TextView) itemView.findViewById(R.id.shop_price);
        this.itemclicklsner = clicklsner;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemclicklsner!=null){
                    itemclicklsner.itemClick(getAdapterPosition());
                }
            }
        });
    }
}
