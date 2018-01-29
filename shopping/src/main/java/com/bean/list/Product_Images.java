package com.bean.list;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/10.
 */

public class Product_Images implements Serializable {
    private String pro_Img_Addr;
    private String pro_Img_Desc;

    public String getPro_Img_Addr() {
        return pro_Img_Addr;
    }

    public void setPro_Img_Addr(String pro_Img_Addr) {
        this.pro_Img_Addr = pro_Img_Addr;
    }

    public String getPro_Img_Desc() {
        return pro_Img_Desc;
    }

    public void setPro_Img_Desc(String pro_Img_Desc) {
        this.pro_Img_Desc = pro_Img_Desc;
    }
}
