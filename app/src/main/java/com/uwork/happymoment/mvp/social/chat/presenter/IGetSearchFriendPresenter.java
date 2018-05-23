package com.uwork.happymoment.mvp.social.chat.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.chat.bean.SearchFriendBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetSearchFriendContract;
import com.uwork.happymoment.mvp.social.chat.model.IGetSearchFriendModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBasePresenterImpl;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.List;

/**
 * Created by jie on 2018/5/23.
 */

public class IGetSearchFriendPresenter<T extends IGetSearchFriendContract.View & IBaseActivityContract.View> extends IBasePresenterImpl<T>
        implements IGetSearchFriendContract.Presenter {

    private IGetSearchFriendModel mModel;

    public IGetSearchFriendPresenter(Context context) {
        super(context);
        mModel = new IGetSearchFriendModel(context);
    }

    @Override
    public void searchFriend(String keyWord) {
        addSubscription(mModel.searchFriend(keyWord, new OnModelCallBack<List<SearchFriendBean>>() {
            @Override
            public void onStart() {
                getView().showLoading();
            }

            @Override
            public void onCancel() {
                getView().dismissLoading();
            }

            @Override
            public void onSuccess(List<SearchFriendBean> value) {
                getView().dismissLoading();
                if (value !=null && value.size()>0){
                    getView().showFriend(value);
                }else {
                    getView().empty();
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
