package com.uwork.happymoment.mvp.my.fragment.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uwork.happymoment.R;

import java.util.List;

/**
 * Created by jie on 2018/5/30.
 */

public class CancelOrderFragment extends BaseOrderFragment {
    public static final String TAG = CancelOrderFragment.class.getSimpleName();

    private static CancelOrderFragment fragment;

    public static CancelOrderFragment newInstance() {
        if (null == fragment) {
            fragment = new CancelOrderFragment();
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
        View view = inflater.inflate(R.layout.fragment_cancel_order, container, false);
        return view;
    }
}
