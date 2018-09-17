package com.shop.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.list.Global_Final;
import com.bean.list.Seller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utils.list.ItemClickListener;
import com.utils.list.MyRecyleViewHold;
import com.utils.list.RecyclerViewAdapter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

public class SearchListActivity extends AppCompatActivity {

    private RecyclerView searchlist;
    private List<Seller> keylist=new ArrayList();
    private RecyclerViewAdapter listadapter ;
    private String cust_acct;
    private String searchKey;
    private TextView searchhint;

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
        setContentView(R.layout.activity_search_list);
        searchlist= (RecyclerView) findViewById(R.id.searchlist);
        searchhint= (TextView) findViewById(R.id.searchDataHint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();

    }
    public void initData(){
        cust_acct=getIntent().getStringExtra("cust_acct");
        searchKey=getIntent().getStringExtra("searchkey");
        getDataFromServer(searchKey);
    }
    private Handler keyhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            searchhint.setVisibility(View.GONE);
           if(msg.what==1 ){
               if(keylist.size()>0) {
                   searchlist.setLayoutManager(new LinearLayoutManager(SearchListActivity.this, LinearLayoutManager.VERTICAL, false));
                   listadapter = new RecyclerViewAdapter(keylist);
                   listadapter.setIclistener(new ItemClickListener() {
                       @Override
                       public void itemClick(int position) {
                           Intent intent = new Intent(BaseApplication.getContext(), DetailActivity.class);
                           intent.putExtra("detailseller", keylist.get(position));
                           intent.putExtra("cust_acct",cust_acct);
                           startActivity(intent);
                       }
                   });
                   searchlist.setAdapter(listadapter);
               }else{
                   searchhint.setText("没有匹配的搜索结果！");
                   searchhint.setVisibility(View.VISIBLE);
               }
           }
            if(msg.what==2){
                searchhint.setText("错误或网络异常！");
            }
        }
    };

    public void getDataFromServer(String str) {
        RequestParams param = new RequestParams(Global_Final.sellerpath);
        param.addQueryStringParameter("proname",searchKey);
        x.http().get(param, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        gsonParse(result);
                        keyhandler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        keyhandler.sendEmptyMessage(2);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        keyhandler.sendEmptyMessage(2);
                    }

                    @Override
                    public void onFinished() {
                    }
                }
        );
    }

    public void gsonParse(String json) {
        Gson sellerjson = new Gson();
        keylist = sellerjson.fromJson(json, new TypeToken<List<Seller>>() {}.getType());
    }
}
