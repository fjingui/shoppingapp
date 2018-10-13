package com.shop.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.learn.myapplication.PullDoorView;

public class WelcomePage extends AppCompatActivity {

    private PullDoorView pdoorview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        getSupportActionBar().hide();
        pdoorview = (PullDoorView) findViewById(R.id.activity_welcome_page);
        pdoorview.setBgImage(R.mipmap.smallruralcover);
    }
    public void startAnimation(){

    }
}
