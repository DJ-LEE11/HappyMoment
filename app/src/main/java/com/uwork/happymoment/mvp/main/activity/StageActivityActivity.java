package com.uwork.happymoment.mvp.main.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.example.libvideo.NiceVideoPlayer;
import com.example.libvideo.NiceVideoPlayerManager;
import com.example.libvideo.TxVideoPlayerController;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.main.bean.StageActivityDetailBean;
import com.uwork.happymoment.mvp.main.contract.IStageActivityContract;
import com.uwork.happymoment.mvp.main.presenter.IStageActivityPresenter;
import com.uwork.happymoment.ui.banner.StageActivityBanner;
import com.uwork.happymoment.ui.dialog.ShareDialog;
import com.uwork.librx.mvp.BaseActivity;
import com.uwork.libutil.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StageActivityActivity extends BaseActivity implements SensorEventListener, IStageActivityContract.View {

    public static final String ACTIVITY_ID = "ACTIVITY_ID";
    @BindView(R.id.ivHeader)
    ImageView mIvHeader;
    @BindView(R.id.video)
    NiceVideoPlayer mVideo;
    @BindView(R.id.tvSelectVideo)
    TextView mTvSelectVideo;
    @BindView(R.id.tvSelectPhoto)
    TextView mTvSelectPhoto;
    @BindView(R.id.tvActivityName)
    TextView mTvActivityName;
    @BindView(R.id.tvStoreName)
    TextView mTvStoreName;
    @BindView(R.id.tvTime)
    TextView mTvTime;
    @BindView(R.id.tvActivityIntroduce)
    TextView mTvActivityIntroduce;
    @BindView(R.id.tvPhone)
    TextView mTvPhone;
    @BindView(R.id.tvAddress)
    TextView mTvAddress;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.tvStoreIntroduce)
    TextView mTvStoreIntroduce;
    @BindView(R.id.tvNotice)
    TextView mTvNotice;
    @BindView(R.id.tvOtherActivity)
    TextView mTvOtherActivity;
    @BindView(R.id.tvOtherActivityTitle)
    TextView mTvOtherActivityTitle;
    @BindView(R.id.stageActivityBanner)
    StageActivityBanner mStageActivityBanner;

    private BaiduMap mBaiduMap;
    private UiSettings mUiSettings;
    // 定位相关
    private LocationClient mLocationClient;
    private MyLocationListener myListener = new MyLocationListener();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    private SensorManager mSensorManager;
    private Double mLastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccuracy;
    //地图默认缩放
    protected float mDeFaultZoomLevel = 15.0f;
    //地图最大缩放
    protected float mMaxZoomLevel = 20.0f;
    //地图最小缩放
    protected float mMinZoomLevel = 11.0f;
    // 是否首次定位
    private boolean mIsFirstLoc = true;
    private MyLocationData mLocData;
    //活动覆盖点
    private Marker mActivityMarker;


    private int mActivityId;
    private String mAddress;
    private String mStartTime;
    private String mEndTime;
    private double mStageLat;
    private double mStageLon;
    private IStageActivityPresenter mIStageActivityPresenter;
    private Dialog mShareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_stage_activity;
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mIStageActivityPresenter = new IStageActivityPresenter(this);
        list.add(mIStageActivityPresenter);
        return list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        selectVideo(true);
        initMap();
    }

    private void selectVideo(boolean isSelect) {
        mVideo.setVisibility(isSelect ? View.VISIBLE : View.GONE);
        mIvHeader.setVisibility(isSelect ? View.GONE : View.VISIBLE);
        mTvSelectVideo.setSelected(isSelect);
        mTvSelectPhoto.setSelected(!isSelect);
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        mActivityId = intent.getIntExtra(ACTIVITY_ID, 0);
        mIStageActivityPresenter.getStageActivityDetail(mActivityId);
    }

    @Override
    public void showDetail(StageActivityDetailBean stageActivityDetailBean) {
        mAddress = stageActivityDetailBean.getAddress();
        mStartTime = stageActivityDetailBean.getStartTime();
        mEndTime = stageActivityDetailBean.getEndTime();
        String lngLat = stageActivityDetailBean.getLngLat();
        if (!TextUtils.isEmpty(lngLat)) {
            String[] location = lngLat.split(",");
            if (location.length == 2) {
                mStageLon = Double.valueOf(location[0]);
                mStageLat = Double.valueOf(location[1]);
            }
        }
        initVideoPhoto(stageActivityDetailBean);
        initInfo(stageActivityDetailBean);
        addOverlay();
        initBanner(stageActivityDetailBean);
    }

    private void initVideoPhoto(StageActivityDetailBean stageActivityDetailBean) {
        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams paramsVideo = mVideo.getLayoutParams();
        paramsVideo.width = getResources().getDisplayMetrics().widthPixels; // 宽度为屏幕宽度
        paramsVideo.height = (int) (paramsVideo.width * 9f / 16f);    // 高度为宽度的9/16
        mVideo.setLayoutParams(paramsVideo);
        mVideo.setPlayerType(NiceVideoPlayer.TYPE_IJK);
        mVideo.setUp(stageActivityDetailBean.getVideoUrl().get(0), null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("");
        ImageLoadMnanger.INSTANCE.loadImage(controller.imageView(), stageActivityDetailBean.getPictureUrl().get(0));
        mVideo.setController(controller);

        ViewGroup.LayoutParams paramsPhoto = mIvHeader.getLayoutParams();
        paramsPhoto.width = getResources().getDisplayMetrics().widthPixels; // 宽度为屏幕宽度
        paramsPhoto.height = (int) (paramsPhoto.width * 9f / 16f);    // 高度为宽度的9/16
        mIvHeader.setLayoutParams(paramsPhoto);
        ImageLoadMnanger.INSTANCE.loadImage(mIvHeader, stageActivityDetailBean.getPictureUrl().get(0));
    }

    private void initInfo(StageActivityDetailBean stageActivityDetailBean) {
        mTvActivityName.setText(stageActivityDetailBean.getName());
        mTvStoreName.setText("所属门店：" + stageActivityDetailBean.getStageName());
        mTvTime.setText("活动时间：" + TimeUtils.formatDateTime(Long.valueOf(mStartTime), "yyyy.M.d") + "-" + TimeUtils.formatDateTime(Long.valueOf(mEndTime), "yyyy.M.d"));
        mTvActivityIntroduce.setText(stageActivityDetailBean.getIntroduce());
        mTvPhone.setText("联系电话：" + stageActivityDetailBean.getContactWay());
        mTvAddress.setText("详细地址：" + stageActivityDetailBean.getAddress());
        mTvStoreIntroduce.setText(stageActivityDetailBean.getStageIntroduce());
    }

    private void initBanner(StageActivityDetailBean stageActivityDetailBean) {
        List<StageActivityDetailBean.StageActivitiesBean> stageActivities = stageActivityDetailBean.getStageActivities();
        if (stageActivities != null && stageActivities.size() > 0) {
            mStageActivityBanner.setIndicatorStyle(BaseIndicatorBanner.STYLE_DRAWABLE_RESOURCE)
                    .setIndicatorSelectorRes(R.mipmap.banner_point_un_select, R.mipmap.banner_point_select)
                    .setSource(stageActivities)
                    .setAutoScrollEnable(false)//不自动滚动
                    .startScroll();
            mTvOtherActivity.setText(stageActivities.get(0).getName());
            mStageActivityBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mTvOtherActivity.setText(stageActivities.get(position).getName());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            mStageActivityBanner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                @Override
                public void onItemClick(int position) {
                    showToast("id"+stageActivities.get(position).getId());
                }
            });
        } else {
            mStageActivityBanner.setVisibility(View.GONE);
            mTvOtherActivity.setVisibility(View.GONE);
            mTvOtherActivityTitle.setVisibility(View.GONE);
        }
    }

    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return false;
        }
    };

    @OnClick({R.id.tvSelectVideo, R.id.tvSelectPhoto, R.id.ivBack, R.id.ivShare, R.id.tvJoin, R.id.tvCall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSelectVideo:
                selectVideo(true);
                break;
            case R.id.tvSelectPhoto:
                selectVideo(false);
                break;
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivShare:
                showShareDialog();
                break;
            case R.id.tvJoin:
                showToast("加入");
                break;
            case R.id.tvCall:
                showToast("打电话");
                break;
        }
    }

    private void showShareDialog() {
        mShareDialog = new ShareDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {//分享到微信
                showToast("微信");
                mShareDialog.cancel();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {//分享到朋友圈
                showToast("朋友圈");
                mShareDialog.cancel();
            }
        });
        mShareDialog.show();
    }

    @Override
    public void joinSuccess() {

    }

    @Override
    public void onStart() {
        //开启图层定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭图层定位
        mIsFirstLoc = true;
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        mMapView.onDestroy();
        mMapView = null;
    }


    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    private void initMap() {
        mBaiduMap = mMapView.getMap();
        mUiSettings = mBaiduMap.getUiSettings();
        //隐藏百度logo
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        //隐藏地图上比例尺
        mMapView.showScaleControl(false);
        //隐藏缩放控件
        mMapView.showZoomControls(false);
        //隐藏指南针
        mUiSettings.setCompassEnabled(false);
        //关闭地图俯视（3D）
        mUiSettings.setOverlookingGesturesEnabled(false);
        //关闭地图旋转
        //        mUiSettings.setRotateGesturesEnabled(false);

        //获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        //自定义定位Icon
        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_my_location);
        //        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
        //                mCurrentMode, true, mCurrentMarker, accuracyCircleFillColor, accuracyCircleStrokeColor));
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker, 0, 0));
        MapStatus.Builder builder1 = new MapStatus.Builder();
        builder1.overlook(0);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);//获取当前的位置信息、城市
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        //地图缩放范围
        mBaiduMap.setMaxAndMinZoomLevel(mMaxZoomLevel, mMinZoomLevel);
        //覆盖物点击事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //单人游戏
                if (marker == mActivityMarker) {
                    //将marker所在的经纬度的信息转化成屏幕上的坐标
                    final LatLng ll = marker.getPosition();
                    Point point = mBaiduMap.getProjection().toScreenLocation(ll);
                }
                return true;
            }
        });

        //添加地图点击事件
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                mBaiduMap.hideInfoWindow();

            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - mLastX) > 1.0) {
            mCurrentDirection = (int) x;
            mLocData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccuracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(mLocData);
        }
        mLastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccuracy = location.getRadius();
            mLocData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(mLocData);
            if (mIsFirstLoc) {
                mIsFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(mDeFaultZoomLevel);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    private void addOverlay() {
        mBaiduMap.clear();
        mActivityMarker = null;
        //覆盖点
        BitmapDescriptor iconMaker = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
        //第一个点
        LatLng llA = new LatLng(mStageLat, mStageLon);
        MarkerOptions ooA = new MarkerOptions().position(llA).icon(iconMaker)
                .zIndex(5).draggable(true);
        mActivityMarker = (Marker) (mBaiduMap.addOverlay(ooA));
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(llA);
        mBaiduMap.setMapStatus(u);
    }
}
