package com.shop.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.list.CustInfo;
import com.bean.list.Global_Final;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.utils.list.HttpPostReqData;
import com.utils.list.ParseJsonData;

public class OrderAddrManage extends AppCompatActivity {

    private EditText addrname,addrphone,addrdetail;
    private TextView addrselect;
    private Button saveaddr;
    private String newcustjson;
    private CustInfo newcust = new CustInfo();
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_addr_manage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("新增收货地址");
        addrname= (EditText) findViewById(R.id.newaddrnmae);
        addrphone= (EditText) findViewById(R.id.newaddrphone);
        addrdetail= (EditText) findViewById(R.id.newaddrdetail);
        addrselect= (TextView) findViewById(R.id.newaddrprov);
        saveaddr= (Button) findViewById(R.id.addrsave);
        addrselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseArea(v);
            }
        });
        initData();
        saveaddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addrname.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"收货人不能为空",Toast.LENGTH_SHORT).show();
                } else if (addrphone.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"联系电话不能为空",Toast.LENGTH_SHORT).show();
                } else if (addrselect.getText().toString().isEmpty() || addrdetail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"收货地址不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    int cid = 0;
                    if(!TextUtils.isEmpty(newcust.getCust_id()+"") ) {
                        cid =newcust.getCust_id();
                    }
                        newCustInfo(cid,"18956662004",addrname.getText().toString(),addrphone.getText().toString(),addrselect.getText().toString()+addrdetail.getText().toString());
                        if(cid > 0){
                            new HttpPostReqData().PostData(Global_Final.updatecust,newcustjson);
                        }else{
                            new HttpPostReqData().PostData(Global_Final.newcustpath,newcustjson);
                        }

                        finish();
                }
            }
        });
    }
    private void chooseArea(View view){
//        判断输入法的隐藏状态
        InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(input.isActive()){
            input.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
        selectAddr();
    }
    private void selectAddr(){
        CityPicker cityPicker = new CityPicker.Builder(this)
                .textSize(14).title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("安徽省")
                .city("池州市")
                .district("贵池区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false).build();
        cityPicker.show();
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                String provice=citySelected[0];
                String city=citySelected[1];
                String dstrict=citySelected[2];
                addrselect.setText(provice.trim()+city.trim()+dstrict.trim());
            }
        });
    }
    public void initData(){
        newcust = (CustInfo) getIntent().getSerializableExtra("cust_info");
        if(newcust != null){
            addrname.setText(newcust.getCust_name());
            addrphone.setText(newcust.getCust_contact_nbr());
        }
    }
    private void newCustInfo(int cust_id,String cust_acct,String custname,String custphone,String addr){
        if( cust_id >0 ){
            newcust.setCust_id(cust_id);
        }
        newcust.setCust_acct(cust_acct);
        newcust.setCust_name(custname);
        newcust.setCust_contact_nbr(custphone);
        newcust.setCust_address(addr);
        newcustjson=ParseJsonData.parseToJson(newcust);
    }

}
