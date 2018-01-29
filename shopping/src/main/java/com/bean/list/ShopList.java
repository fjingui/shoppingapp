package com.bean.list;

/**
 * Created by Administrator on 2017/4/25/025.
 */

public class ShopList {
    private int shop_image;
    private String shop_name;
    private String shop_price;
    private String shop_addr;
    private String shop_desc;

    public ShopList(String shop_addr, String shop_desc, int shop_image, String shop_name, String shop_price) {
        this.shop_addr = shop_addr;
        this.shop_desc = shop_desc;
        this.shop_image = shop_image;
        this.shop_name = shop_name;
        this.shop_price = shop_price;
    }

    public int getShop_image() {
        return shop_image;
    }

    public void setShop_image(int shop_image) {
        this.shop_image = shop_image;
    }

    public String getShop_addr() {
        return shop_addr;
    }

    public void setShop_addr(String shop_addr) {
        this.shop_addr = shop_addr;
    }

    public String getShop_desc() {
        return shop_desc;
    }

    public void setShop_desc(String shop_desc) {
        this.shop_desc = shop_desc;
    }


    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }
}
