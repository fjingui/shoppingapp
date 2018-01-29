package com.hold.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.list.Product_Images;
import com.bean.list.Seller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shop.myapplication.BaseApplication;
import com.shop.myapplication.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/8.
 */

public class ImagesHold {

    private View imagesView;
    private RecyclerView showimages;
    private Seller seller;

    public ImagesHold() {
        imagesView=initView();
    }

    public View getImagesView() {
        return imagesView;
    }

    public View initView() {
        View view = View.inflate(BaseApplication.getContext(), R.layout.images_view, null);
        showimages= (RecyclerView) view.findViewById(R.id.showimages);
        showimages.setNestedScrollingEnabled(false);
        showimages.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext(),LinearLayoutManager.HORIZONTAL,false));
        showimages.setAdapter(new ImagesAdapter());
        return view;
    }

    public void setData(Seller data) {
        this.seller=data;
    }

    public class RecyViewHold extends RecyclerView.ViewHolder{
        ImageView rvim;

        public RecyViewHold(View itemView) {
            super(itemView);
            this.rvim = (ImageView) itemView.findViewById(R.id.recimagehold);
        }
    }

    public class  ImagesAdapter  extends RecyclerView.Adapter<RecyViewHold> {
        @Override
        public int getItemCount() {
            return seller.getPro_imgs().size();
        }

        @Override
        public RecyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = View.inflate(parent.getContext(), R.layout.recyler_hold, null);
            return new RecyViewHold(inflate);
        }

        @Override
        public void onBindViewHolder(RecyViewHold holder, int position) {
            x.image().bind(holder.rvim,seller.getPro_imgs().get(position).getPro_Img_Addr(),
                    new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                            .setFailureDrawableId(R.mipmap.ic_launcher).setLoadingDrawableId(R.mipmap.ic_launcher)
                            .setUseMemCache(true).build());

        }
    }

}
