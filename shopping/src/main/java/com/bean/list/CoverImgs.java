package com.bean.list;

/**
 * Created by Administrator on 2018/9/22 0022.
 */

public class CoverImgs {
    private int cover_id;
    private String cover_path;
    private String crt_date;
    private int if_show;
    public int getCover_id() {
        return cover_id;
    }
    public void setCover_id(int cover_id) {
        this.cover_id = cover_id;
    }
    public String getCover_path() {
        return cover_path;
    }
    public void setCover_path(String cover_path) {
        this.cover_path = cover_path;
    }
    public String getCrt_date() {
        return crt_date;
    }
    public void setCrt_date(String crt_date) {
        this.crt_date = crt_date;
    }
    public int getIf_show() {
        return if_show;
    }
    public void setIf_show(int if_show) {
        this.if_show = if_show;
    }
}
