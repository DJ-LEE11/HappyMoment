package com.uwork.happymoment.mvp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.login.activity.LoginActivity;
import com.uwork.librx.mvp.BaseActivity;

import java.util.List;

public class SplashActivity extends BaseActivity {

    private static final int GOTO_NEXT_PAGE = 0x001;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == GOTO_NEXT_PAGE) {
                goToFinish(MainActivity.class);
            }
            return false;
        }
    }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mHandler.sendEmptyMessageDelayed(GOTO_NEXT_PAGE, 1000);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeMessages(GOTO_NEXT_PAGE);
        super.onDestroy();
    }
}
