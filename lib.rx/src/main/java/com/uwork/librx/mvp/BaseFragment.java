package com.uwork.librx.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kw.rxbus.RxBus;
import com.uwork.librx.R;
import com.uwork.librx.bean.LoginEvent;
import com.uwork.librx.mvp.contract.IBaseActivityContract;
import com.uwork.librx.mvp.contract.IBaseContract;
import com.uwork.librx.rx.http.ApiException;
import com.uwork.librx.ui.LoadingDialog;
import com.uwork.librx.ui.ThinLineSpinFadeLoaderIndicator;
import com.uwork.libutil.IntentUtils;
import com.uwork.libutil.ToastUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

/**
 * @author 李栋杰
 * @time 2017/8/2  10:37
 * @desc ${TODD}
 */
public abstract class BaseFragment<T extends IBaseContract.Presenter> extends Fragment implements IBaseActivityContract.View{

    public final static String TAG = BaseFragment.class.getSimpleName();

    protected List<T> mPresenters;
    private LoadingDialog loadingDialog;
    protected View errorView, loadingView;
    private long loadingStartTime;
    public boolean isVisible = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化屏幕区域
        initPresenter();
    }

    private void initPresenter() {
        //初始化Presenter
        mPresenters = createPresenter(null);
        //presenter与View绑定
        if (null != mPresenters) {
            for (T presenter : mPresenters) {
                presenter.attachView(this);
            }
        }
    }


    /**
     * 懒加载
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
            onVisiable();
        }else{
            isVisible = false;
            onInvisiable();
        }
    }

    private void onVisiable(){
        lazyLoad();
    }

    private void onInvisiable(){

    }

    protected abstract void lazyLoad();

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract List<T> createPresenter(List<T> list);


    @Override
    public void onDestroy() {
        detachPresenter();
        super.onDestroy();
    }

    private void detachPresenter() {
        //presenter与activity解绑定
        if (null != mPresenters) {
            while (!mPresenters.isEmpty()) {
                T presenter = mPresenters.get(0);
                presenter.dispose();
                presenter.detachView();
                mPresenters.remove(0);
            }
        }
    }

    protected void goTo(Class clazz){
        new IntentUtils.Builder(getActivity())
                .to(clazz)
                .build()
                .start();
    }

    protected void goTo(Class clazz, int requestCode){
        new IntentUtils.Builder(getActivity())
                .to(clazz)
                .build(requestCode)
                .start();
    }

    protected void goToFinish(Class clazz){
        new IntentUtils.Builder(getActivity())
                .to(clazz)
                .finish(true)
                .build()
                .start();
    }

    protected void goTo(Class clazz, boolean isFinish, String key, String value){
        new IntentUtils.Builder(getActivity())
                .to(clazz)
                .putExtra(key,value)
                .finish(isFinish)
                .build()
                .start();
    }

    protected void goTo(Class clazz, boolean isFinish, String key, int value){
        new IntentUtils.Builder(getActivity())
                .to(clazz)
                .putExtra(key,value)
                .finish(isFinish)
                .build()
                .start();
    }

    protected void goTo(Class clazz, boolean isFinish, String key, boolean value){
        new IntentUtils.Builder(getActivity())
                .to(clazz)
                .putExtra(key,value)
                .finish(isFinish)
                .build()
                .start();
    }

    @Override
    public void showToast(String str) {
        ToastUtils.show(getActivity(), str);
    }

    @Override
    public void showLoading() {
            loadingDialog = new LoadingDialog.Builder(getContext())
                    .message("请稍后...")
                    .cancelable(true)
                    .build();
        loadingDialog.show();
    }

    @Override
    public void showLoading(View v, int strId) {
        String tip = getResources().getString(strId);
        showLoading(v, tip, ContextCompat.getColor(getActivity(), R.color.colorAccent), 0);
    }

    @Override
    public void showLoading(View v, String tip) {
        showLoading(v, tip, ContextCompat.getColor(getActivity(), R.color.colorAccent), 0);
    }

    @Override
    public void showLoading(View v, int strId, int color) {
        String tip = getResources().getString(strId);
        showLoading(v, tip, color, 0);
    }

    @Override
    public void showLoading(View v, String tip, int color) {
        showLoading(v, tip, color, 0);
    }

    @Override
    public void showLoading(View v, String tip, int color, int backgroundColor) {
        if (loadingView == null) {
            loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.loading_layout, null);
        }
        TextView tvLoading = (TextView) loadingView.findViewById(R.id.tvLoading);
        AVLoadingIndicatorView avi = (AVLoadingIndicatorView) loadingView.findViewById(R.id.avi);

        if (tvLoading != null) {
            if (TextUtils.isEmpty(tip)) {
                tvLoading.setText("");
                tvLoading.setVisibility(View.GONE);
            } else {
                tvLoading.setText(tip);
                tvLoading.setVisibility(View.VISIBLE);
            }
        }
        if (avi != null) {
            avi.setIndicator(new ThinLineSpinFadeLoaderIndicator());
            if (color != 0) {
                avi.setIndicatorColor(color); // 修改颜色
            }
            //设置背景
            if (backgroundColor != 0) {
                ((View) avi.getParent().getParent()).setBackgroundColor(backgroundColor);
            }
        }
        if (!loadingView.isShown()) {
            insertView(v, loadingView);
            loadingStartTime = System.currentTimeMillis();
        }
        loadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void showLoadingDialog(View v, int strId, int imgId) {

    }

    @Override
    public void showLoadingDialog(View v, String tip, int imgId) {

    }

    @Override
    public void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.finish();
        }
    }

    @Override
    public void dismissLoadingDialog() {

    }


    @Override
    public void onErrorPageClick() {
        removeErrorPage();
    }

    @Override
    public void removeErrorPage() {
        if (errorView != null && errorView.isShown()) {
            ((ViewGroup) errorView.getParent()).removeView(errorView);
            errorView = null;
        }
    }

    @Override
    public void showErrorPage(View v, int strId, int imgId) {
        String tip = getResources().getString(strId);
        showErrorPage(v, tip, imgId, 0);
    }

    @Override
    public void showErrorPage(View v, String tip, int imgId) {
        showErrorPage(v, tip, imgId, 0);
    }

    @Override
    public void showErrorPage(View v, String tip, int imgId, int backgroundColor) {
        if (errorView == null) {
            errorView = LayoutInflater.from(getActivity()).inflate(R.layout.error_layout, null);
        }
        TextView tvError = (TextView) errorView.findViewById(R.id.tvError);
        ImageView imgError = (ImageView) errorView.findViewById(R.id.imgError);
        if (tvError != null) {
            tvError.setText(tip);
            //设置背景
            if (backgroundColor != 0) {
                ((View) tvError.getParent().getParent()).setBackgroundColor(backgroundColor);
            }
        }
        if (imgError != null) {
            imgError.setImageResource(imgId);
            imgError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onErrorPageClick();
                }
            });
        }
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (!errorView.isShown()) {
            insertView(v, errorView);
        }
    }

    @Override
    public void insertView(View parentView, View childView) {
        ViewGroup parent = (ViewGroup) parentView.getParent();
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) parentView.getLayoutParams();
        FrameLayout frameLayout = null;
        FrameLayout.LayoutParams params = null;
        if (parent instanceof FrameLayout) {
            params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            frameLayout = (FrameLayout) parent;
        } else {
            frameLayout = new FrameLayout(getActivity());
            parent.removeView(parentView);
            FrameLayout.LayoutParams paramsMargin = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            paramsMargin.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin,
                    layoutParams.bottomMargin);
            frameLayout.addView(parentView, paramsMargin);
            layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT;
            layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
            layoutParams.setMargins(0, 0, 0, 0);
            parent.addView(frameLayout, layoutParams);
            params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
        }
        if (childView.getParent() != null) {
            ((ViewGroup) childView.getParent()).removeView(childView);
        }
        frameLayout.addView(childView, params);
    }

    @Override
    public void handleException(ApiException e) {
        if (e != null) {
            switch (e.getCode()) {
                case ApiException.ERROR.HTTP_ERROR:
                    dealHttpError(e);
                    break;
                case ApiException.ERROR.NETWORD_ERROR:
                    dealNetworkError(e);
                    break;
                case ApiException.ERROR.PARSE_ERROR:
                    dealParseError(e);
                    break;
                case ApiException.ERROR.UNKNOWN:
                    dealUnknownError(e);
                    break;
                case ApiException.ERROR.UNAUTHORIZED:
                    gotoLogin();
                    break;
                default:
                    dealServerError(e);
                    break;
            }
        }
    }

    protected void dealServerError(ApiException e){
        showToast(e.getDisplayMessage());//默认情况，直接Toast提示。
    }

    protected void dealHttpError(ApiException e) {
        showToast(e.getDisplayMessage());//默认情况，直接Toast提示。
    }

    protected void dealNetworkError(ApiException e) {
        showToast(e.getDisplayMessage());//默认情况，直接Toast提示。
    }

    protected void dealParseError(ApiException e) {
        showToast(e.getDisplayMessage());//默认情况，直接Toast提示。
    }

    protected void dealUnknownError(ApiException e) {
        showToast(e.getDisplayMessage());//默认情况，直接Toast提示。
    }

    //返回登陆页面
    protected void gotoLogin() {
        RxBus.getInstance().send(new LoginEvent(true));
        getActivity().finish();
    }
}
