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
    public ImageView saleimage;
    public TextView saledesc;
    public TextView saleprice;
    public ItemClickListener itemclicklsner;

    public ImportRecyleViewHold(View itemView, ItemClickListener clicklsner) {
        super(itemView);
        salename = (TextView) itemView.findViewById(R.id.import_name);
        saleimage = (ImageView) itemView.findViewById(R.id.improt_image);
        saledesc = (TextView) itemView.findViewById(R.id.import_desc);
        saleprice = (TextView) itemView.findViewById(R.id.import_price);
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
