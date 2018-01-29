package com.hold.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shop.myapplication.BaseApplication;
import com.shop.myapplication.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/8/26 0026.
 */

public class OrderItem {
    private View orderItemView;
    @ViewInject(R.id.itemimage)
    private ImageView itemimg;
    @ViewInject(R.id.itemname)
    private TextView itemname;
    @ViewInject(R.id.itemfac)
    private TextView itemfac;
    @ViewInject(R.id.itemprice)
    private TextView itemprice;
    @ViewInject(R.id.itemamount)
    private TextView itemamount;
    @ViewInject(R.id.itemdel)
    private TextView itemdel;

    public OrderItem() {
        orderItemView=InitView();
    }

    public View InitView() {
        View orderitem = View.inflate(BaseApplication.getContext(), R.layout.orderitem_view, null);
        x.view().inject(orderitem);
        return orderitem;
    }

    public View getOrderItemView() {
        return orderItemView;
    }

    public ImageView getItemimg() {
        return itemimg;
    }

    public TextView getItemname() {
        return itemname;
    }

    public TextView getItemfac() {
        return itemfac;
    }

    public TextView getItemprice() {
        return itemprice;
    }

    public TextView getItemdel() {
        return itemdel;
    }

    public TextView getItemamount() {
        return itemamount;
    }
}
