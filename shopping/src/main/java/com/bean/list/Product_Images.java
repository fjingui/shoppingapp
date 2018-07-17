package com.bean.list;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/10.
 */

public class Product_Images implements Serializable {

    private int product_id;
    private int pro_img_id;
    private String pro_img_addr;
    private String pro_img_desc;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getPro_img_id() {
        return pro_img_id;
    }

    public void setPro_img_id(int pro_img_id) {
        this.pro_img_id = pro_img_id;
    }

    public String getPro_img_addr() {
        return pro_img_addr;
    }

    public void setPro_img_addr(String pro_img_addr) {
        this.pro_img_addr = pro_img_addr;
    }

    public String getPro_img_desc() {
        return pro_img_desc;
    }

    public void setPro_img_desc(String pro_img_desc) {
        this.pro_img_desc = pro_img_desc;
    }
}
