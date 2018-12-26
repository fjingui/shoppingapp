package com.shop.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bean.list.ManageAcct;
import com.easemob.chat.EMChatManager;
import com.utils.list.LoginUserAcct;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class UserAcctFragment extends Fragment {

    private Button allorders;
    private Button sale_thing;
    private Button sale_acct;
    private Button orderaddrmanage;
    private Button headerbtn;
    private Button dfkbtn;
    private Button receivebtn;
    private Button gettedbtn;
    private String cust_acct;
    private Button backlogin;
    private Button shopping_about;
    private Button dspthing;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View accview = View.inflate(getContext(), R.layout.fragment_useracct, null);
        dfkbtn = accview.findViewById(R.id.btn_dfk);
        receivebtn = accview.findViewById(R.id.btn_receive);
        gettedbtn = accview.findViewById(R.id.btn_getted);
        allorders = accview.findViewById(R.id.allorder);
        backlogin = accview.findViewById(R.id.backlogin);
        orderaddrmanage = accview.findViewById(R.id.orderaddr_manage);
        headerbtn = accview.findViewById(R.id.headerbtn);
        sale_thing = accview.findViewById(R.id.sale_thing);
        sale_acct = accview.findViewById(R.id.sale_acct);
        shopping_about = accview.findViewById(R.id.shopping_about);
        dspthing = accview.findViewById(R.id.dspthing);
        if( TextUtils.equals(LoginUserAcct.user.getCust_acct(), ManageAcct.mainacct)  ){
            dspthing.setVisibility(View.VISIBLE);
        }
        if (cust_acct == null) {
            cust_acct = LoginUserAcct.user.getCust_acct();
        }
        if(TextUtils.isEmpty(LoginUserAcct.user.getAcct_name())){
            headerbtn.setText("点击登录");
        }else{
            headerbtn.setText(LoginUserAcct.user.getAcct_name());
        }


        dfkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cust_acct == null) {
                    JumpToActivity.jumpToLogin(getActivity(), new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct = LoginUserAcct.user.getCust_acct();
                            headerbtn.setText(LoginUserAcct.user.getAcct_name());
                            Intent intent = new Intent(getContext(), DpayActivity.class);
                            intent.putExtra("cust_acct", cust_acct);
                            startActivity(intent);
                        }
                    });
                } else {
                    Intent intent = new Intent(getContext(), DpayActivity.class);
                    intent.putExtra("cust_acct", cust_acct);
                    startActivity(intent);
                }
            }
        });
        receivebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cust_acct == null) {
                    JumpToActivity.jumpToLogin(getActivity(), new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct = LoginUserAcct.user.getCust_acct();
                            headerbtn.setText(LoginUserAcct.user.getAcct_name());
                            Intent intent = new Intent(getContext(), DreceiveActivity.class);
                            intent.putExtra("cust_acct", cust_acct);
                            startActivity(intent);
                        }
                    });
                } else {
                    Intent intent = new Intent(getContext(), DreceiveActivity.class);
                    intent.putExtra("cust_acct", cust_acct);
                    startActivity(intent);
                }
            }
        });
        gettedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cust_acct == null) {
                    JumpToActivity.jumpToLogin(getActivity(), new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct = JumpToActivity.cust_acct;
                            headerbtn.setText(LoginUserAcct.user.getAcct_name());
                            Intent intent = new Intent(getContext(), GettedActivity.class);
                            intent.putExtra("cust_acct", cust_acct);
                            startActivity(intent);
                        }
                    });
                } else {
                    Intent intent = new Intent(getContext(), GettedActivity.class);
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
                            headerbtn.setText(LoginUserAcct.user.getAcct_name());
                        }
                    });
                }
            }
        });
        allorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cust_acct == null) {
                    JumpToActivity.jumpToLogin(getActivity(), new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct = JumpToActivity.cust_acct;
                            headerbtn.setText(LoginUserAcct.user.getAcct_name());
                            Intent intent = new Intent(getContext(), MyAllOrders.class);
                            intent.putExtra("cust_acct", cust_acct);
                            startActivity(intent);
                        }
                    });
                } else {
                    Intent intent = new Intent(getContext(), MyAllOrders.class);
                    intent.putExtra("cust_acct", cust_acct);
                    startActivity(intent);
                }
            }
        });
        orderaddrmanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cust_acct == null) {
                    JumpToActivity.jumpToLogin(getActivity(), new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct = JumpToActivity.cust_acct;
                            headerbtn.setText(LoginUserAcct.user.getAcct_name());
                            Intent intent = new Intent(getContext(), OrderAddrList.class);
                            intent.putExtra("cust_acct", cust_acct);
                            startActivity(intent);
                        }
                    });
                } else {
                    Intent intent = new Intent(getContext(), OrderAddrList.class);
                    intent.putExtra("cust_acct", cust_acct);
                    startActivity(intent);
                }
            }
        });
        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cust_acct = "";
                LoginUserAcct.user=null;
                EMChatManager.getInstance().logout();
                headerbtn.setText(R.string.loginbtn);
                getActivity().finish();

            }
        });
        sale_acct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cust_acct ==null ){
                    JumpToActivity.jumpToLogin(getActivity(), new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct=JumpToActivity.cust_acct;
                            headerbtn.setText(LoginUserAcct.user.getAcct_name());
                            Intent intent = new Intent(getContext(), SaleAcctDetailActivity.class);
                            intent.putExtra("cust_acct", cust_acct);
                            startActivity(intent);
                        }
                    });
                }else {
                    Intent intent = new Intent(getContext(), SaleAcctDetailActivity.class);
                    intent.putExtra("cust_acct", cust_acct);
                    startActivity(intent);
                }
            }
        });
        sale_thing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cust_acct ==null ){
                    JumpToActivity.jumpToLogin(getActivity(), new JumpToActivity.LoginCallback() {
                        @Override
                        public void onlogin() {
                            cust_acct=JumpToActivity.cust_acct;
                            headerbtn.setText(LoginUserAcct.user.getAcct_name());
                            Intent intent = new Intent(getContext(), MySaledThing.class);
                            intent.putExtra("cust_acct", cust_acct);
                            startActivity(intent);
                        }
                    });
                }else {
                    Intent intent = new Intent(getContext(), MySaledThing.class);
                    intent.putExtra("cust_acct", cust_acct);
                    startActivity(intent);
                }
            }
        });
        shopping_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShoppingAbout.class);
                startActivity(intent);
            }
        });
        dspthing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ApproveGoods.class);
                startActivity(intent);
            }
        });
        return accview;
    }
}
