package com.uwork.happymoment.mvp.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.libmarqueeview.SimpleMF;
import com.example.libmarqueeview.SimpleMarqueeView;
import com.example.libvideo.NiceVideoPlayer;
import com.example.libvideo.NiceVideoPlayerManager;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.main.activity.MessageCenterActivity;
import com.uwork.happymoment.mvp.main.adapter.VideoAdapter;
import com.uwork.happymoment.mvp.main.adapter.VideoViewHolder;
import com.uwork.happymoment.mvp.main.bean.BannerBean;
import com.uwork.happymoment.mvp.main.bean.VideoBean;
import com.uwork.happymoment.mvp.main.contract.IBannerContract;
import com.uwork.happymoment.mvp.main.contract.IRecommendContract;
import com.uwork.happymoment.mvp.main.presenter.IBannerPresenter;
import com.uwork.happymoment.mvp.main.presenter.IRecommendPresenter;
import com.uwork.happymoment.ui.banner.HomeTopBanner;
import com.uwork.librx.mvp.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jie on 2018/5/9.
 */

public class HomePageFragment extends BaseFragment implements IBannerContract.View, IRecommendContract.View {

    public static final String TAG = HomePageFragment.class.getSimpleName();

    private static HomePageFragment fragment;
    @BindView(R.id.homeTopBanner)
    HomeTopBanner mHomeTopBanner;
    Unbinder unbinder;
    @BindView(R.id.tvCity)
    TextView mTvCity;
    @BindView(R.id.homeCentreBanner)
    HomeTopBanner mHomeCentreBanner;
    @BindView(R.id.marqueeView)
    SimpleMarqueeView mMarqueeView;
    @BindView(R.id.rvVideo)
    RecyclerView mRvVideo;

    public static HomePageFragment newInstance() {
        if (null == fragment) {
            fragment = new HomePageFragment();
        }
        return fragment;
    }

    // 定位相关
    private LocationClient mLocationClient;
    private MyLocationListener myListener = new MyLocationListener();
    // 是否首次定位
    private boolean mIsFirstLoc = true;
    private String mCurrentCity;

    private IBannerPresenter mIBannerPresenter;
    private IRecommendPresenter mIRecommendPresenter;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mIBannerPresenter = new IBannerPresenter(getContext());
        mIRecommendPresenter = new IRecommendPresenter(getContext());
        list.add(mIBannerPresenter);
        list.add(mIRecommendPresenter);
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        initLocation();
        showRollBAr();
        initData();
        return view;
    }

    private void showRollBAr() {
        List<String> data = new ArrayList<>();
        data.add("飞机打死不可恢复");
        data.add("电风扇的空间和福克斯的");
        data.add("粉底师傅的说法父母都是客户反馈了的数据部分都是");
        SimpleMF<String> marqueeFactory = new SimpleMF(getContext());
        marqueeFactory.setData(data);
        mMarqueeView.setMarqueeFactory(marqueeFactory);
        mMarqueeView.startFlipping();
        //点击事件
        //        mMarqueeView.setOnItemClickListener(new OnItemClickListener() {
        //            @Override
        //            public void onItemClickListener(View mView, Object mData, int mPosition) {
        //
        //            }
        //        });
    }

    private void initData() {
        mIBannerPresenter.getBanner(1);
        mIBannerPresenter.getBanner(2);
        mIRecommendPresenter.getRecommend();
    }

    @Override
    public void shoBanner(Integer type, List<BannerBean> bannerBeanList) {
        if (type == 1) {
            mHomeTopBanner.setIndicatorStyle(BaseIndicatorBanner.STYLE_DRAWABLE_RESOURCE)
                    .setIndicatorSelectorRes(R.mipmap.banner_point_un_select, R.mipmap.banner_point_select)
                    .setSource(bannerBeanList)
                    .startScroll();
        } else {
            mHomeCentreBanner.setSource(bannerBeanList)
                    .startScroll();
        }

    }

    private void initLocation() {
        // 定位初始化
        mLocationClient = new LocationClient(getContext());
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);//获取当前的位置信息、城市
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @OnClick({R.id.llLocation, R.id.llMessage, R.id.llStage, R.id.llLoveHelp, R.id.llHealthManager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llLocation:
                break;
            case R.id.llMessage:
                goTo(MessageCenterActivity.class);
                break;
            case R.id.llStage:
                showToast("客栈");
                break;
            case R.id.llLoveHelp:
                showToast("敬请期待");
                break;
            case R.id.llHealthManager:
                showToast("敬请期待");
                break;
        }
    }

    @Override
    public void showRecommend(List<VideoBean> videoBean) {
        mRvVideo.setNestedScrollingEnabled(false);
        mRvVideo.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvVideo.setHasFixedSize(true);
        VideoAdapter adapter = new VideoAdapter(getActivity(), videoBean);
        mRvVideo.setAdapter(adapter);
        mRvVideo.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                NiceVideoPlayer niceVideoPlayer = ((VideoViewHolder) holder).mVideoPlayer;
                if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
            }
        });
    }

    @Override
    public void showEmpty() {

    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            if (mIsFirstLoc) {
                mIsFirstLoc = false;
                if (!TextUtils.isEmpty(location.getCity())) {
                    mCurrentCity = location.getCity();
                    mTvCity.setText(mCurrentCity);
                }
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //关闭图层定位
        mIsFirstLoc = true;
        mLocationClient.stop();
        unbinder.unbind();
    }

}
