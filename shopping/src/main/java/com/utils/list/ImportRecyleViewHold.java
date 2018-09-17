package com.utils.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shop.myapplication.R;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class ImportRecyleViewHold extends RecyclerView.ViewHolder {
    public TextView salename;
    public TextView saleunit;
    public ImageView saleimage;
    public TextView saledesc;
    public TextView saleprice;
    public ItemClickListener itemclicklsner;

    public ImportRecyleViewHold(View itemView, ItemClickListener clicklsner) {
        super(itemView);
        salename = itemView.findViewById(R.id.import_name);
        saleunit = itemView.findViewById(R.id.imp_pro_unit);
        saleimage = itemView.findViewById(R.id.improt_image);
        saledesc = itemView.findViewById(R.id.import_desc);
        saleprice = itemView.findViewById(R.id.import_price);
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
