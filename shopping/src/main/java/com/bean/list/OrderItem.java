package com.bean.list;

/**
 * Created by Administrator on 2017/9/2 0002.
 */

public class OrderItem {
    private String cust_order_id;
    private String factory_log;
    private String factory_name;
    private String product_name;
    private Float product_price;
    private int order_amount;

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
    public String toString() {
        return "OrderItem{" +
                "cust_order_id='" + cust_order_id + '\'' +
                ", factory_log='" + factory_log + '\'' +
                ", factory_name='" + factory_name + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_price=" + product_price +
                ", order_amount=" + order_amount +
                '}';
    }
}
