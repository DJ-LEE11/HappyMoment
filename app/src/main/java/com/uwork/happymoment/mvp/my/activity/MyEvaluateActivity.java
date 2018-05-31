package com.uwork.happymoment.mvp.my.activity;

import android.os.Bundle;

import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;

import java.util.List;

public class MyEvaluateActivity extends BaseTitleActivity {

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
        return R.layout.activity_my_evaluate;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("我的评价");
        setBackClick();
    }
}
