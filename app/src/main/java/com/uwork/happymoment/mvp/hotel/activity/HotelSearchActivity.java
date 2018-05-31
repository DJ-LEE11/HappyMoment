package com.uwork.happymoment.mvp.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.libvideo.NiceVideoPlayerManager;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.hotel.adapter.HotelAdapter;
import com.uwork.happymoment.mvp.hotel.bean.HotelItemBean;
import com.uwork.happymoment.mvp.hotel.contract.ISearchHotelContract;
import com.uwork.happymoment.mvp.hotel.presenter.ISearchHotelPresenter;
import com.uwork.librx.mvp.BaseActivity;
import com.uwork.libutil.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.uwork.happymoment.mvp.hotel.activity.HotelMapActivity.HOTEL_LAT;
import static com.uwork.happymoment.mvp.hotel.activity.HotelMapActivity.HOTEL_LON;
import static com.uwork.happymoment.mvp.hotel.activity.RoomListActivity.HOTEL_ID;
import static com.uwork.happymoment.mvp.hotel.activity.RoomListActivity.HOTEL_NAME;

public class HotelSearchActivity extends BaseActivity implements ISearchHotelContract.View{

    @BindView(R.id.etSearchHotel)
    EditText mEtSearchHotel;
    @BindView(R.id.rvVideo)
    RecyclerView mRvVideo;

    private HotelAdapter mHotelAdapter;
    private ISearchHotelPresenter mISearchHotelPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null){
            list = new ArrayList();
        }
        mISearchHotelPresenter = new ISearchHotelPresenter(this);
        list.add(mISearchHotelPresenter);
        return list;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_hotel_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initList();
    }

    private void initList() {
        mRvVideo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mHotelAdapter = new HotelAdapter(new ArrayList<>());
        mRvVideo.setAdapter(mHotelAdapter);

        mHotelAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                HotelItemBean hotelItemBean = (HotelItemBean) adapter.getData().get(position);
                if (view.getId() == R.id.llLocation){
                    String lngLat = hotelItemBean.getLngLat();
                    if (!TextUtils.isEmpty(lngLat)){
                        String[] location = lngLat.split(",");
                        if (location.length==2){
                            new IntentUtils.Builder(HotelSearchActivity.this)
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
                    new IntentUtils.Builder(HotelSearchActivity.this)
                            .to(RoomListActivity.class)
                            .putExtra(HOTEL_ID,hotelItemBean.getId())
                            .putExtra(HOTEL_NAME,hotelItemBean.getName())
                            .build()
                            .start();
                }
            }
        });
    }

    @OnClick({R.id.ivBack, R.id.tvSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvSearch:
                String searchText = mEtSearchHotel.getText().toString();
                if (!TextUtils.isEmpty(searchText)) {
                    mISearchHotelPresenter.searchHotelList(searchText);
                } else {
                    showToast("请输入搜索内容");
                }
                break;
        }
    }

    @Override
    public void showHotelList(List<HotelItemBean> hotelItemBeanList) {
        mHotelAdapter.setNewData(hotelItemBeanList);
    }

    @Override
    public void showEmpty() {
        mHotelAdapter.setNewData(new ArrayList<>());
        mHotelAdapter.setEmptyView(this, "搜不到当前客栈");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

}
