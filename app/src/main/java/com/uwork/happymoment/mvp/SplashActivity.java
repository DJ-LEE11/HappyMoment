package com.uwork.happymoment.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.uwork.happymoment.R;
import com.uwork.happymoment.manager.IMRongManager;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.librx.mvp.BaseActivity;

import java.util.List;

import io.rong.imkit.RongIM;

public class SplashActivity extends BaseActivity {

    private static final int GOTO_NEXT_PAGE = 0x001;
    public static final String CLEAN_TOKEN = "CLEAN_TOKEN";

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

    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        isCleanToken();
    }

    private void isCleanToken() {
        boolean noCheckToken = false;
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(CLEAN_TOKEN)) {
            noCheckToken = getIntent().getExtras().getBoolean(CLEAN_TOKEN);
            if (noCheckToken) {
                RongIM.getInstance().logout();
                UserManager.getInstance().logout(this);
            }
        }
        if (UserManager.getInstance().isLogin(this)){
            UserBean user = UserManager.getInstance().getUser(this);
            //连接融云
            IMRongManager.imConnect(this,user.getImtoken());
        }
        mHandler.sendEmptyMessageDelayed(GOTO_NEXT_PAGE, 1000);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeMessages(GOTO_NEXT_PAGE);
        super.onDestroy();
    }
}
