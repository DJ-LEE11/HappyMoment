package com.uwork.happymoment.mvp.social.chat.activity;

import android.os.Bundle;
import android.view.View;

import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;

import java.util.List;

public class NewFriendActivity extends BaseTitleActivity {

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
        return R.layout.activity_new_friend;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        new InitTitle().invoke();
    }

    private class InitTitle {
        public void invoke() {
            setTitle("新的朋友");
            setBackClick();
            setRightClick(0, "添加朋友", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("添加朋友");
                }
            });
        }
    }
}
