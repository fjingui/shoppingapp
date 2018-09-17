package com.shop.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easemob.chat.EMConversation;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.ui.EaseConversationListFragment;

public class ChatListFrame extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getActivity(), R.layout.chatlistframe, null);
        EaseConversationListFragment mychatlist = new EaseConversationListFragment();
        mychatlist.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent = new Intent(getActivity(), SaleChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName());
                startActivity(intent);
            }
        });
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.chatlist, mychatlist).commit();

    return inflate;
}
}
