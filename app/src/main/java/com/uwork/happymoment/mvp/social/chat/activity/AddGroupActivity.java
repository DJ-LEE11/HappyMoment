package com.uwork.happymoment.mvp.social.chat.activity;

import android.os.Bundle;
import android.view.View;

import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;

import java.util.List;

public class AddGroupActivity extends BaseTitleActivity {

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
        return R.layout.activity_add_group;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("选择联系人");
        setMenuClick(0, "取消", R.color.text_color_01, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, 0, "完成", R.color.text_color_yellow, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
