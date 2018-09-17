package com.bean.list;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/17 0017.
 */

public class ChrgDetail {

    private int account_detail_id;
    private Long cust_acct;
    private String pay_type;
    private Float pay_money;
    private String pay_date;
    private Float balance;
    private String action;

    public Long getCust_acct() {
        return cust_acct;
    }

    public void setCust_acct(Long cust_acct) {
        this.cust_acct = cust_acct;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public Float getPay_money() {
        return pay_money;
    }

    public void setPay_money(Float pay_money) {
        this.pay_money = pay_money;
    }

    public String getPay_date() {
        return pay_date;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getAccount_detail_id() {
        return account_detail_id;
    }

    public void setAccount_detail_id(int account_detail_id) {
        this.account_detail_id = account_detail_id;
    }
}
