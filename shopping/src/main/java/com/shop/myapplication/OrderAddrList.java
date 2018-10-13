package com.shop.myapplication;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bean.list.CustInfo;
import com.bean.list.Global_Final;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class OrderAddrList extends AppCompatActivity {

    private List<CustInfo> addrmanagelist = new ArrayList<>();
    private RecyclerView addrmanagerecy;
    private Button newaddrbtn;
    private AddrManageAdapter addradapter = new AddrManageAdapter();
    private String cust_acct;

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
        setContentView(R.layout.activity_order_addr_list);
        getSupportActionBar().setTitle("收货地址管理");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addrmanagerecy = (RecyclerView) findViewById(R.id.allorderaddr);
        newaddrbtn = (Button) findViewById(R.id.neworderaddr);
        addrmanagerecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        initData();
        addrmanagerecy.setAdapter(addradapter);
        addradapter.setBoxcheck(new BoxChecked() {
            @Override
            public void boxCheckedChange(View view, int position) {
                CheckBox check = (CheckBox) view;
                check.setChecked(true);
                updateCustStatus(addrmanagelist.get(position).getCust_id());
                SystemClock.sleep(300);
                initData();
            }
        });
        newaddrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderAddrManage.class);
                startActivity(intent);
            }
        });
    }

    public void initData() {
        cust_acct=getIntent().getStringExtra("cust_acct");
        new Thread() {
            public void run() {
                getDataFromServer();
                SystemClock.sleep(500);
                if (addrmanagelist != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addradapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }.start();
    }

    public void parseCustAddr(String json) {
        Gson gson = new Gson();
        addrmanagelist = gson.fromJson(json, new TypeToken<List<CustInfo>>() {
        }.getType());
    }

    public void getDataFromServer() {
        RequestParams reparam = new RequestParams(Global_Final.requestcustlist);
        reparam.addQueryStringParameter("cust_acct", cust_acct);
        x.http().post(reparam, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseCustAddr(result);
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
        });
    }

    public void updateCustStatus(int custid) {
        RequestParams params = new RequestParams(Global_Final.updatecuststatus);
        params.addQueryStringParameter("cust_acct", cust_acct);
        params.addQueryStringParameter("cust_id", String.valueOf(custid));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
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
        });
    }

    public class OrderManageHold extends RecyclerView.ViewHolder {
        private TextView addrname;
        private TextView addrphone;
        private TextView orderaddr;
        private CheckBox addrchecked;
        private Button addredit;
        private Button addrdel;
        BoxChecked boxchecked;

        public OrderManageHold(View itemView, final BoxChecked boxcheck) {
            super(itemView);
            this.addrname = (TextView) itemView.findViewById(R.id.addr_name);
            this.addrphone = (TextView) itemView.findViewById(R.id.addr_phone);
            this.orderaddr = (TextView) itemView.findViewById(R.id.orderaddr);
            this.addrchecked = (CheckBox) itemView.findViewById(R.id.addr_check);
            this.addredit = (Button) itemView.findViewById(R.id.addr_edit);
            this.addrdel = (Button) itemView.findViewById(R.id.addr_del);
            this.boxchecked=boxcheck;
            addrchecked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (boxchecked != null) {
                        boxchecked.boxCheckedChange(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    public class AddrManageAdapter extends RecyclerView.Adapter<OrderManageHold> {
        private BoxChecked boxcheck;

        public void setBoxcheck(BoxChecked boxcheck) {
            this.boxcheck = boxcheck;
        }

        public int getItemCount() {
            return addrmanagelist.size();
        }

        @Override
        public OrderManageHold onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.orderaddrlist, null);
            return new OrderManageHold(view,boxcheck);
        }

        @Override
        public void onBindViewHolder(OrderManageHold holder, int position) {
            holder.addrname.setText(addrmanagelist.get(position).getCust_name());
            holder.addrphone.setText(addrmanagelist.get(position).getCust_contact_nbr());
            holder.orderaddr.setText(addrmanagelist.get(position).getCust_address());
            if (addrmanagelist.get(position).getChecked() > 0) {
                holder.addrchecked.setChecked(true);
            } else {
                holder.addrchecked.setChecked(false);
            }

        }

    }

    public interface BoxChecked {
        void boxCheckedChange(View view, int position);
    }
}
