package com.shop.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bean.list.ApkVersion;
import com.bean.list.CoverImgs;
import com.bean.list.Global_Final;
import com.learn.myapplication.NoScrollViewPager;
import com.learn.myapplication.PullDoorView;
import com.utils.list.HttpPostReqData;
import com.utils.list.LoginUserAcct;
import com.utils.list.ParseJsonData;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import util.UpdateAppUtils;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private View mainview;
    private PullDoorView pdoorview;
    private CoverImgs coverimg;
    private ApkVersion apkversion;
    private ImageView pulldoorimg;
    private RadioGroup radgroup;
    private RadioButton homeradio;
    private RadioButton shopradio;
    private RadioButton carradio;
    private RadioButton accoradio;
    private RadioButton chatradio;
    private NoScrollViewPager framevp;
    private MyFragmentPagerAdapter framePadapter;
    private ArrayList<Fragment> framelist=new ArrayList();
    public static String cust_acct=null;
    private HttpPostReqData hreqdata;
    private HttpPostReqData initapkver;
    private Handler initdata = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0001){
                coverimg = ParseJsonData.parseObjectJson(hreqdata.getResultdata(),CoverImgs.class);
                x.image().bind(pulldoorimg,coverimg.getCover_path(),
                        new ImageOptions.Builder().setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                                .setFailureDrawableId(R.mipmap.ic_launcher).setLoadingDrawableId(R.mipmap.ic_launcher)
                                .setUseMemCache(true).build());
                pdoorview.bringToFront();

            }
            if(msg.what == 0002){
                apkversion = ParseJsonData.parseObjectJson(initapkver.getResultdata(),ApkVersion.class);
                UpdateAppUtils.from(MainActivity.this)
                        .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME) //更新检测方式，默认为VersionCode
                        .serverVersionCode(apkversion.getVersion_code())
                        .serverVersionName(apkversion.getVersion_name())
                        .apkPath(apkversion.getApk_path())
                        .update();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ConnectivityManager connectservice = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectservice.getActiveNetworkInfo();
        cust_acct = LoginUserAcct.user.getCust_acct();
        pdoorview = findViewById(R.id.welcomeimage);
        pulldoorimg = findViewById(R.id.pulldoorimg);
        mainview=findViewById(R.id.activity_main);
        radgroup = findViewById(R.id.radiogp);
         homeradio = findViewById(R.id.homeradio);
         shopradio = findViewById(R.id.shopradio);
         carradio = findViewById(R.id.carradio);
         chatradio = findViewById(R.id.chatlist);
        accoradio= findViewById(R.id.accoradio);
        framevp = findViewById(R.id.framevp);
        if(!networkinfo.isAvailable() ){
            Toast.makeText(this,"网络不可用，请检查网络状态！",Toast.LENGTH_LONG).show();
        }
        initCover();

        buttonClick();
        framelist.add(new HomeFrame());
        framelist.add(new SellThing());
        framelist.add(new OrderCarFrame());
        framelist.add(new ChatListFrame());
        framelist.add(new UserAcctFragment());

        framePadapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),framelist);
        framevp.setAdapter(framePadapter);
        framevp.setOffscreenPageLimit(1);

        radgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.homeradio:
                        framevp.setCurrentItem(0);
                        break;
                    case R.id.shopradio:
                        framevp.setCurrentItem(1);
                        break;
                    case R.id.carradio:
                        framevp.setCurrentItem(2);
                        break;
                    case R.id.chatlist:
                        framevp.setCurrentItem(3);
                        break;
                    case R.id.accoradio:
                        framevp.setCurrentItem(4);
                        break;

                }

            }
        });
       // pdoorview.setBgImage(R.mipmap.smallruralcover);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainview.setFocusable(true);
        mainview.setFocusableInTouchMode(true);
        mainview.requestFocus();
    }

    public void initCover(){
        hreqdata = new HttpPostReqData(initdata,0001);
        initapkver = new HttpPostReqData(initdata,0002);
        hreqdata.requestData(Global_Final.getcoverimg);
        initapkver.requestData(Global_Final.apkversion);
    }
    public void buttonClick(){
        homeradio.setOnClickListener(this);
        shopradio.setOnClickListener(this);
        carradio.setOnClickListener(this);
        accoradio.setOnClickListener(this);
        chatradio.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeradio:
                radgroup.check(R.id.homeradio);
                break;
            case R.id.shopradio:
                if (cust_acct==null){
                    JumpToActivity.jumpToLogin(this, new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct=JumpToActivity.cust_acct;
                            framevp.setCurrentItem(0);
                        }
                    });
                }else {
                    radgroup.check(R.id.shopradio);
                }
                break;
            case R.id.carradio:
                if (cust_acct==null){
                    JumpToActivity.jumpToLogin(this, new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct=JumpToActivity.cust_acct;
                            framevp.setCurrentItem(0);
                        }
                    });
                }else {
                    radgroup.check(R.id.carradio);
                }
                break;
            case R.id.chatlist:
                if (cust_acct==null){
                    JumpToActivity.jumpToLogin(this, new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct=JumpToActivity.cust_acct;
                            framevp.setCurrentItem(0);
                        }
                    });
                }else {
                    radgroup.check(R.id.chatlist);
                }
                break;
            case R.id.accoradio:
                radgroup.check(R.id.accoradio);
                break;

        }
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> frames;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> frames) {
            super(fm);
            this.frames = frames;
        }

    @Override
    public long getItemId(int position) {
        return frames.get(position).hashCode();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return frames.get(position);
        }
        @Override
        public int getCount() {
            return frames.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }

}