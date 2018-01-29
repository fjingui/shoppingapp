package com.bean.list;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class CustInfo implements Serializable{
    private int cust_id;
    private String cust_name;
    private String cust_address;
    private String cust_contact_nbr;
    private String cust_acct;
    private int checked;

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

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

    @Override
    public String toString() {
        return "CustInfo{" +
                "cust_acct='" + cust_acct + '\'' +
                ", cust_id=" + cust_id +
                ", cust_name='" + cust_name + '\'' +
                ", cust_address='" + cust_address + '\'' +
                ", cust_contact_nbr='" + cust_contact_nbr + '\'' +
                '}';
    }
}
