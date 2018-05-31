package com.uwork.happymoment.mvp.hotel.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.hotel.bean.RoomDetailBean;
import com.uwork.happymoment.mvp.hotel.contract.IRoomDetailContract;
import com.uwork.happymoment.mvp.hotel.model.IRoomDetailModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

/**
 * Created by jie on 2018/5/31.
 */

public class IRoomDetailPresenter  <T extends IRoomDetailContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IRoomDetailContract.Presenter {

    private IRoomDetailModel mModel;

    public IRoomDetailPresenter(Context context) {
        super(context);
        mModel = new IRoomDetailModel(context);
    }

    @Override
    public void roomDetail(Integer id) {
        addSubscription(mModel.roomDetail(id, new OnModelCallBack<RoomDetailBean>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(RoomDetailBean value) {
                getView().dismissLoading();
                if (value!=null){
                    getView().showRoomDetail(value);
                }
            }

            @Override
            public void onError(ApiException e) {
                getView().dismissLoading();
                getView().handleException(e);
            }

            @Override
            public void onComplete() {
                getView().dismissLoading();
            }
        }));
    }
}
