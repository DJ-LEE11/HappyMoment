package com.uwork.happymoment.mvp.my.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kw.rxbus.RxBus;
import com.uwork.happymoment.R;
import com.uwork.librx.bean.LoginEvent;
import com.uwork.librx.mvp.BaseFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jie on 2018/5/9.
 */

public class MyFragment extends BaseFragment {

    public static final String TAG = MyFragment.class.getSimpleName();

    private static MyFragment fragment;
    Unbinder unbinder;

    public static MyFragment newInstance() {
        if (null == fragment) {
            fragment = new MyFragment();
        }
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tvMy)
    public void onViewClicked() {
        showToast("登录失效，请先登录");
        RxBus.getInstance().send(new LoginEvent(true));
        getActivity().finish();
    }
}
