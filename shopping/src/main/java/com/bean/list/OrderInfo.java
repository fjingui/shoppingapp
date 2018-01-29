package com.bean.list;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class OrderInfo implements Serializable{
    private String cust_acct;
    private int product_id;
    private String order_status;
    private int order_amount;
    private float order_money;

    public String getCust_acct() {
        return cust_acct;
    }

    public void setCust_acct(String cust_acct) {
        this.cust_acct = cust_acct;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public int getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(int order_amount) {
        this.order_amount = order_amount;
    }

    public float getOrder_money() {
        return order_money;
    }

    public void setOrder_money(float order_money) {
        this.order_money = order_money;
    }
}