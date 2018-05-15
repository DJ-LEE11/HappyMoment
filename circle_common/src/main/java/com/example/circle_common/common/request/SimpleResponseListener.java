package com.example.circle_common.common.request;

import com.example.circle_base_library.network.base.OnResponseListener;
import com.example.circle_base_ui.util.UIHelper;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by 大灯泡 on 2016/10/28.
 */

public abstract class SimpleResponseListener<T> implements OnResponseListener<T> {

    @Override
    public void onStart(int requestType) {

    }

    @Override
    public void onError(BmobException e, int requestType) {
        UIHelper.ToastMessage(e.getMessage());
        e.printStackTrace();
    }
}
