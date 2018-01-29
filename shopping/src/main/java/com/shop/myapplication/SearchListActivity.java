package com.shop.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.list.Global_Final;
import com.bean.list.Seller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private SearchView keysearch;
    private List<Seller> keylist=new ArrayList();
    private SaleRVAdapter listadapter=new SaleRVAdapter();
    private String cust_acct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search_list);
        keysearch = (SearchView) findViewById(R.id.keysearch);
        searchlist= (RecyclerView) findViewById(R.id.searchlist);
        Intent intent = getIntent();
        cust_acct=intent.getStringExtra("cust_acct");
        searchlist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        searchlist.setAdapter(listadapter);

        keysearch.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                Message msg = Message.obtain();
                msg.obj=string;
                keyhandler.sendMessage(msg);

            }
        });
        keysearch.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
    }
    private Handler keyhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String str = (String) msg.obj;
            getDataFromServer(str);
            SystemClock.sleep(1000);
            if(keylist!=null){
                listadapter.notifyDataSetChanged();
            }
        }
    };

    public void getDataFromServer(String str) {
        RequestParams param = new RequestParams(Global_Final.sellerpath);
        param.addQueryStringParameter("proname",str);
        x.http().get(param, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        gsonParse(result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

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

    class SaleRecyleView extends RecyclerView.ViewHolder{
        private TextView shopaddr;
        private TextView shopdesc;
        private ImageView shopim;
        private TextView shopname;
        private TextView shopprice;
        public SaleRecyleView(View itemView) {
            super(itemView);
            shopaddr = (TextView) itemView.findViewById(R.id.shop_addr);
            shopdesc = (TextView) itemView.findViewById(R.id.shop_desc);
            shopim = (ImageView) itemView.findViewById(R.id.shop_im);
            shopname = (TextView) itemView.findViewById(R.id.shop_name);
            shopprice = (TextView) itemView.findViewById(R.id.shop_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BaseApplication.getContext(), DetailActivity.class);
                    intent.putExtra("detailseller", keylist.get(getAdapterPosition()));
                    intent.putExtra("cust_acct",cust_acct);
                    startActivity(intent);
                }
            });
        }
    }
    class SaleRVAdapter extends RecyclerView.Adapter<SaleRecyleView>{
        @Override
        public int getItemCount() {
            return keylist.size();
        }

        @Override
        public SaleRecyleView onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(getApplicationContext(), R.layout.shoplist, null);
            return new SaleRecyleView(view);
        }

        @Override
        public void onBindViewHolder(SaleRecyleView holder, int position) {
            x.image().bind(holder.shopim, keylist.get(position).getFactory_log(),
                    new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                            .setFailureDrawableId(R.mipmap.ic_launcher).setLoadingDrawableId(R.mipmap.ic_launcher)
                            .setUseMemCache(true).build());

            holder.shopaddr.setText(keylist.get(position).getFactory_addr());
            holder.shopdesc.setText(keylist.get(position).getComment());
            holder.shopname.setText(keylist.get(position).getFactory_name());
            holder.shopprice.setText(keylist.get(position).getProduct_price() + keylist.get(position).getPrice_unit());

        }
    }
}
