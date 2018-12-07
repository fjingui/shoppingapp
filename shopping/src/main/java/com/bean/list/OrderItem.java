package com.bean.list;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/9/2 0002.
 */

public class OrderItem implements Parcelable{
    private String cust_order_id;
    private String cust_acct;
    private String factory_log;
    private String factory_name;
    private int product_id;
    private String product_name;
    private Float product_price;
    private int product_stor;
    private int order_amount;
    private float order_money;
    private String order_status;
    private String product_unit;
    private String saler_cust_acct;
    private float express_chrg;
    private float discount_chrg;

    public OrderItem(){
    }

    public String getCust_acct() {
        return cust_acct;
    }

    public void setCust_acct(String cust_acct) {
        this.cust_acct = cust_acct;
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

    public int getProduct_stor() {
        return product_stor;
    }

    public void setProduct_stor(int product_stor) {
        this.product_stor = product_stor;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public float getOrder_money() {
        return order_money;
    }
    public void setOrder_money(float order_money) {
        this.order_money = order_money;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public String getSaler_cust_acct() {
        return saler_cust_acct;
    }

    public void setSaler_cust_acct(String saler_cust_acct) {
        this.saler_cust_acct = saler_cust_acct;
    }

    public float getDiscount_chrg() {
        return discount_chrg;
    }

    public void setDiscount_chrg(float discount_chrg) {
        this.discount_chrg = discount_chrg;
    }

    public float getExpress_chrg() {
        return express_chrg;
    }

    public void setExpress_chrg(float express_chrg) {
        this.express_chrg = express_chrg;
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
        dest.writeInt(product_id);
        dest.writeFloat(order_money);
        dest.writeString(order_status);
        dest.writeString(product_unit);
        dest.writeString(cust_acct);
        dest.writeInt(product_stor);
        dest.writeString(saler_cust_acct);
        dest.writeFloat(express_chrg);
        dest.writeFloat(discount_chrg);
    }
    protected OrderItem(Parcel in) {
        cust_order_id = in.readString();
        factory_log = in.readString();
        factory_name = in.readString();
        product_name = in.readString();
        product_price=in.readFloat();
        order_amount = in.readInt();
        product_id=in.readInt();
        order_money=in.readFloat();
        order_status=in.readString();
        product_unit=in.readString();
        cust_acct=in.readString();
        product_stor=in.readInt();
        saler_cust_acct=in.readString();
        express_chrg=in.readFloat();
        discount_chrg=in.readFloat();
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
