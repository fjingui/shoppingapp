package com.shop.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.bean.list.Global_Final;
import com.bean.list.LogoImages;
import com.bean.list.Seller;
import com.bean.list.SpaceItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hold.list.LoadStateView;
import com.utils.list.GetDataFromServer;
import com.utils.list.ImportRecyleViewHold;
import com.utils.list.ItemClickListener;
import com.utils.list.LoginUserAcct;
import com.utils.list.MyRecyleViewHold;
import com.utils.list.ParseJsonData;
import com.utils.list.RecyclerViewAdapter;
import com.utils.list.RemoveParent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/6.
 */

public class HomeFrame extends Fragment {

 //   private int[] images = {R.mipmap.bus, R.mipmap.car, R.mipmap.dzq, R.mipmap.girl, R.mipmap.mm, R.mipmap.zlqx};
    private List<ImageView> imageviews = new ArrayList();
    private List<LogoImages> logoimages = new ArrayList();
    private List<Seller> shoplist = new ArrayList();
    private List<Seller> importseller = new ArrayList();
    private LinearLayout pointgroup;
    private ImageView[] pointarr;
    private ViewPager myviewpager;
    private RecyclerView salelist;
    private RecyclerView importlist;
    private RecyclerViewAdapter salerva;
    private ImportRecyclerAdapter importva;
    private SearchView searchview;
    private FrameLayout stateview;
    private View mainlayout;
    private String cust_acct;
    private LoadStateView loaddatastate=new LoadStateView();
    private String sellerstate;
    private String importstate;
    private GetDataFromServer getimagespath;
    private GetDataFromServer allseller;
    private GetDataFromServer importsell;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View homeview = View.inflate(getActivity(), R.layout.fragment_home, null);
        myviewpager = homeview.findViewById(R.id.vpager);
        pointgroup = homeview.findViewById(R.id.vpoint);
        salelist= homeview.findViewById(R.id.salelist);
        importlist= homeview.findViewById(R.id.importseller);
        searchview= homeview.findViewById(R.id.mainsearch);
        stateview=  homeview.findViewById(R.id.stateview);
        mainlayout=homeview.findViewById(R.id.mainlayout);
        importva=new ImportRecyclerAdapter();
        cust_acct= LoginUserAcct.user.getCust_acct();
        
