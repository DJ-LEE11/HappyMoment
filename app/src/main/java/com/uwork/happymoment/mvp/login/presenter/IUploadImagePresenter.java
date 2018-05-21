package com.uwork.happymoment.mvp.login.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.login.bean.UploadBean;
import com.uwork.happymoment.mvp.login.contract.IUploadImageContract;
import com.uwork.happymoment.mvp.login.model.IUploadImageModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.BaseResult;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;
import com.uwork.libutil.ToastUtils;

import okhttp3.MultipartBody;

/**
 * Created by jie on 2018/5/20.
 */

public class IUploadImagePresenter <T extends IUploadImageContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IUploadImageContract.Presenter {

    private IUploadImageModel mModel;


    public IUploadImagePresenter(Context context) {
        super(context);
        mModel = new IUploadImageModel(context);
    }

    @Override
    public void uploadImage(MultipartBody.Part part) {
        addSubscription(mModel.uploadImage(part, new OnModelCallBack<BaseResult<UploadBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onSuccess(BaseResult<UploadBean> value) {
                getView().loadHead(value.getData().getFilePath());
            }

            @Override
            public void onError(ApiException e) {
                if (e.getCode().equals(ApiException.ERROR.UNKNOWN)){
                    ToastUtils.show(getContext(),"图片过大");
                }else {
                    getView().handleException(e);
                }
            }

            @Override
            public void onComplete() {
            }
        }));
    }
}
