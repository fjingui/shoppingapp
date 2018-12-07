package com.bean.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/5/24.
 */

public class Seller implements Serializable {

    private int factory_id;
    private String factory_name;
    private String factory_addr;
    private Long fac_contact_nbr;
    private String join_date;
    private String factory_log;
    private String comment;
    private int product_id;
    private String product_name;
    private Float product_price;
    private int product_stor;
    private String price_unit;
    private String product_desc;
    private String product_unit;
    private String saler_cust_acct;
    private List<Product_Images> pro_imgs=new ArrayList<Product_Images>();

    public List<Product_Images> getPro_imgs() {
        return pro_imgs;
    }
    public void setPro_imgs(List<Product_Images> pro_imgs) {
        this.pro_imgs = pro_imgs;
    }
    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(String price_unit) {
        this.price_unit = price_unit;
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

    public Long getFac_contact_nbr() {
        return fac_contact_nbr;
    }

    public void setFac_contact_nbr(Long fac_contact_nbr) {
        this.fac_contact_nbr = fac_contact_nbr;
    }

    public String getFactory_addr() {
        return factory_addr;
    }

    public void setFactory_addr(String factory_addr) {
        this.factory_addr = factory_addr;
    }

    public int getFactory_id() {
        return factory_id;
    }

    public void setFactory_id(int factory_id) {
        this.factory_id = factory_id;
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

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProduct_unit() {
        return product_unit;
    }
    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public int getProduct_stor() {
        return product_stor;
    }

    public void setProduct_stor(int product_stor) {
        this.product_stor = product_stor;
    }

    public String getSaler_cust_acct() {
        return saler_cust_acct;
    }

    public void setSaler_cust_acct(String saler_cust_acct) {
        this.saler_cust_acct = saler_cust_acct;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "comment='" + comment + '\'' +
                ", factory_id=" + factory_id +
                ", factory_name='" + factory_name + '\'' +
                ", factory_addr='" + factory_addr + '\'' +
                ", fac_contact_nbr=" + fac_contact_nbr +
                ", join_date='" + join_date + '\'' +
                ", factory_log='" + factory_log + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_price=" + product_price +
                ", price_unit='" + price_unit + '\'' +
                ", product_desc='" + product_desc + '\'' +
                '}';
    }
}
