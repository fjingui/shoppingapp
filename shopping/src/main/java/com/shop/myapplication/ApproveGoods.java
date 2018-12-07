package com.shop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bean.list.Global_Final;
import com.bean.list.Seller;
import com.utils.list.GetDataFromServer;
import com.utils.list.ItemClickListener;
import com.utils.list.ParseJsonData;
import com.utils.list.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ApproveGoods extends AppCompatActivity {

    private TextView lstatev ;
    private List<Seller> goodslist =new ArrayList<>();
    private GetDataFromServer getgoods;
    private RecyclerViewAdapter goodsrva;
    private RecyclerView goods;
    private Handler initdata = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0001){
                goodslist = ParseJsonData.parseFromJson(getgoods.getGetresult(),Seller[].class);
                if(goodslist.isEmpty()){
                    lstatev.setText(R.string.emptydata);
                    lstatev.setVisibility(View.VISIBLE);
                }else{
                    lstatev.setVisibility(View.GONE);
                    goodsrva = new RecyclerViewAdapter(goodslist);
                    goods.setLayoutManager(new LinearLayoutManager(ApproveGoods.this,LinearLayoutManager.VERTICAL,false));
                    goods.setAdapter(goodsrva);
                    goodsrva.setIclistener(new ItemClickListener() {
                        @Override
                        public void itemClick(int position) {
                            Intent intent = new Intent(BaseApplication.getContext(), DetailActivity.class);
                            intent.putExtra("detailseller", goodslist.get(position));
                            startActivity(intent);
                        }
                    });
                }

            }
        }
    };
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
        setContentView(R.layout.activity_approve_goods);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        goods = (RecyclerView) findViewById(R.id.goods);
        lstatev = (TextView) findViewById(R.id.emptydata);
        initData();
    }
    public void initData(){
        getgoods = new GetDataFromServer(initdata,null,0001);
        getgoods.getData(Global_Final.approvegood);
    }
}
