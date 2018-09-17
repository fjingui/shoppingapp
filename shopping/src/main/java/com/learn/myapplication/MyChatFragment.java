package com.learn.myapplication;

import android.content.Context;
import android.view.View;

import com.easemob.chat.EMMessage;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.controller.EaseUI;
import com.easemob.easeui.ui.EaseChatFragment;
import com.easemob.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.utils.list.LoginUserAcct;

/**
 * Created by Administrator on 2018/9/14 0014.
 */

public class MyChatFragment extends EaseChatFragment {

    private EaseChatFragmentHelper ecfh = new EaseChatFragmentHelper() {
        @Override
        public void onSetMessageAttributes(EMMessage message) {
            //设置要发送扩展消息用户昵称
            message.setAttribute("USER_NICK", LoginUserAcct.user.getAcct_name());

            //设置要发送扩展消息用户头像
         //   message.setAttribute(Constant.ChatUserPic, SpUtils.getInstance().getString("userAvatar",""));
        }
        @Override
        public void onEnterToChatDetails() {
        }

        @Override
        public void onAvatarClick(String username) {
        }

        @Override
        public boolean onMessageBubbleClick(EMMessage message) {
            return false;
        }

        @Override
        public void onMessageBubbleLongClick(EMMessage message) {
        }

        @Override
        public boolean onExtendMenuItemClick(int itemId, View view) {
            return false;
        }

        @Override
        public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
            return null;
        }
    };

    @Override
    public void setChatFragmentHelper(EaseChatFragmentHelper chatFragmentHelper) {
        super.setChatFragmentHelper(ecfh);
    }


}

