package com.shop.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bean.list.Global_Final;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.utils.list.GeneOrderId;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class RegesterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText account;
    private EditText username;
    private EditText passw;
    private EditText repassw;
    private Button register;
    int myregresult =0;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        account = (EditText) findViewById(R.id.account);
        username = (EditText) findViewById(R.id.username);
        passw = (EditText) findViewById(R.id.passw);
        repassw = (EditText) findViewById(R.id.repassw);
        register = (Button) findViewById(R.id.registerbtn);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String reg_account = account.getText().toString().trim();
        String reg_username = username.getText().toString().trim();
        String init_pass = passw.getText().toString().trim();
        String init_repass = repassw.getText().toString().trim();
        String acctnbr ="";
        if(!TextUtils.isEmpty(reg_account)) {
             acctnbr = GeneOrderId.getAcctNbr(reg_account);
        }
        View errorView = null;

        if (TextUtils.isEmpty(reg_account)){
            account.setError("账号不能为空");
            errorView = account;
            errorView.requestFocus();
          //  Toast.makeText(RegesterActivity.this, "账号不能为空", Toast.LENGTH_LONG).show();
        }else if (reg_account.length()!=11 ){
            account.setError("不存在的手机号");
            errorView = account;
            errorView.requestFocus();
           // Toast.makeText(RegesterActivity.this, "不存在的手机号", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(reg_username)){
            username.setError("用户名不能为空");
            errorView = account;
            errorView.requestFocus();
           // Toast.makeText(RegesterActivity.this, "用户名不能为空", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(init_pass)){
            passw.setError("请输入密码");
            errorView = account;
            errorView.requestFocus();
          //  Toast.makeText(RegesterActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(init_repass)){
            repassw.setError("请输入确认密码");
            errorView = account;
            errorView.requestFocus();
          //  Toast.makeText(RegesterActivity.this, "请输入确认密码", Toast.LENGTH_LONG).show();
        }else if (!TextUtils.equals(init_pass,init_repass)){
            repassw.setError("密码输入不一致");
            errorView = account;
            errorView.requestFocus();
           // Toast.makeText(RegesterActivity.this, "密码输入不一致", Toast.LENGTH_LONG).show();
        }else{
            registerUserAccount(reg_account,reg_username,init_pass,acctnbr);
            if(myregresult>0){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMChatManager.getInstance().createAccountOnServer(account.getText().toString().trim(),"123456");
                        } catch (EaseMobException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            }
        }

    public void registerUserAccount(String acc,String user,String pass,String accnbr){
        RequestParams registe = new RequestParams(Global_Final.register);
        registe.addQueryStringParameter("cust_acct",acc);
        registe.addQueryStringParameter("acct_name",user);
        registe.addQueryStringParameter("passw",pass);
        registe.addQueryStringParameter("acct_nbr",accnbr);
        registe.addQueryStringParameter("status","在用");
        x.http().post(registe, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                myregresult = Integer.parseInt(result) ;
                if(myregresult>0) {
                    Toast.makeText(RegesterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(RegesterActivity.this, "账户已被注册！请更换号码。", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
}
