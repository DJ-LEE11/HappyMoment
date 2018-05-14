package com.uwork.happymoment.mvp.social.chat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.uwork.happymoment.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//聊天页面
public class ConversationActivity extends AppCompatActivity {


    @BindView(R.id.tvTitle)
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        String id = getIntent().getData().getQueryParameter("targetId");
        String sName = getIntent().getData().getQueryParameter("title");
        if (!TextUtils.isEmpty(sName)) {
            mTvTitle.setText(sName);
        } else {
            //ToDo 通过Id进行请求获取名字
            mTvTitle.setText(id);
        }
    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }
}