        searchview.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                }
            }
        });

        loaddatastate.getFreshbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateview.removeAllViews();
                initDataFromServer();
            }
        });
        initDataFromServer();
        return homeview;
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            myviewpager.setCurrentItem(myviewpager.getCurrentItem() +1, true);
            mhandler.sendEmptyMessageDelayed(0, 2000);
        }

    };
    private Handler loadhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==3 && !getimagespath.getGetresult().toString().equals("")){
                logoimages= ParseJsonData.parseFromJson(getimagespath.getGetresult(),LogoImages[].class);
                initData();
                initPoint();
                myviewpager.setAdapter(new HomeFrame.MyPagerAdapter());
                myviewpager.setCurrentItem(Integer.MAX_VALUE / 2);
                mhandler.sendEmptyMessageDelayed(0, 300);
            }
            if( msg.what==1 && !allseller.getGetresult().toString().equals("")){
                shoplist= ParseJsonData.parseFromJson(allseller.getGetresult(),Seller[].class);
                sellerstate=allseller.getState();
                salelist.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                salerva=new RecyclerViewAdapter(shoplist);
                salelist.setAdapter(salerva);
                salerva.setIclistener(new ItemClickListener() {
                    @Override
                    public void itemClick(int position) {
                        Intent intent = new Intent(BaseApplication.getContext(), DetailActivity.class);
                        intent.putExtra("detailseller", shoplist.get(position));
                        intent.putExtra("cust_acct",cust_acct);
                        startActivity(intent);
                    }
                });
                SpaceItem space = new SpaceItem(16);
                salelist.addItemDecoration(space);
                }
            if(msg.what==2 && !importsell.getGetresult().toString().equals("")){
                importseller= ParseJsonData.parseFromJson(importsell.getGetresult(),Seller[].class);
                importstate=importsell.getState();
                importlist.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                importlist.setAdapter(importva);
                importva.setIclistener(new ItemClickListener() {
                    @Override
                    public void itemClick(int position) {
                        Intent intent = new Intent(BaseApplication.getContext(), DetailActivity.class);
                        intent.putExtra("detailseller", importseller.get(position));
                        intent.putExtra("cust_acct",cust_acct);
                        startActivity(intent);
                    }
                });
            }

            if(sellerstate=="success" && importstate=="success"){
                stateview.removeAllViews();
                mainlayout.setVisibility(View.VISIBLE);
            }
        }
    };

    public void initData() {
        imageviews.clear();
        if(!logoimages.isEmpty()){
        for (int i = 0; i < logoimages.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
          //  imageView.setImageResource(images[i]);
            x.image().bind(imageView,logoimages.get(i).getImg_addr(),
                    new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                            .setFailureDrawableId(R.mipmap.ic_launcher).setLoadingDrawableId(R.mipmap.ic_launcher)
                            .setUseMemCache(true).build());
            imageviews.add(imageView);
         }
        }
        myviewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                int newposition = position % imageviews.size();
                for (int i = 0; i < imageviews.size(); i++) {
                    pointarr[newposition].setImageResource(R.drawable.point1);
                    if (i != newposition) pointarr[i].setImageResource(R.drawable.point2);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }

    public void initPoint() {
        pointarr = new ImageView[imageviews.size()];
        for (int i = 0; i < imageviews.size(); i++) {
            ImageView point = new ImageView(getActivity());
            point.setImageResource(R.drawable.point2);
            pointarr[i] = point;
            pointgroup.addView(point);
        }
    }

    public void initDataFromServer() {

        getimagespath=new GetDataFromServer(loadhandler, stateview, 3);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getimagespath.getData(Global_Final.imagespath);
            }
        }).start();
        allseller = new GetDataFromServer(loadhandler, stateview, 1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                allseller.getData(Global_Final.sellerpath);
            }
        }).start();

        importsell = new GetDataFromServer(loadhandler, stateview, 2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                importsell.getData(Global_Final.importseller);
            }
        }).start();
        mainlayout.setVisibility(View.GONE);
        stateview.removeAllViews();
        RemoveParent.removeParent(loaddatastate.showLoading(true));
        stateview.addView(loaddatastate.showLoading(true));
    }
        class MyPagerAdapter extends PagerAdapter {

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                int newposition = position % imageviews.size();
                container.addView(imageviews.get(newposition));
                return imageviews.get(newposition);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                int newposition = position % imageviews.size();
                container.removeView(imageviews.get(newposition));
            }
        }
    class ImportRecyclerAdapter extends RecyclerView.Adapter<ImportRecyleViewHold>{
        private ItemClickListener iclistener;

        public void setIclistener(ItemClickListener iclistener) {
            this.iclistener = iclistener;
        }

        @Override
        public int getItemCount() {
            return importseller.size();
        }

        @Override
        public ImportRecyleViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(BaseApplication.getContext(), R.layout.importseller_home, null);
            return new ImportRecyleViewHold(view,iclistener);
        }

        @Override
        public void onBindViewHolder(ImportRecyleViewHold holder, int position) {
            holder.salename.setText(importseller.get(position).getProduct_name());
            holder.saleunit.setText(importseller.get(position).getProduct_unit());
            x.image().bind(holder.saleimage,importseller.get(position).getFactory_log(),
                    new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setFailureDrawableId(R.mipmap.ic_launcher).setLoadingDrawableId(R.mipmap.ic_launcher)
                    .setUseMemCache(true).build());
            holder.saledesc.setText(importseller.get(position).getProduct_desc());
            holder.saleprice.setText(importseller.get(position).getProduct_price() + importseller.get(position).getPrice_unit());
        }
    }

}
