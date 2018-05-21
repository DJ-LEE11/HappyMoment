package com.uwork.happymoment.mvp.login.contract;

import com.uwork.happymoment.mvp.login.bean.UploadBean;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.MultipartBody;

/**
 * Created by jie on 2018/5/20.
 */

public interface IUploadImageContract {
    interface View {
        void loadHead(String Url);
    }

    interface Presenter {
        void uploadImage( MultipartBody.Part part);
    }

    interface Model {
        //上传照片
        ResourceSubscriber uploadImage(MultipartBody.Part image,
                                       OnModelCallBack<BaseResult<UploadBean>> callBack);
    }
}
