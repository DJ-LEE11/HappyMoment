package com.uwork.happymoment.mvp.my.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uwork.happymoment.R;
import com.uwork.librx.mvp.BaseFragment;

import java.util.List;

/**
 * Created by jie on 2018/5/30.
 */

public class ActivityOnGoingFragment extends BaseFragment {
    public static final String TAG = ActivityOnGoingFragment.class.getSimpleName();

    private static ActivityOnGoingFragment fragment;

    public static ActivityOnGoingFragment newInstance() {
        if (null == fragment) {
            fragment = new ActivityOnGoingFragment();
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
        View view = inflater.inflate(R.layout.fragment_activity_on_going, container, false);
        return view;
    }
}
