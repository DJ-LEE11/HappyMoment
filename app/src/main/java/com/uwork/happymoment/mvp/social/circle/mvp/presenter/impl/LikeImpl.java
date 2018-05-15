package com.uwork.happymoment.mvp.social.circle.mvp.presenter.impl;


import com.example.circle_base_library.network.base.OnResponseListener;
import com.example.circle_common.common.request.AddLikeRequest;
import com.example.circle_common.common.request.UnLikeRequest;
import com.uwork.happymoment.mvp.social.circle.mvp.callback.OnLikeChangeCallback;
import com.uwork.happymoment.mvp.social.circle.mvp.presenter.ILikePresenter;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by 大灯泡 on 2016/12/7.
 * <p>
 * 点赞model
 */

public class LikeImpl implements ILikePresenter {

    @Override
    public void addLike(String momentid, final OnLikeChangeCallback onLikeChangeCallback) {
        if (onLikeChangeCallback == null) return;
        AddLikeRequest request = new AddLikeRequest(momentid);
        request.setOnResponseListener(new OnResponseListener<String>() {
            @Override
            public void onStart(int requestType) {

            }

            @Override
            public void onSuccess(String response, int requestType) {
                onLikeChangeCallback.onLike(response);
            }

            @Override
            public void onError(BmobException e, int requestType) {

            }
        });
        request.execute();
    }

    @Override
    public void unLike(String likesid, final OnLikeChangeCallback onLikeChangeCallback) {
        if (onLikeChangeCallback == null) return;
        UnLikeRequest request = new UnLikeRequest(likesid);
        request.setOnResponseListener(new OnResponseListener<Boolean>() {
            @Override
            public void onStart(int requestType) {

            }

            @Override
            public void onSuccess(Boolean response, int requestType) {
                if (response) {
                    onLikeChangeCallback.onUnLike();
                }
            }

            @Override
            public void onError(BmobException e, int requestType) {

            }
        });
        request.execute();
    }
}
