package com.uwork.happymoment.mvp.social.circle.presenter;

import android.content.Context;

import com.uwork.happymoment.mvp.social.circle.bean.MomentsItemResponseBean;
import com.uwork.happymoment.mvp.social.circle.contract.ICircleContract;
import com.uwork.happymoment.mvp.social.circle.model.ICircleModel;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.presenter.IBaseListPagePresenter;

/**
 * Created by jie on 2018/5/19.
 */

public class ICirclePresenter<T extends ICircleContract.View & IBaseActivityContract.View>
        extends IBaseListPagePresenter<T, MomentsItemResponseBean> implements ICircleContract.Presenter {

    public ICirclePresenter(Context context) {
        super(context);
        mModel = new ICircleModel(context);
    }
}
