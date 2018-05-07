package com.uwork.happymoment.mvp;

import android.os.Bundle;

import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;

import java.util.List;

import butterknife.OnClick;

public class MainActivity extends BaseTitleActivity {

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("首页");
        setBackClick();
    }


    @OnClick(R.id.btn_test)
    public void onViewClicked() {
        showToast("hahah");
    }
}
