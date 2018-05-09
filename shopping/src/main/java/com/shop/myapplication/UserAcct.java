package com.shop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.list.CustInfo;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class UserAcct extends Fragment {

    private Button allorders;
    private Button orderaddrmanage;
    private Button headerbtn;
    private Button dfkbtn;
    private String cust_acct;
    private Button backlogin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View accview = View.inflate(getContext(), R.layout.fragment_useracct, null);
        dfkbtn= (Button) accview.findViewById(R.id.btn_dfk);
        allorders = (Button) accview.findViewById(R.id.allorder);
        backlogin= (Button) accview.findViewById(R.id.backlogin);
        orderaddrmanage= (Button) accview.findViewById(R.id.orderaddr_manage);
        headerbtn = (Button) accview.findViewById(R.id.headerbtn);
        if(cust_acct==null) {
            cust_acct = ((MainActivity) getActivity()).getCust_acct();
        }
        if(cust_acct!=null){
            headerbtn.setText(cust_acct);
        }
        dfkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cust_acct ==null ){
                    JumpToActivity.jumpToLogin(getActivity(), new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct=JumpToActivity.cust_acct;
                            headerbtn.setText(cust_acct);
                            ((MainActivity)getActivity()).setCust_acct(cust_acct);
                            Intent intent = new Intent(getContext(), DpayActivity.class);
                            intent.putExtra("cust_acct", cust_acct);
                            startActivity(intent);
                        }
                    });
                }else {
                    Intent intent = new Intent(getContext(), DpayActivity.class);
                    intent.putExtra("cust_acct", cust_acct);
                    startActivity(intent);
                }
            }
        });
        headerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cust_acct == null) {
                    JumpToActivity.jumpToLogin(getActivity(), new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct = JumpToActivity.cust_acct;
                            ((MainActivity) getActivity()).setCust_acct(cust_acct);
                            headerbtn.setText(cust_acct);
                        }
                    });
                }
            }
        });
        allorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cust_acct ==null ){
                    JumpToActivity.jumpToLogin(getActivity(), new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct=JumpToActivity.cust_acct;
                            headerbtn.setText(cust_acct);
                            ((MainActivity)getActivity()).setCust_acct(cust_acct);
                            Intent intent = new Intent(getContext(), MyAllOrders.class);
                            intent.putExtra("cust_acct", cust_acct);
                            startActivity(intent);
                        }
                    });
                }else {
                    Intent intent = new Intent(getContext(), MyAllOrders.class);
                    intent.putExtra("cust_acct", cust_acct);
                    startActivity(intent);
                }
            }
        });
        orderaddrmanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cust_acct ==null ){
                    JumpToActivity.jumpToLogin(getActivity(), new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct=JumpToActivity.cust_acct;
                            ((MainActivity)getActivity()).setCust_acct(cust_acct);
                            Intent intent = new Intent(getContext(), OrderAddrList.class);
                            intent.putExtra("cust_acct", cust_acct);
                            startActivity(intent);
                        }
                    });
                }else {
                    Intent intent = new Intent(getContext(), OrderAddrList.class);
                    intent.putExtra("cust_acct", cust_acct);
                    startActivity(intent);
                }
            }
        });
        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cust_acct="";
                ((MainActivity) getActivity()).setCust_acct("");
                headerbtn.setText(R.string.loginbtn);
            }
        });
        return accview;
    }

}
