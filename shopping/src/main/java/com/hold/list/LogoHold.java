package com.hold.list;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.list.Seller;
import com.bean.list.ShopList;
import com.shop.myapplication.BaseApplication;
import com.shop.myapplication.R;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/7/3.
 */

public class LogoHold  {

    private View logoview;
    private ImageView logoiv;
    private TextView logotxt;

    public LogoHold() {
        logoview=initView();
    }

    public View initView() {
        View view = View.inflate(BaseApplication.getContext(), R.layout.logo_view, null);
        logoiv = (ImageView) view.findViewById(R.id.logoimage);
        logotxt = (TextView) view.findViewById(R.id.logotext);
        return view;
}

public void setData(Seller inf) {
        x.image().bind(logoiv, inf.getFactory_log() ,
        new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_CROP)
        .setFailureDrawableId(R.mipmap.ic_launcher).setLoadingDrawableId(R.mipmap.ic_launcher)
        .setUseMemCache(true).build());
        logotxt.setText(inf.getComment());
        }

public View getLogoview() {
        return logoview;
        }
        }
