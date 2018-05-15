package com.uwork.happymoment.mvp.social.circle.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.social.circle.activity.circledemo.FriendCircleDemoActivity;
import com.uwork.librx.mvp.BaseFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jie on 2018/5/9.
 */

public class CircleListFragment extends BaseFragment {

    public static final String TAG = CircleListFragment.class.getSimpleName();

    private static CircleListFragment fragment;
    Unbinder unbinder;

    public static CircleListFragment newInstance() {
        if (null == fragment) {
            fragment = new CircleListFragment();
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
        View view = inflater.inflate(R.layout.fragment_circle_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        goTo(FriendCircleDemoActivity.class);
    }
}
