package com.bean.list;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class Product implements Serializable{
    private int product_id;
    private String product_name;
    private Float product_price;
    private String price_unit;
    private String product_desc ;
    private String sale_state;
    private int factory_id;
    private String product_unit;
    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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

    public String getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(String price_unit) {
        this.price_unit = price_unit;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getSale_state() {
        return sale_state;
    }

    public void setSale_state(String sale_state) {
        this.sale_state = sale_state;
    }

    public int getFactory_id() {
        return factory_id;
    }

    public void setFactory_id(int factory_id) {
        this.factory_id = factory_id;
    }

    public String getProduct_unit() {
        return product_unit;
    }
    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }
}
