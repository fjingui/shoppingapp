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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.list.CustInfo;
import com.bean.list.Global_Final;
import com.utils.list.FocusItemClickListen;
import com.utils.list.GetDataFromServer;
import com.utils.list.LoginUserAcct;
import com.utils.list.ParseJsonData;

import java.util.ArrayList;
import java.util.List;

public class OrderAddrList extends AppCompatActivity {

    private List<CustInfo> addrmanagelist = new ArrayList<>();
    private CustInfo cif = new CustInfo();
    private RecyclerView addrmanagerecy;
    private Button newaddrbtn;
    private String cust_acct;
    private AddrManageAdapter addradapter = new AddrManageAdapter();
    private GetDataFromServer addrlist;
    private GetDataFromServer deladd;
    private GetDataFromServer setaddrmain;

    private Handler addresslist = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 001) {
                if(addrlist.getGetresult()!=null){
                    addrmanagelist = ParseJsonData.parseFromJson(addrlist.getGetresult(), CustInfo[].class);
                    if(!addrmanagelist.isEmpty()){
                        addrmanagerecy.setLayoutManager(new LinearLayoutManager(OrderAddrList.this, LinearLayoutManager.VERTICAL, false));
                        addradapter = new AddrManageAdapter();
                        addrmanagerecy.setAdapter(addradapter);
                        addradapter.setDeladdr(new FocusItemClickListen() {
                            @Override
                            public void itemOnclick(int position) {
                                deladd = new GetDataFromServer();
                                deladd.setParam6(addrmanagelist.get(position).getCust_id());
                                deladd.delData(Global_Final.delcustaddr);
                                initData();
                            }
                        });
                        addradapter.setEdtaddr(new FocusItemClickListen() {
                            @Override
                            public void itemOnclick(int position) {
                                Intent orderadd = new Intent(OrderAddrList.this, OrderAddrManage.class);
                                cif = addrmanagelist.get(position);
                                orderadd.putExtra("cust_info", cif);
                                startActivity(orderadd);
                            }
                        });
                        addradapter.setBoxcheck(new BoxChecked() {
                            @Override
                            public void boxCheckedChange(int position) {
                                for(int i=0;i<addrmanagerecy.getChildCount();i++){
                                    RadioButton rb = addrmanagerecy.getLayoutManager().getChildAt(i).findViewById(R.id.addr_check);
                                    if(i == position){
                                        rb.setChecked(true);
                                    }else {
                                        rb.setChecked(false);
                                    }
                                }

                                setaddrmain = new GetDataFromServer();
                                setaddrmain.setParam(cust_acct);
                                setaddrmain.setParam6(addrmanagelist.get(position).getCust_id());
                                setaddrmain.getData(Global_Final.updatecuststatus);
                            }
                        });
                    }
                }

            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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
        initData();
        newaddrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderAddrManage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void initData() {
        cust_acct = LoginUserAcct.user.getCust_acct();
        addrlist = new GetDataFromServer(addresslist, null, 001);
        addrlist.setParam(cust_acct);
        addrlist.getData(Global_Final.requestcustlist);
    }

    public class OrderManageHold extends RecyclerView.ViewHolder {
        private TextView addrname;
        private TextView addrphone;
        private TextView orderaddr;
        RadioButton addrchecked;
        private Button addredit;
        private Button addrdel;
        BoxChecked boxchecked;
        private FocusItemClickListen deladdr;
        private FocusItemClickListen edtaddr;

        public OrderManageHold(View itemView, BoxChecked boxcheck, FocusItemClickListen del, FocusItemClickListen edt) {
            super(itemView);
            this.addrname = itemView.findViewById(R.id.addr_name);
            this.addrphone = itemView.findViewById(R.id.addr_phone);
            this.orderaddr = itemView.findViewById(R.id.orderaddr);
            this.addrchecked = itemView.findViewById(R.id.addr_check);
            this.addredit = itemView.findViewById(R.id.addr_edit);
            this.addrdel = itemView.findViewById(R.id.addr_del);
            this.boxchecked = boxcheck;
            this.edtaddr = edt;
            this.deladdr = del;

            addrchecked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (boxchecked != null ) {
                        boxchecked.boxCheckedChange( getAdapterPosition());
                    }
                }
            });
            addrdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (deladdr != null) {
                        if (!addrchecked.isChecked()) {
                            deladdr.itemOnclick(getAdapterPosition());
                        } else {
                            Toast.makeText(OrderAddrList.this, "不能删除当前地址!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            addredit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edtaddr != null) {
                        edtaddr.itemOnclick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public class AddrManageAdapter extends RecyclerView.Adapter<OrderManageHold> {
        private BoxChecked boxcheck;
        private FocusItemClickListen deladdr;
        private FocusItemClickListen edtaddr;

        public void setDeladdr(FocusItemClickListen deladdr) {
            this.deladdr = deladdr;
        }

        public void setEdtaddr(FocusItemClickListen edtaddr) {
            this.edtaddr = edtaddr;
        }

        public void setBoxcheck(BoxChecked boxcheck) {
            this.boxcheck = boxcheck;
        }

        public int getItemCount() {
            return addrmanagelist.size();
        }

        @Override
        public OrderManageHold onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.orderaddrlist, null);
            return new OrderManageHold(view, boxcheck, deladdr, edtaddr);
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
        void boxCheckedChange(int position);
    }
}
