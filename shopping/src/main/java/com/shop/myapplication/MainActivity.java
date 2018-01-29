package com.shop.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private RadioGroup radgroup;
    private RadioButton homeradio;
    private RadioButton shopradio;
    private RadioButton carradio;
    private RadioButton accoradio;
    private ViewPager framevp;
    private MyFragmentPagerAdapter framePadapter;
    private ArrayList<Fragment> framelist=new ArrayList();
    private String cust_acct=null;
    private Boolean iflogin=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ConnectivityManager connectservice = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectservice.getActiveNetworkInfo();
        radgroup = (RadioGroup) findViewById(R.id.radiogp);
         homeradio = (RadioButton) findViewById(R.id.homeradio);
         shopradio = (RadioButton) findViewById(R.id.shopradio);
         carradio = (RadioButton) findViewById(R.id.carradio);
        accoradio= (RadioButton) findViewById(R.id.accoradio);
        framevp = (ViewPager) findViewById(R.id.framevp);
        if(!networkinfo.isAvailable()){
            Toast.makeText(this,"网络不可用，请检查网络状态！",Toast.LENGTH_LONG).show();
        }
        buttonClick();

        framelist.add(new HomeFrame());
        framelist.add(new LimitFocus());
        framelist.add(new OrderCarFrame());
        framelist.add(new UserAcct());

        framePadapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),framelist);
        framevp.setAdapter(framePadapter);
        framevp.setOffscreenPageLimit(1);
      //  framevp.setCurrentItem(3);


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
                        if (cust_acct!=null) {
                            framevp.setCurrentItem(2);
                        }
                        break;
                    case R.id.accoradio:
                        framevp.setCurrentItem(3);
                        break;

                }

            }
        });
    }

   public void buttonClick(){
        homeradio.setOnClickListener(this);
        shopradio.setOnClickListener(this);
        carradio.setOnClickListener(this);
        accoradio.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeradio:
                radgroup.check(R.id.homeradio);
                break;
            case R.id.shopradio:
                radgroup.check(R.id.shopradio);
                break;
            case R.id.carradio:
                if (cust_acct==null){
                    JumpToActivity.jumpToLogin(this, new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct=JumpToActivity.cust_acct;
                            iflogin=true;
                            framevp.setCurrentItem(2);
                        }
                    });
                }
                if (cust_acct!=null && iflogin==true) {
                    radgroup.check(R.id.carradio);
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

    public void addFragments(Fragment newfragment){
            frames.add(newfragment);
            notifyDataSetChanged();
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
//    public void sendAcctBroadcast(){
//        Intent loginintent = new Intent();
//        loginintent.setAction("LOGIN_ACCT_ACTION");
//        loginintent.putExtra("cust_acct",cust_acct);
//        sendBroadcast(loginintent);
//    }

    public String getCust_acct() {
        return cust_acct;
    }

    public void setCust_acct(String cust_acct) {
        this.cust_acct = cust_acct;
    }
}