package com.hold.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.list.Seller;
import com.shop.myapplication.BaseApplication;
import com.shop.myapplication.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by Administrator on 2017/7/6.
 */

public class DetailTextHold {

    private View detailtextview;

    private TextView descdetailtv;
    private TextView descaddr;

    public DetailTextHold() {
        detailtextview=initView();
    }

    public View initView() {
        View view = View.inflate(BaseApplication.getContext(), R.layout.detailtext_view, null);

        descdetailtv= (TextView) view.findViewById(R.id.descdetailtv);
        descaddr= (TextView) view.findViewById(R.id.descaddrtv);

        return view;
    }

    public void setData(Seller inf) {

        descdetailtv.setText(inf.getProduct_desc());
        descaddr.setText(inf.getFactory_addr());

    }
    public View getDetailtextview() {
        return detailtextview;
    }

}
