package com.hold.list;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.list.Global_Final;
import com.shop.myapplication.BaseApplication;
import com.shop.myapplication.R;
import com.utils.list.HttpPostReqData;

/**
 * Created by Administrator on 2018/12/19 0019.
 */

public class ApproveButton {
    private View approvebtn;
    private TextView allow;
    private TextView notallow;
    private Button chatbtn;
    private HttpPostReqData updatestate ;

    public ApproveButton(){approvebtn = initView();}

    public View initView(){
        View approv = View.inflate(BaseApplication.getContext(), R.layout.dsp_appv_btn, null);
        allow = approv.findViewById(R.id.allow);
        notallow = approv.findViewById(R.id.notallow);
        chatbtn = approv.findViewById(R.id.chat_btn);

        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updatestate !=null){
                    updatestate.postData02(Global_Final.updateprostate);
                    Toast.makeText(BaseApplication.getContext(),"更新成功！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        notallow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updatestate !=null){
                    updatestate.postData02(Global_Final.delproduct);
                    Toast.makeText(BaseApplication.getContext(),"更新成功！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return approv;
    }

    public void setUpdatestate(HttpPostReqData updatestate) {
        this.updatestate = updatestate;
    }

    public View getApprovebtn() {
        return approvebtn;
    }

    public Button getChatbtn() {
        return chatbtn;
    }
}
