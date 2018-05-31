package com.uwork.happymoment.mvp.hotel.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.hotel.bean.RoomItemBean;
import com.uwork.happymoment.mvp.hotel.contract.IGetRoomListContract;
import com.uwork.happymoment.mvp.hotel.model.IGetRoomListModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/31.
 */

public class IGetRoomListPresenter <T extends IGetRoomListContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IGetRoomListContract.Presenter {

    private IGetRoomListModel mModel;

    public IGetRoomListPresenter(Context context) {
        super(context);
        mModel = new IGetRoomListModel(context);
    }

    @Override
    public void getRoomList(Integer id) {
        addSubscription(mModel.getRoomList(id, new OnModelCallBack<List<RoomItemBean>>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(List<RoomItemBean> value) {
                getView().dismissLoading();
                if (value!=null && value.size()>0){
                    getView().showRoomList(value);
                }else {
                    getView().showEmpty();
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
