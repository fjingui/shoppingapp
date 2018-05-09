package com.shop.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.list.Global_Final;
import com.bean.list.Seller;
import com.bean.list.SpaceItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hold.list.LoadStateView;
import com.utils.list.FocusItemClickListen;
import com.utils.list.GetDataFromServer;
import com.utils.list.ParseJsonData;
import com.utils.list.RemoveParent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/9.
 */

public class LimitFocus extends Fragment {

    private List<Seller> limitlist=new ArrayList<>();
    private FocusAdapter focusadapter;
    private RecyclerView preciousrv;
    private String cust_acct;
    private LoadStateView loaddatastate=new LoadStateView();
    private String requeststate;
    private GetDataFromServer preciouspath;
    private FrameLayout loadstate;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View focusframe = View.inflate(getActivity(), R.layout.fragment_focus,null);
        preciousrv = (RecyclerView) focusframe.findViewById(R.id.recyview);
        loadstate = (FrameLayout) focusframe.findViewById(R.id.preciousload);
        cust_acct=((MainActivity)getActivity()).getCust_acct();
        initData();
        return focusframe;
    }
private Handler prechandler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        if(msg.what==1){
            limitlist= ParseJsonData.parseFromJson(preciouspath.getGetresult(),Seller[].class);
            requeststate=preciouspath.getState();
            preciousrv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            focusadapter=new FocusAdapter();
            focusadapter.setFocusitemclnlsn(new FocusItemClickListen() {
                @Override
                public void itemOnclick(int position) {
                    Intent intent = new Intent(getActivity(),DetailActivity.class);
                    intent.putExtra("detailseller",limitlist.get(position));
                    intent.putExtra("cust_acct",cust_acct);
                    startActivityForResult(intent,2);
                }
            });
            preciousrv.setAdapter(focusadapter);
            SpaceItem space = new SpaceItem(16);
            preciousrv.addItemDecoration(space);
        }
        if(requeststate=="success"){
           loadstate.removeAllViews();
        }
    }
};
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode==2) {
            cust_acct=data.getStringExtra("cust_acct");
            ((MainActivity)getActivity()).setCust_acct(cust_acct);
        }
    }

    public void initData() {
        preciouspath=new GetDataFromServer(prechandler,loadstate,1);
        new Thread(){
            @Override
            public void run() {
                preciouspath.getData(Global_Final.limitsellerpath);
        }
        }.start();
        loadstate.removeAllViews();
        RemoveParent.removeParent(loaddatastate.showLoading(true));
        loadstate.addView(loaddatastate.showLoading(true));
    }
    public class  FocusHold extends RecyclerView.ViewHolder {
        ImageView iview;
        TextView tview;
        FocusItemClickListen myfocusitemclicklisten;
        public FocusHold(View itemView,FocusItemClickListen focusitemclnlsn) {
            super(itemView);
            this.iview = (ImageView) itemView.findViewById(R.id.limit_img);
            this.tview = (TextView) itemView.findViewById(R.id.limit_desc);
            this.myfocusitemclicklisten=focusitemclnlsn;
            iview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(myfocusitemclicklisten!=null){
                        myfocusitemclicklisten.itemOnclick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public class FocusAdapter extends RecyclerView.Adapter<FocusHold> {

        private FocusItemClickListen focusitemclnlsn;

        public void setFocusitemclnlsn(FocusItemClickListen focusitemclnlsn) {
            this.focusitemclnlsn = focusitemclnlsn;
        }
        @Override
        public int getItemCount() {
            return limitlist.size();
        }
        @Override
        public FocusHold onCreateViewHolder(ViewGroup parent, int viewType) {
            View focus = View.inflate(parent.getContext(), R.layout.focuslimit, null);
            return new FocusHold(focus,focusitemclnlsn);
        }

        @Override
        public void onBindViewHolder(FocusHold holder, int position) {

           x.image().bind(holder.iview,limitlist.get(position).getFactory_log(),new ImageOptions.Builder()
                   .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                   .setFailureDrawableId(R.mipmap.ic_launcher)
                   .setLoadingDrawableId(R.mipmap.ic_launcher)
                   .setUseMemCache(true).build());
            holder.tview.setText(limitlist.get(position).getProduct_desc());

        }
    }
}
