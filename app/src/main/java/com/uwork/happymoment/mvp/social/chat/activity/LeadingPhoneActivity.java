package com.uwork.happymoment.mvp.social.chat.activity;

import android.os.Bundle;
import android.view.View;

import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;

import java.util.List;

//导入手机通讯录
public class LeadingPhoneActivity extends BaseTitleActivity {

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
        return R.layout.activity_leading_phone;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initTitle();
    }

    private void initTitle() {
        setTitle("手机通讯录");
        setLeftClick(0, "关闭", R.color.text_color_red, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
