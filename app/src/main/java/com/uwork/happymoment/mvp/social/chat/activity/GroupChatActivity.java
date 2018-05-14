package com.uwork.happymoment.mvp.social.chat.activity;

import android.os.Bundle;

import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;

import java.util.List;

import butterknife.OnClick;
//群聊
public class GroupChatActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_group_chat;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("群聊");
        setBackClick();
    }

    @OnClick(R.id.llAddGroup)
    public void onViewClicked() {
        goTo(AddGroupActivity.class);
    }
}
