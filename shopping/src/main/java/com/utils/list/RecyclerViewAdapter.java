package com.utils.list;

import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bean.list.Seller;
import com.shop.myapplication.BaseApplication;
import com.shop.myapplication.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/13 0013.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<MyRecyleViewHold> {
    private ItemClickListener iclistener;
    private List<Seller> salelist;

    public RecyclerViewAdapter(List<Seller> salelist) {
        this.salelist = salelist;
    }

    public void setIclistener(ItemClickListener iclistener) {
        this.iclistener = iclistener;
    }

    @Override
    public int getItemCount() {
        return salelist.size();
    }

    @Override
    public MyRecyleViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(BaseApplication.getContext(), R.layout.shoplist, null);
        return new MyRecyleViewHold(view ,iclistener);
    }

    @Override
    public void onBindViewHolder(MyRecyleViewHold holder, int position) {
        x.image().bind(holder.shopim, salelist.get(position).getFactory_log(),
                new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                        .setFailureDrawableId(R.mipmap.ic_launcher).setLoadingDrawableId(R.mipmap.ic_launcher)
                        .setUseMemCache(true).build());

        holder.shopaddr.setText(salelist.get(position).getFactory_addr());
        holder.shopdesc.setText(salelist.get(position).getComment());
        holder.shopname.setText(salelist.get(position).getFactory_name());
        holder.shopprice.setText(salelist.get(position).getProduct_price() + salelist.get(position).getPrice_unit());

    }

}
