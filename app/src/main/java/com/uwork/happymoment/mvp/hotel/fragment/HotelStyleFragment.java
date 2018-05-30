package com.uwork.happymoment.mvp.hotel.fragment;

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

public class HotelStyleFragment extends BaseFragment {
    public static final String TAG = HotelStyleFragment.class.getSimpleName();

    private static HotelStyleFragment fragment;

    public static HotelStyleFragment newInstance() {
        if (null == fragment) {
            fragment = new HotelStyleFragment();
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
        View view = inflater.inflate(R.layout.fragment_hotel_style, container, false);
        return view;
    }
}
