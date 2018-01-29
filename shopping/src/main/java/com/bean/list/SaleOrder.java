package com.bean.list;

/**
 * Created by Administrator on 2017/8/8.
 */

public class SaleOrder {
    private String cust_name;
    private String cust_address;
    private String cust_contact_nbr;
    private String cust_acct;
    private int product_id;
    private String order_status;
    private int order_amount;
    private float order_money;

    public String getCust_address() {
        return cust_address;
    }

    public void setCust_address(String cust_address) {
        this.cust_address = cust_address;
    }

    public String getCust_contact_nbr() {
        return cust_contact_nbr;
    }

    public void setCust_contact_nbr(String cust_contact_nbr) {
        this.cust_contact_nbr = cust_contact_nbr;
    }

    public String getCust_acct() {
        return cust_acct;
    }

    public void setCust_acct(String cust_acct) {
        this.cust_acct = cust_acct;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
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

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

}
