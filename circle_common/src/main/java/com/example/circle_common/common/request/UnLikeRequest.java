package com.example.circle_common.common.request;

import android.text.TextUtils;

import com.example.circle_base_library.network.base.BaseRequestClient;
import com.example.circle_common.common.entity.LikesInfo;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 大灯泡 on 2016/12/8.
 */

public class UnLikeRequest extends BaseRequestClient<Boolean> {

    private String likesInfoid;

    public UnLikeRequest(String likesInfoid) {
        this.likesInfoid = likesInfoid;
    }

    @Override
    protected void executeInternal(final int requestType, boolean showDialog) {
        if (TextUtils.isEmpty(likesInfoid)) {
            onResponseError(new BmobException("likesinfoid为空"), requestType);
            return;
        }
        LikesInfo info = new LikesInfo();
        info.setObjectId(likesInfoid);
        info.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                onResponseSuccess(e == null, requestType);
            }
        });

    }
}
