package com.uwork.librx.mvp.contract;


import com.uwork.librx.rx.http.ApiException;

public interface IBaseActivityContract {

    public interface View extends IBaseContract.View {


        void showToast(String str);

        void showLoading();

        void showLoading(android.view.View v, int strId);

        void showLoading(android.view.View v, String tip);

        void showLoading(android.view.View v, int strId, int color);

        void showLoading(android.view.View v, String tip, int color);

        void showLoading(android.view.View v, String tip, int color, int backgroundColor);

        void showLoadingDialog();

        void showLoadingDialog(android.view.View v, int strId, int imgId);

        void showLoadingDialog(android.view.View v, String tip, int imgId);

        void dismissLoading();

        void dismissLoadingDialog();

        void showErrorPage(android.view.View v, int strId, int imgId);

        void showErrorPage(android.view.View v, String tip, int imgId);

        void showErrorPage(android.view.View v, String tip, int imgId, int backgroundColor);

        void onErrorPageClick();

        void insertView(android.view.View parentView, android.view.View childView);

        void removeErrorPage();

        //处理上面提供展示方法的错误
        void handleException(ApiException e);

    }

    public interface Presenter {

    }

    public interface Model {

    }


}