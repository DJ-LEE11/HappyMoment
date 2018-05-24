package com.uwork.happymoment.mvp.my.activity;

import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MapActivity extends BaseTitleActivity implements SensorEventListener {

    @BindView(R.id.rlContent)
    RelativeLayout mRlContent;
    private BaiduMap mBaiduMap;
    private UiSettings mUiSettings;
    // 定位相关
    private LocationClient mLocationClient;
    private MyLocationListener myListener = new MyLocationListener();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private SensorManager mSensorManager;
    private Double mLastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccuracy;
    private String mCurrentCity;
    //地图默认缩放
    protected float mDeFaultZoomLevel = 15.0f;
    //地图最大缩放
    protected float mMaxZoomLevel = 20.0f;
    //地图最小缩放
    protected float mMinZoomLevel = 11.0f;
    // 是否首次定位
    private boolean mIsFirstLoc = true;
    private MyLocationData mLocData;
    //单人游戏覆盖点
    private Marker mMarkerPersonGameA;
    private Marker mMarkerPersonGameB;
    private Marker mMarkerPersonGameC;

    @BindView(R.id.mapView)
    MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_map;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("地图");
        setBackClick();
        initBaiDuMap();
    }

    private void initBaiDuMap() {
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
                if (marker == mMarkerPersonGameA || marker == mMarkerPersonGameB || marker == mMarkerPersonGameC) {
                    //将marker所在的经纬度的信息转化成屏幕上的坐标
                    final LatLng ll = marker.getPosition();
                    Point point = mBaiduMap.getProjection().toScreenLocation(ll);
                    marker.setVisible(false);
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
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[SensorManager.DATA_X];
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
                addOverlay(mCurrentLat, mCurrentLon);
                if (location.getCity() != null) {
                    mCurrentCity = location.getCity();
                    showToast(mCurrentCity);
                }
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    private void addOverlay(double latitude, double longitude) {
        mBaiduMap.clear();
        addPersonGameOverlay(latitude, longitude);
        drawLocationCircle();
    }

    //添加单人游戏覆盖物
    private void addPersonGameOverlay(double latitude, double longitude) {
        mMarkerPersonGameA = null;
        mMarkerPersonGameB = null;
        mMarkerPersonGameC = null;
        //覆盖点
        BitmapDescriptor iconMaker = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
        //第一个点
        LatLng llA = new LatLng(latitude + 0.002, longitude + 0.001);
        MarkerOptions ooA = new MarkerOptions().position(llA).icon(iconMaker)
                .zIndex(5).draggable(true);
        mMarkerPersonGameA = (Marker) (mBaiduMap.addOverlay(ooA));
        //第二个点
        LatLng llB = new LatLng(latitude - 0.009, longitude + 0.003);
        MarkerOptions ooB = new MarkerOptions().position(llB).icon(iconMaker)
                .zIndex(5).draggable(true);
        mMarkerPersonGameB = (Marker) (mBaiduMap.addOverlay(ooB));
        //第三个点
        LatLng llC = new LatLng(latitude - 0.006, longitude - 0.007);
        MarkerOptions ooC = new MarkerOptions().position(llC).icon(iconMaker)
                .zIndex(5).draggable(true);
        mMarkerPersonGameC = (Marker) (mBaiduMap.addOverlay(ooC));
    }

    //定位光圈
    public void drawLocationCircle() {
        //设置圆心的左边
        LatLng pt1 = new LatLng(mCurrentLat, mCurrentLon);
        OverlayOptions overlayOptions = new CircleOptions()
                .center(pt1)
                //设置圆的颜色
                .fillColor(Color.parseColor("#223B48EE"))
                .radius(1000);
        mBaiduMap.addOverlay(overlayOptions);
    }

    @OnClick(R.id.ivLocation)
    public void onViewClicked() {
        LatLng ll = new LatLng(mCurrentLat, mCurrentLon);
        MapStatus.Builder builder = new MapStatus.Builder();
        //地图缩放
        builder.target(ll).zoom(mDeFaultZoomLevel);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        showToast(mCurrentCity);
        if (mMarkerPersonGameA == null){
            addPersonGameOverlay(mCurrentLat, mCurrentLon);
        }
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
    public void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
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

}
