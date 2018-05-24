package com.uwork.happymoment.mvp.social.circle.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.circle.bean.HomeCircleBean;
import com.uwork.happymoment.mvp.social.circle.contract.IMomentListOtherContract;
import com.uwork.happymoment.mvp.social.circle.model.IMomentListOtherModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBaseListPagePresenter;

/**
 * Created by jie on 2018/5/23.
 */

public class IMomentListOtherPresenter <T extends IMomentListOtherContract.View & IBaseActivityContract.View>
        extends IBaseListPagePresenter<T, HomeCircleBean> implements IMomentListOtherContract.Presenter {

    public IMomentListOtherPresenter(Context context) {
        super(context);
        mModel = new IMomentListOtherModel(context);
    }
    @Override
    public void loadFirst() {
        ((IMomentListOtherModel)mModel).setUserId(getView().getUserId());
        super.loadFirst();
    }
}
