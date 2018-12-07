package com.shop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.list.Factory;
import com.bean.list.Global_Final;
import com.bean.list.Product;
import com.bean.list.Product_Images;
import com.hold.list.LoadStateView;
import com.utils.list.GetDataFromServer;
import com.utils.list.HttpPostReqData;
import com.utils.list.LoginUserAcct;
import com.utils.list.ParseJsonData;
import com.utils.list.RemoveParent;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

@ContentView(R.layout.fragment_sell_thing)
public class SellThing extends Fragment implements View.OnClickListener {

    private static final int REQUEST_CODE = 123;
    private ArrayList<String> mResults = new ArrayList<>();
    @ViewInject(R.id.submitbtn)
    private Button submitbtn;
    @ViewInject(R.id.selectedimages)
    private TextView seledimages;
    @ViewInject(R.id.seller)
    private EditText seller;
    @ViewInject(R.id.selladdr)
    private EditText selladdr;
    @ViewInject(R.id.sellproduct)
    private EditText sellproduct;
    @ViewInject(R.id.sellformat)
    private EditText sellformat;
    @ViewInject(R.id.sellprice)
    private EditText sellprice;
    @ViewInject(R.id.prostor)
    private EditText prostor;
    @ViewInject(R.id.sellamount)
    private EditText sellamount;
    @ViewInject(R.id.sellunit)
    private EditText sellunit;
    @ViewInject(R.id.selldesc)
    private EditText selldesc;
    @ViewInject(R.id.sellnbr)
    private EditText sellnbr;
    @ViewInject(R.id.upstateview)
    private FrameLayout upstateflout;
    @ViewInject(R.id.salelayout)
    private View salelayoutview;
    private LoadStateView upstateview = new LoadStateView();
    private static final int UPFAC = 00;
    private static final int INITTFAC = 10;
    private static final int GETFAC = 11;
    private static final int GETPRO = 12;
    private static final int GETPRO_2 = 13;
    private static final int UPEDFAC = 20;
    private static final int UPEDPRO = 21;
    private static final int UPEDPROURI = 22;
    private static final int UPEDIMGS = 23;
    private int lastresult = 0;
    private int upstep = 0;
    private String cust_acct;
    private Factory factory = new Factory();
    private Product product = new Product();
    private Product_Images pro_imgs = new Product_Images();
    private String facjson;
    private String projson;
    private String proimgsjson;
    private String fmtdate;
    private GetDataFromServer getfacdata;
    private GetDataFromServer getprodata;
    private GetDataFromServer delfailpro;
    private GetDataFromServer delfailproimg;
    private View errorView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fmtdate = sdf.format(dt);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (LoginUserAcct.user.getCust_acct() != null) {
            cust_acct = LoginUserAcct.user.getCust_acct();
            getFacData(INITTFAC);
        }
        seledimages.setOnClickListener(this);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            //                else if(mResults.isEmpty()) {
//                    Toast.makeText(getActivity(), "请上传或拍照图片！", Toast.LENGTH_LONG).show();
//                }
            @Override
            public void onClick(View v) {
                lastresult = 0;
                if (cust_acct == null) {
                    JumpToActivity.jumpToLogin(getActivity(), new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct = JumpToActivity.cust_acct;
                        }
                    });
                } else if (mResults.size() == 0) {
                    seledimages.setError("请上传产品图片！");
                    errorView = seller;
                    errorView.requestFocus();
                } else if (seller.getText().toString().equals("")) {
                    seller.setError("称呼不能为空！");
                    errorView = seller;
                    errorView.requestFocus();
                } else if (selladdr.getText().toString().equals("")) {
                    selladdr.setError("销售地址不能为空！");
                    errorView = seller;
                    errorView.requestFocus();
                } else if (sellproduct.getText().toString().equals("")) {
                    sellproduct.setError("销售品不能为空！");
                    errorView = seller;
                    errorView.requestFocus();
                } else if (sellformat.getText().toString().equals("")) {
                    sellformat.setError("销售规格不能为空！");
                    errorView = seller;
                    errorView.requestFocus();
                } else if (sellprice.getText().toString().equals("")) {
                    sellprice.setError("销售价格不能为空！");
                    errorView = seller;
                    errorView.requestFocus();
                } else if (sellamount.getText().toString().equals("")) {
                    sellamount.setError("单价数量不能为空！");
                    errorView = seller;
                    errorView.requestFocus();
                } else if (sellunit.getText().toString().equals("")) {
                    sellunit.setError("数量单位不能为空！");
                    errorView = seller;
                    errorView.requestFocus();
                } else if (selldesc.getText().toString().equals("")) {
                    selldesc.setError("销售品描述不能为空！");
                    errorView = seller;
                    errorView.requestFocus();
                } else if (sellnbr.getText().toString().equals("")) {
                    sellnbr.setError("联系电话不能为空！");
                    errorView = seller;
                    errorView.requestFocus();
                } else {
                    if (TextUtils.isEmpty(factory.getCust_acct())) {
                        upFacData();
                    } else {
                        getProData(GETPRO);
                    }
                    salelayoutview.setVisibility(View.GONE);
                    upstateflout.removeAllViews();
                    RemoveParent.removeParent(upstateview.showLoading(true));
                    upstateflout.addView(upstateview.showLoading(true));
                    upstateflout.setVisibility(View.VISIBLE);
                }
            }
        });
        upstateview.getFreshbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upstep == 2 || upstep == 3) {
                    delPro();
                    upstateflout.removeAllViews();
                    upstateflout.setVisibility(View.GONE);
                    salelayoutview.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    private Handler parseupdatahandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == INITTFAC) {
                if (!getfacdata.getGetresult().equals("")) {
                    factory = ParseJsonData.parseObjectJson(getfacdata.getGetresult(), Factory.class);
                    if (!TextUtils.isEmpty(factory.getCust_acct())) {
                        if (seller.getText().toString().equals("")) {
                            seller.setText(factory.getFactory_name());
                        }
                        if (selladdr.getText().toString().equals("")) {
                            selladdr.setText(factory.getFactory_addr());
                        }
                        if (sellnbr.getText().toString().equals("")) {
                            sellnbr.setText(factory.getFac_contact_nbr());
                        }
                    }
                }
            }else if (msg.what == UPEDFAC) {
                upstep = 1;
                getFacData(GETFAC);
            } else if (msg.what == GETFAC) {
                factory = ParseJsonData.parseObjectJson(getfacdata.getGetresult(), Factory.class);
                getProData(GETPRO);
            } else if (msg.what == GETPRO) {
                product = ParseJsonData.parseObjectJson(getprodata.getGetresult(), Product.class);
                if (product.getProduct_id() > 0) {
                    Toast.makeText(getActivity(), "您已存在同样产品，勿重复上传！", Toast.LENGTH_LONG).show();
                    salelayoutview.setVisibility(View.VISIBLE);
                    upstateflout.removeAllViews();
                    upstateflout.setVisibility(View.GONE);
                } else {
                    upProData();
                    upstep = 2;
                }
            } else if (msg.what == UPEDPRO) {
                getProData(GETPRO_2);
            } else if (msg.what == GETPRO_2) {
                product = ParseJsonData.parseObjectJson(getprodata.getGetresult(), Product.class);
                for (int i = 0; i < mResults.size(); i++) {
                    setProImgUri(Global_Final.setproimguri + fmtdate + "/" + cust_acct + "-" + i + ".jpg");
                }
            } else if (msg.what == UPEDPROURI) {
                upstep = 3;
                saveImgsTo(Global_Final.saveproimgs);

            } else if (msg.what == UPEDIMGS) {
                upstep = 4;
                salelayoutview.setVisibility(View.VISIBLE);
                upstateflout.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), PublishResult.class);
                startActivity(intent);
            } else {
                upstateview.getFreshbtn().setText("返回");
                upstateview.getShowinfo().setText("抱歉，数据上传失败，点击返回重试");
                salelayoutview.setVisibility(View.GONE);
                upstateflout.removeAllViews();
                RemoveParent.removeParent(upstateview.showLoadFail(true));
                upstateflout.addView(upstateview.showLoadFail(true));
                upstateflout.setVisibility(View.VISIBLE);
            }

        }
    };

    public void getFacData(int what) {
        getfacdata = new GetDataFromServer(parseupdatahandler, upstateflout, what);
        getfacdata.setParam(cust_acct);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getfacdata.getData(Global_Final.getfactory);
            }
        }).start();

    }

    public void getProData(int what) {
        getprodata = new GetDataFromServer(parseupdatahandler, upstateflout, what);
        getprodata.setParam3(sellproduct.getText().toString());
        getprodata.setParam4(String.valueOf(factory.getFactory_id()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                getprodata.getData(Global_Final.getproduct);
            }
        }).start();
    }

    public void parseSellerData() {
        factory.setCust_acct(cust_acct);
        factory.setFactory_name(seller.getText().toString());
        factory.setFactory_addr(selladdr.getText().toString());
        factory.setComment(selldesc.getText().toString());
        factory.setFac_contact_nbr(sellnbr.getText().toString());
        factory.setFactory_log(Global_Final.setproimguri + fmtdate + "/" + cust_acct + "-" +0+".jpg"); //获取第一个图像 mresult.get(0)
        facjson = ParseJsonData.parseToJson(factory);
    }

    public void parseProData() {
        product.setFactory_id(factory.getFactory_id());
        product.setProduct_name(sellproduct.getText().toString());
        product.setProduct_price(Float.parseFloat(sellprice.getText().toString()));
        if(!TextUtils.isEmpty(prostor.getText().toString())){
            product.setProduct_stor(Integer.parseInt(prostor.getText().toString()) );
        }else{
            product.setProduct_stor(1);
        }
        if (Integer.parseInt(sellamount.getText().toString()) == 1) {
            product.setPrice_unit("元/" + sellunit.getText().toString());
        } else {
            product.setPrice_unit("元/" + sellamount.getText().toString() + sellunit.getText().toString());
        }
        product.setProduct_unit(sellformat.getText().toString());
        product.setProduct_desc(selldesc.getText().toString());
        product.setSale_state("待售");
        projson = ParseJsonData.parseToJson(product);
    }

    public void parsePorImg(String imgpath) {
        pro_imgs.setProduct_id(product.getProduct_id());
        pro_imgs.setPro_img_addr(imgpath); //设置上传后图片路径
        proimgsjson = ParseJsonData.parseToJson(pro_imgs);
    }

    public void upFacData() {
        parseSellerData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new HttpPostReqData(parseupdatahandler, UPEDFAC).PostData(Global_Final.insertfactory, facjson);
            }
        }).start();
    }

    public void upProData() {
        parseProData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new HttpPostReqData(parseupdatahandler, UPEDPRO).PostData(Global_Final.insertproduct, projson);
            }
        }).start();
    }

    public void setProImgUri(String imgpath) {
        parsePorImg(imgpath);
        new Thread(new Runnable() {
            @Override
            public void run() {
                new HttpPostReqData(parseupdatahandler, UPEDPROURI).PostData(Global_Final.insertproimg, proimgsjson);
            }
        }).start();
    }

    public void delPro() {
        delfailpro = new GetDataFromServer();
        delfailpro.setParam5(product.getProduct_id());
        new Thread(new Runnable() {
            @Override
            public void run() {
                delfailpro.delData(Global_Final.delproduct);
            }
        }).start();
    }

    public void delProImg() {
        delfailproimg = new GetDataFromServer();
        delfailproimg.setParam5(product.getProduct_id());
        new Thread(new Runnable() {
            @Override
            public void run() {
                delfailproimg.delData(Global_Final.delproimg);
            }
        }).start();
    }

    public void saveImgsTo(String url) {
        RequestParams params = new RequestParams(url);
        List<KeyValue> list = new ArrayList<KeyValue>();
        for (int i = 0; i < mResults.size(); i++) {
            list.add(new KeyValue("file", new File(mResults.get(i))));
        }
        MultipartBody body = new MultipartBody(list, "UTF-8");
        params.setRequestBody(body);
        params.addQueryStringParameter("cust_acct", cust_acct);
        params.addQueryStringParameter("product_id", product.getProduct_id() + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                lastresult += 1;
                if(lastresult == mResults.size()){
                    parseupdatahandler.sendEmptyMessage(UPEDIMGS);
                }
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }
        });
    }

    @Override
    public void onClick(View v) {

// start multiple photos selector
        Intent intent = new Intent(getContext(), ImagesSelectorActivity.class);
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5);
// min size of image which will be shown; to filter tiny images (mainly icons)
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
// pass current selected images as the initial value
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // get selected images from selector
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;
                StringBuffer sb = new StringBuffer();
                sb.append(String.format("共 %d 张照片：", mResults.size())).append("\n");
                for (String result : mResults) {
                    sb.append(result).append("\n");
                }
                seledimages.setText(sb.toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
