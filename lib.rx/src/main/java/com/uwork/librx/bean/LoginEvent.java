package com.uwork.librx.bean;

/**
 * Created by lidongjie on 2017/3/14.
 */

public class LoginEvent {
    //返回登录页面
    public LoginEvent(boolean isFinish) {
        this.isFinish = isFinish;
    }

    private boolean isFinish;

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }
}
