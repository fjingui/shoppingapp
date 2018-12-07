package com.bean.list;

/**
 * Created by Administrator on 2017/5/26.
 */

public class Global_Final {
    //10.0.2.2 /114.115.138.24 /192.168.1.100
    public static final String basepath = "http://192.168.1.101:8080/shopping_service/";
    public static final String imagespath =basepath +"json/homeimgs";
    public static final String sellerpath =basepath +"json/sellerjson";
    public static final String approvegood =basepath +"json/approvejson";
    public static final String importseller =basepath +"json/importseller";
    public static final String limitsellerpath=basepath + "json/limitseller";
    public static final String neworderpath=basepath+"json/neworder";
    public static final String requestorderpath=basepath+"json/reqorder_queryOrder";
    public static final String requestsalerorders=basepath+"json/reqorder_querySalerOrders";
    public static final String updateorderchrg=basepath+"json/updateorder";
    public static final String updateorderstatus=basepath+"json/updateorderstatus";
    public static final String newcustpath=basepath+"json/newcust2_insertCust";
    public static final String requestcustpath=basepath+"json/requestcust_queryCust";
    public static final String delcustaddr=basepath+"json/newcust_delCustAddress";
    public static final String requestcustlist=basepath+"json/requestcust_queryCustList";
    public static final String updatecuststatus=basepath+"json/newcust_updateCustStatus";
    public static final String updatecust=basepath+"json/newcust2_updateCust";
    public static final String deleteorderpath=basepath+"json/order_deleteOrder";
    public static final String deleteallorder=basepath+"json/order_delallorder";
    public static final String userlogin=basepath+"json/userlogin";
    public static final String getfactory=basepath+"manage/fac_select";
    public static final String getproduct=basepath+"manage/pro_select";
    public static final String insertfactory=basepath+"manage/fac_insertFactory";
    public static final String insertproduct=basepath+"manage/pro_insertProduct";
    public static final String insertproimg=basepath+"manage/proimg_insert";
    public static final String saveproimgs =basepath+"manage/proimg_save";
    public static final String setproimguri=basepath+"productimages/";
    public static final String delfactory=basepath+"manage/fac_del";
    public static final String delproduct=basepath+"manage/pro_del";
    public static final String delproimg=basepath+"manage/proimg_del";
    public static final String accountdetail =basepath+"json/account_detail";
    public static final String accdtailinsert=basepath+"json/accdtail_insert";
    public static final String accountbalance=basepath+"json/account_balance";
    public static final String register=basepath+"manage/regesteacct";
    public static final String qureyacct=basepath+"manage/queryacct";
    public static final String withdraw_get=basepath+"json/wda_getWithDrawAcct";
    public static final String withdraw_insert=basepath+"json/wda_insertWithDrawAcct";
    public static final String withdraw_saveAcctImage=basepath+"json/wda_saveAcctImage";
    public static final String setwithimagepath=basepath+"withdrawimages/";
    public static final String withperform_get=basepath+"json/withperform_selectWithPerform";
    public static final String withperform_update=basepath+"json/withperform_update";
    public static final String getcoverimg=basepath+"json/getcoverimgs";
    public static final String apkversion =basepath+"manage/getapkver";
}
