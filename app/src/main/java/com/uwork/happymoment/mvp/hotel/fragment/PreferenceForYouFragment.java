package com.uwork.happymoment.mvp.hotel.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.hotel.activity.HotelMapActivity;
import com.uwork.happymoment.mvp.hotel.activity.RoomListActivity;
import com.uwork.happymoment.mvp.hotel.adapter.HotelAdapter;
import com.uwork.happymoment.mvp.hotel.bean.HotelItemBean;
import com.uwork.happymoment.mvp.hotel.contract.IPreferenceForYouContract;
import com.uwork.happymoment.mvp.hotel.presenter.IPreferenceForYouPresenter;
import com.uwork.librx.mvp.BaseFragment;
import com.uwork.libutil.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.uwork.happymoment.mvp.hotel.activity.HotelMapActivity.HOTEL_LAT;
import static com.uwork.happymoment.mvp.hotel.activity.HotelMapActivity.HOTEL_LON;
import static com.uwork.happymoment.mvp.hotel.activity.RoomListActivity.HOTEL_ID;
import static com.uwork.happymoment.mvp.hotel.activity.RoomListActivity.HOTEL_NAME;

/**
 * Created by jie on 2018/5/30.
 */

public class PreferenceForYouFragment extends BaseFragment implements IPreferenceForYouContract.View {
    public static final String TAG = PreferenceForYouFragment.class.getSimpleName();

    private static PreferenceForYouFragment fragment;
    @BindView(R.id.id_sticky_scrollview)
    RecyclerView mRVHotel;
    Unbinder unbinder;

    private HotelAdapter mHotelAdapter;

    public static PreferenceForYouFragment newInstance() {
        if (null == fragment) {
            fragment = new PreferenceForYouFragment();
        }
        return fragment;
    }

    private IPreferenceForYouPresenter mIPreferenceForYouPresenter;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mIPreferenceForYouPresenter = new IPreferenceForYouPresenter(getContext());
        list.add(mIPreferenceForYouPresenter);
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preference_for_you, container, false);
        unbinder = ButterKnife.bind(this, view);
        initList();
        initData();
        return view;
    }

    private void initList() {
        mRVHotel.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mHotelAdapter = new HotelAdapter(new ArrayList<>());
        mRVHotel.setAdapter(mHotelAdapter);

        mHotelAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                HotelItemBean hotelItemBean = (HotelItemBean) adapter.getData().get(position);
                if (view.getId() == R.id.llLocation){
                    String lngLat = hotelItemBean.getLngLat();
                    if (!TextUtils.isEmpty(lngLat)){
                        String[] location = lngLat.split(",");
                        if (location.length==2){
                            new IntentUtils.Builder(getActivity())
                                    .to(HotelMapActivity.class)
                                    .putExtra(HOTEL_LON,Double.valueOf(location[0]))//经度
                                    .putExtra(HOTEL_LAT,Double.valueOf(location[1]))//维度
                                    .build()
                                    .start();
                        }else {
                            showToast("暂无活动地址");
                        }
                    }else {
                        showToast("暂无活动地址");
                    }
                }else if (view.getId() == R.id.tvDetail){
                    new IntentUtils.Builder(getActivity())
                            .to(RoomListActivity.class)
                            .putExtra(HOTEL_ID,hotelItemBean.getId())
                            .putExtra(HOTEL_NAME,hotelItemBean.getName())
                            .build()
                            .start();
                }
            }
        });
    }

    private void initData() {
        mIPreferenceForYouPresenter.getHotelList();
    }

    @Override
    public void showHotelList(List<HotelItemBean> hotelItemBeanList) {
        mHotelAdapter.setNewData(hotelItemBeanList);
    }

    @Override
    public void showEmpty() {
        mHotelAdapter.setEmptyView(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
