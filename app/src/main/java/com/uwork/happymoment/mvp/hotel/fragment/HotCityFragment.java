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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.hotel.activity.HotelMapActivity;
import com.uwork.happymoment.mvp.hotel.activity.RoomListActivity;
import com.uwork.happymoment.mvp.hotel.adapter.HotelAdapter;
import com.uwork.happymoment.mvp.hotel.bean.HotCityLabelBean;
import com.uwork.happymoment.mvp.hotel.bean.HotelItemBean;
import com.uwork.happymoment.mvp.hotel.contract.IHotelCityContract;
import com.uwork.happymoment.mvp.hotel.presenter.IHotelCityPresenter;
import com.uwork.librx.mvp.BaseFragment;
import com.uwork.libutil.IntentUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

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

public class HotCityFragment extends BaseFragment implements IHotelCityContract.View {
    public static final String TAG = HotCityFragment.class.getSimpleName();

    private static HotCityFragment fragment;
    @BindView(R.id.id_sticky_scrollview)
    RecyclerView mRVHotel;
    Unbinder unbinder;
    @BindView(R.id.flowLayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.rlFlowContent)
    RelativeLayout mRlFlowContent;

    private HotelAdapter mHotelAdapter;
    private LayoutInflater mInflater;

    public static HotCityFragment newInstance() {
        if (null == fragment) {
            fragment = new HotCityFragment();
        }
        return fragment;
    }

    private IHotelCityPresenter mIHotelCityPresenter;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mIHotelCityPresenter = new IHotelCityPresenter(getContext());
        list.add(mIHotelCityPresenter);
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_city, container, false);
        unbinder = ButterKnife.bind(this, view);
        mInflater = LayoutInflater.from(getActivity());
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
        mIHotelCityPresenter.hotCityLabel();
    }

    @Override
    public void showLabel(List<HotCityLabelBean> labelBeanList) {
        mRlFlowContent.setVisibility(View.VISIBLE);
        List<String> labels = new ArrayList<>();
        for (HotCityLabelBean hotCityLabelBean : labelBeanList) {
            labels.add(hotCityLabelBean.getName());
        }

        TagAdapter tagAdapter = new TagAdapter<String>(labels) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.label_text,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        mFlowLayout.setAdapter(tagAdapter);

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mIHotelCityPresenter.hotelList(labelBeanList.get(position).getId());
                return true;
            }
        });
        //默认选中第一个
        tagAdapter.setSelectedList(0);
        mIHotelCityPresenter.hotelList(labelBeanList.get(0).getId());
    }

    @Override
    public void showLabelEmpty() {
        mRlFlowContent.setVisibility(View.GONE);
        mHotelAdapter.setEmptyView(getActivity());
    }

    @Override
    public void showHotelList(List<HotelItemBean> hotelItemBeanList) {
        mHotelAdapter.setNewData(hotelItemBeanList);
    }

    @Override
    public void showListEmpty() {
        mHotelAdapter.setNewData(new ArrayList<>());
        mHotelAdapter.setEmptyView(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
