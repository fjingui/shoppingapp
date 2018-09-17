package com.utils.list;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class GeneOrderId {
    private static long orderNum = 0l;
    private static String date ;
    public static synchronized String getOrderid(){
        String date=new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        Long orderno=Long.parseLong(date)*10000;
        orderNum++;
        orderno+=orderNum;
        return orderno+"";
    }

    public static synchronized String getAcctNbr(String str){
       String date =  new SimpleDateFormat("yyMMdd").format(new Date());
        return str+date;
    }
}
