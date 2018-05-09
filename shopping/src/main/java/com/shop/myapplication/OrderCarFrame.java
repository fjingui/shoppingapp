package com.shop.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.list.CustInfo;
import com.bean.list.Global_Final;
import com.bean.list.OrderItem;
import com.bean.list.Seller;
import com.bean.list.SpaceItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hold.list.LoadStateView;
import com.learn.myapplication.AmountView;
import com.learn.myapplication.LoginBroadReceiver;
import com.learn.myapplication.OrderBroadcastReceiver;
import com.utils.list.GetDataFromServer;
import com.utils.list.ParseJsonData;
import com.utils.list.RemoveParent;
import com.utils.list.ViewGroupUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class OrderCarFrame extends Fragment {
    private RecyclerView orderitems;
    private TextView alldel;
    private CheckBox allchecked;
    private TextView itemcheckedpri;
    private Button checkeditems;
    private OrderAdapter orderdata;
    private List<OrderItem> orderlist = new ArrayList();
    private ArrayList<OrderItem> payorders=new ArrayList<OrderItem>();
    private CustInfo custaddr=new CustInfo();
    private String orderid=null;
    private float checkedprice=0;
    private int checkednums=0;
    private String cust_acct;
    private TextView emptydata;
    private GetDataFromServer getcarorderlist;
    private GetDataFromServer gercustaddr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View carorder = View.inflate(BaseApplication.getContext(), R.layout.order_car_view, null);
        orderitems= (RecyclerView) carorder.findViewById(R.id.orderitems);
        emptydata= (TextView) carorder.findViewById(R.id.emptyText);
        allchecked= (CheckBox) carorder.findViewById(R.id.allchecked);
        alldel = (TextView) carorder.findViewById(R.id.alldel);
        itemcheckedpri= (TextView) carorder.findViewById(R.id.itemcheckedpri);
        checkeditems= (Button) carorder.findViewById(R.id.checkednums);
        if(cust_acct==null) {
            cust_acct = ((MainActivity) getActivity()).getCust_acct();
        }

        allchecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i=0;i<orderitems.getLayoutManager().getChildCount();i++){
                     CheckBox radbtn= (CheckBox) orderitems.getLayoutManager().getChildAt(i).findViewById(R.id.itemclick);
                  if(radbtn.isChecked()) {radbtn.setChecked(false);}
                    else {
                      radbtn.setChecked(true);
                  }
                }
            }
        });
        alldel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delOrderFromServer(Global_Final.deleteallorder, orderid,cust_acct);
                SystemClock.sleep(300);
                initDataFromServer();
            }
        });

        checkeditems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (custaddr==null){
                    Intent intent = new Intent(getActivity(),OrderAddrManage.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(),OrderAcitvity.class);
                    intent.putExtra("cust_acct",cust_acct);
                   // ArrayList<OrderItem> orderlist2= new ArrayList<OrderItem>(orderlist);
                    intent.putParcelableArrayListExtra("orderlist", payorders);
                    startActivity(intent);
                }
            }
        });
        initDataFromServer();
        return carorder;
    }

    private Handler loadcardata=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        if(msg.what==1){
            orderlist= ParseJsonData.parseFromJson(getcarorderlist.getGetresult(),OrderItem[].class) ;
            orderdata=new OrderAdapter();
            orderitems.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            orderitems.setAdapter(orderdata);
            orderdata.setItemCheckedListener(new ItemCheckedListener() {
                @Override
                public void itemOnChecked() {
                    checkedhandler.sendEmptyMessage(0);
                }
            });
            orderdata.setItemDelListener(new ItemDelListener() {
                @Override
                public void itemOnDel(int position) {
                    orderid=orderlist.get(position).getCust_order_id();
                    delOrderFromServer(Global_Final.deleteorderpath, orderid,cust_acct);
                    SystemClock.sleep(300);
                    initDataFromServer();
                    orderid=null;
                }
            });

        }
        if(msg.what==2){
            custaddr= (CustInfo) ParseJsonData.parseObjectJson(gercustaddr.getGetresult(),CustInfo.class);
        }
        if(orderlist.isEmpty()){
            emptydata.setText("暂无数据!");
            emptydata.setVisibility(View.VISIBLE);
        }
    }
};
    private Handler checkedhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            payorders.clear();
            checkednums=0;
            checkedprice=0;
            for (int i=0;i<orderitems.getLayoutManager().getChildCount();i++){
                CheckBox radbtn= (CheckBox) orderitems.getLayoutManager().getChildAt(i).findViewById(R.id.itemclick);
                AmountView itemamount = (AmountView) orderitems.getLayoutManager().getChildAt(i).findViewById(R.id.itemamount);
                orderlist.get(i).setOrder_amount(itemamount.getAmount());
                if (radbtn.isChecked()){
                    payorders.add(orderlist.get(i));
                    Float itemprice=orderlist.get(i).getProduct_price();
                    int nums=itemamount.getAmount();
                    checkedprice+=itemprice*nums;
                    checkednums+=1;
                }
            }
            DecimalFormat decf= new DecimalFormat(".00");
            itemcheckedpri.setText("合计"+decf.format(checkedprice)+"元");
            checkeditems.setText("结算"+"("+checkednums+")");
            checkedprice=0;
            checkednums=0;
        }
    };

    public void initDataFromServer(){
        getcarorderlist=new GetDataFromServer(loadcardata,null,1);
        getcarorderlist.setParam(cust_acct);
        getcarorderlist.setParam2("购物车");
        new Thread(new Runnable() {
            @Override
            public void run() {
                getcarorderlist.getData(Global_Final.requestorderpath);
            }
        }).start();
        gercustaddr=new GetDataFromServer(loadcardata,null,2);
        gercustaddr.setParam(cust_acct);
        new Thread(new Runnable() {
            @Override
            public void run() {
                gercustaddr.getData(Global_Final.requestcustpath);
            }
        }).start();
        emptydata.setVisibility(View.GONE);
    }

    public void delOrderFromServer(String delPath, String orderid,String cust_acct){
        RequestParams delorderparm=new RequestParams(delPath);
        delorderparm.addQueryStringParameter("orderid", orderid);
        delorderparm.addQueryStringParameter("cust_acct",cust_acct);
        x.http().post(delorderparm,new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {
             //   Toast.makeText(getContext(),"删除完成",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class OrderHold extends RecyclerView.ViewHolder {
        CheckBox itemradio;
        ImageView itemimage;
        TextView itemname;
        TextView itemfac;
        TextView itempri;
        AmountView itemamount;
        TextView itemdel;
        ItemCheckedListener itemlistener;
        ItemDelListener itemDelListener;

        public OrderHold(View itemView,ItemCheckedListener mychecklistener,ItemDelListener mydellistener) {
            super(itemView);
            this.itemradio= (CheckBox) itemView.findViewById(R.id.itemclick);
            this.itemimage = (ImageView) itemView.findViewById(R.id.itemimage);
            this.itemname = (TextView) itemView.findViewById(R.id.itemname);
            this.itemfac = (TextView) itemView.findViewById(R.id.itemfac);
            this.itempri = (TextView) itemView.findViewById(R.id.itemprice);
            this.itemamount = (AmountView) itemView.findViewById(R.id.itemamount);
            this.itemdel= (TextView) itemView.findViewById(R.id.itemdel);
            this.itemlistener=mychecklistener;
            this.itemDelListener=mydellistener;
            itemamount.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
                @Override
                public void amountChangeListener(View view, int amount) {
                    checkedhandler.sendEmptyMessage(0);
                }
            });
            itemradio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(itemlistener!=null){
                        itemlistener.itemOnChecked();
                    }
                }

            });
            itemdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemDelListener!=null){
                        itemDelListener.itemOnDel(getAdapterPosition());
                    }
                }
            });
        }

    }

    public class OrderAdapter extends RecyclerView.Adapter<OrderHold> {
        private ItemCheckedListener itemCheckedListener;
        private ItemDelListener itemDelListener;
        @Override
        public int getItemCount() {
            return orderlist.size();
        }

        @Override
        public OrderHold onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.orderitem_view, null);
            return new OrderHold(view,itemCheckedListener,itemDelListener);
        }
        public void setItemCheckedListener(ItemCheckedListener myListener){
            this.itemCheckedListener=myListener;
        }
        public void setItemDelListener(ItemDelListener mydellistener){
            this.itemDelListener=mydellistener;
        }
        @Override
        public void onBindViewHolder(OrderHold holder, int position) {
            x.image().bind(holder.itemimage, orderlist.get(position).getFactory_log(), new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setFailureDrawableId(R.mipmap.ic_launcher)
                    .setLoadingDrawableId(R.mipmap.ic_launcher)
                    .setUseMemCache(true).build());
            holder.itemname.setText(orderlist.get(position).getProduct_name());
            holder.itemfac.setText(orderlist.get(position).getFactory_name());
            holder.itempri.setText("￥"+orderlist.get(position).getProduct_price());
            holder.itemamount.setGoods_storage(50);
            holder.itemamount.getEtAmount().setText(orderlist.get(position).getOrder_amount()+"");

        }
    }

    public interface ItemCheckedListener {
         void itemOnChecked( );
    }
    public interface ItemDelListener{
        void itemOnDel(int position);
    }


}
