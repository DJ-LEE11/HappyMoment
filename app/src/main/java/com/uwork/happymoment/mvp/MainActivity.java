package com.uwork.happymoment.mvp;

import android.os.Bundle;

import com.uwork.happymoment.R;
import com.uwork.librx.mvp.BaseActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_test)
    public void onViewClicked() {
        showToast("hahah");
    }
}
