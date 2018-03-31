package com.bean.list;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/9/2 0002.
 */

public class OrderItem implements Parcelable{
    private String cust_order_id;
    private String factory_log;
    private String factory_name;
    private String product_name;
    private Float product_price;
    private int order_amount;

    public OrderItem(){

    }
    public String getCust_order_id() {
        return cust_order_id;
    }

    public void setCust_order_id(String cust_order_id) {
        this.cust_order_id = cust_order_id;
    }

    public String getFactory_log() {
        return factory_log;
    }

    public void setFactory_log(String factory_log) {
        this.factory_log = factory_log;
    }

    public String getFactory_name() {
        return factory_name;
    }

    public void setFactory_name(String factory_name) {
        this.factory_name = factory_name;
    }

    public int getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(int order_amount) {
        this.order_amount = order_amount;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Float product_price) {
        this.product_price = product_price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cust_order_id);
        dest.writeString(factory_log);
        dest.writeString(factory_name);
        dest.writeString(product_name);
        dest.writeFloat(product_price);
        dest.writeInt(order_amount);
    }
    protected OrderItem(Parcel in) {
        cust_order_id = in.readString();
        factory_log = in.readString();
        factory_name = in.readString();
        product_name = in.readString();
        product_price=in.readFloat();
        order_amount = in.readInt();
    }
    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
}
