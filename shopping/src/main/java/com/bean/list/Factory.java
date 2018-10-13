package com.bean.list;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/5 0005.
 */

public class Factory implements Serializable{
    private int factory_id;
    private String cust_acct;
    private String factory_name;
    private String factory_addr;
    private String fac_contact_nbr;
    private String factory_log;
    private String comment;
    public int getFactory_id() {
        return factory_id;
    }
    public void setFactory_id(int factory_id) {
        this.factory_id = factory_id;
    }
    public String getCust_acct() {
        return cust_acct;
    }
    public void setCust_acct(String cust_acct) {
        this.cust_acct = cust_acct;
    }
    public String getFactory_name() {
        return factory_name;
    }
    public void setFactory_name(String factory_name) {
        this.factory_name = factory_name;
    }
    public String getFactory_addr() {
        return factory_addr;
    }
    public void setFactory_addr(String factory_addr) {
        this.factory_addr = factory_addr;
    }
    public String getFac_contact_nbr() {
        return fac_contact_nbr;
    }
    public void setFac_contact_nbr(String fac_contact_nbr) {
        this.fac_contact_nbr = fac_contact_nbr;
    }
    public String getFactory_log() {
        return factory_log;
    }
    public void setFactory_log(String factory_log) {
        this.factory_log = factory_log;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Factory{" +
                "factory_id=" + factory_id +
                ", cust_acct=" + cust_acct +
                ", factory_name='" + factory_name + '\'' +
                ", factory_addr='" + factory_addr + '\'' +
                ", fac_contact_nbr=" + fac_contact_nbr +
                ", factory_log='" + factory_log + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
