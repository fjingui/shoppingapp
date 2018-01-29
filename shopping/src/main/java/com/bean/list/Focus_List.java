package com.bean.list;

/**
 * Created by Administrator on 2017/6/9.
 */

public class Focus_List {

    private String img_addr;
    private String des;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImg_addr() {
        return img_addr;
    }

    public void setImg_addr(String img_addr) {
        this.img_addr = img_addr;
    }

    @Override
    public String toString() {
        return "Focus_List{" +
                "des='" + des + '\'' +
                ", img_addr='" + img_addr + '\'' +
                '}';
    }
}
