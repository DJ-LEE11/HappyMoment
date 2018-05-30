package com.uwork.happymoment.mvp.hotel.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.mvp.main.adapter.RouteLineAdapter;
import com.uwork.happymoment.ui.dialog.SelectRouteDialog;
import com.uwork.happymoment.ui.overlay.BikingRouteOverlay;
import com.uwork.happymoment.ui.overlay.DrivingRouteOverlay;
import com.uwork.happymoment.ui.overlay.MassTransitRouteOverlay;
import com.uwork.happymoment.ui.overlay.TransitRouteOverlay;
import com.uwork.happymoment.ui.overlay.WalkingRouteOverlay;

import java.util.List;

import butterknife.BindView;

public class HotelMapActivity extends BaseTitleActivity implements SensorEventListener {

    public static final String HOTEL_LAT = "HOTEL_LAT";
    public static final String HOTEL_LON = "HOTEL_LON";
    @BindView(R.id.mapView)
    MapView mMapView;

    private double mHotelLat;
    private double mHotelLon;

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
    private String mCurrentCity;
    //地图默认缩放
    protected float mDeFaultZoomLevel = 15.0f;
    //地图最大缩放
    protected float mMaxZoomLevel = 20.0f;
    //地图最小缩放
    protected float mMinZoomLevel = 9.0f;
    // 是否首次定位
    private boolean mIsFirstLoc = true;
    private MyLocationData mLocData;
    //覆盖点
    private Marker mStageMarker;
    //路线规划
    protected RoutePlanSearch mSearch;
    private Dialog mRouteDialog;

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
        return R.layout.activity_hotel_map;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initTitle("步行模式");
    }

    private void initTitle(String model) {
        setTitle("桃源客栈地图模式");
        setBackClick();
        setRightClick(0, model, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRouteDialog();
            }
        });
    }

    private void showRouteDialog() {
        if (mRouteDialog != null) {
            mRouteDialog.dismiss();
        }
        mRouteDialog = SelectRouteDialog.createSelectRouteDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                route(RouteType.Walk);
                initTitle("步行模式");
                mRouteDialog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                route(RouteType.Bike);
                initTitle("骑车模式");
                mRouteDialog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                route(RouteType.Drive);
                initTitle("自驾模式");
                mRouteDialog.dismiss();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                route(RouteType.Transit);
                initTitle("公交模式");
                mRouteDialog.dismiss();
            }
        });
        mRouteDialog.show();
    }

    private RouteType mCurrentRouteType;
    //路线规划
    private void route(RouteType routeType) {
        addOverlay();
        PlanNode start = PlanNode.withLocation(new LatLng(mCurrentLat, mCurrentLon));
        PlanNode end = PlanNode.withLocation(new LatLng(mHotelLat, mHotelLon));
        switch (routeType){
            case Walk://步行
                mSearch.walkingSearch((new WalkingRoutePlanOption())
                        .from(start).to(end));
                break;
            case Bike://骑行
                mSearch.bikingSearch(new BikingRoutePlanOption().from(start).to(end));
                break;
            case Drive://自驾
                mSearch.drivingSearch(new DrivingRoutePlanOption().from(start).to(end));
                break;
            case Transit://公共交通
                mSearch.transitSearch(new TransitRoutePlanOption().from(start).city(mCurrentCity).to(end));
                break;
        }
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        mHotelLat = intent.getDoubleExtra(HOTEL_LAT, 0.0);
        mHotelLon = intent.getDoubleExtra(HOTEL_LON, 0.0);
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
        //地图上比例尺
        mMapView.showScaleControl(false);
        //隐藏缩放控件
        mMapView.showZoomControls(true);
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
                if (marker == mStageMarker) {
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

        //路线规划
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(mOnGetRoutePlanResultListener);
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
                mCurrentCity = location.getCity();
                addOverlay();
                double distance = DistanceUtil.getDistance(new LatLng(mCurrentLat, mCurrentLon), new LatLng(mHotelLat, mHotelLon));
                if (distance<1500){//少于1.5km使用步行
                    route(RouteType.Walk);
                }else{
                    route(RouteType.Drive);
                    initTitle("自驾模式");
                }


            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    //添加覆盖物
    private void addOverlay() {
        mBaiduMap.clear();
        mStageMarker = null;
        //覆盖点
        BitmapDescriptor iconMaker = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
        //第一个点
        LatLng llA = new LatLng(mHotelLat, mHotelLon);
        MarkerOptions ooA = new MarkerOptions().position(llA).icon(iconMaker)
                .zIndex(5).draggable(true);
        mStageMarker = (Marker) (mBaiduMap.addOverlay(ooA));
    }

    private enum RouteType {
        Walk, Bike, Drive, Transit
    }


    /**
     * 路线规划监听传感器
     */
    boolean hasShownDialogue = false;
    protected OnGetRoutePlanResultListener mOnGetRoutePlanResultListener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult result) {//步行路线规划结果回调
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                showToast("抱歉，未找到结果");
            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                showToast("检索地址有歧义");
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                if (result.getRouteLines().size() > 1) {
                    if (!hasShownDialogue) {
                        MyTransitDlg myTransitDlg = new MyTransitDlg(HotelMapActivity.this,
                                result.getRouteLines(),
                                RouteLineAdapter.Type.WALKING_ROUTE);
                        myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                hasShownDialogue = false;
                            }
                        });
                        myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
                            public void onItemClick(int position) {
                                WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                                mBaiduMap.setOnMarkerClickListener(overlay);
                                overlay.setData(result.getRouteLines().get(position));
                                overlay.addToMap();
                                overlay.zoomToSpan();
                            }

                        });
                        myTransitDlg.show();
                        hasShownDialogue = true;
                    }
                } else if (result.getRouteLines().size() == 1) {
                    // 直接显示
                    WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                } else {
                    Log.d("route result", "结果数<0");
                    return;
                }
            }
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
            if (transitRouteResult == null || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                showToast( "抱歉，未找到结果");
            }
            if (transitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                showToast("检索地址有歧义");
                return;
            }
            if (transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                if (transitRouteResult.getRouteLines().size() > 1) {
                    if (!hasShownDialogue) {
                        MyTransitDlg myTransitDlg = new MyTransitDlg(HotelMapActivity.this,
                                transitRouteResult.getRouteLines(),
                                RouteLineAdapter.Type.TRANSIT_ROUTE);
                        myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                hasShownDialogue = false;
                            }
                        });
                        myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
                            public void onItemClick(int position) {
                                TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
                                mBaiduMap.setOnMarkerClickListener(overlay);
                                overlay.setData(transitRouteResult.getRouteLines().get(position));
                                overlay.addToMap();
                                overlay.zoomToSpan();
                            }

                        });
                        myTransitDlg.show();
                        hasShownDialogue = true;
                    }
                } else if (transitRouteResult.getRouteLines().size() == 1) {
                    // 直接显示
                    TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(transitRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();

                } else {
                    Log.d("route result", "结果数<0");
                    return;
                }
            }
        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {
            if (massTransitRouteResult == null || massTransitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                showToast("抱歉，未找到结果");
            }
            if (massTransitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点模糊，获取建议列表
                massTransitRouteResult.getSuggestAddrInfo();
                return;
            }
            if (massTransitRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                if (!hasShownDialogue) {
                    // 列表选择
                    MyTransitDlg myTransitDlg = new MyTransitDlg(HotelMapActivity.this,
                            massTransitRouteResult.getRouteLines(),
                            RouteLineAdapter.Type.MASS_TRANSIT_ROUTE);
                    myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            hasShownDialogue = false;
                        }
                    });
                    myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
                        public void onItemClick(int position) {
                            MassTransitRouteOverlay overlay = new MassTransitRouteOverlay(mBaiduMap);
                            mBaiduMap.setOnMarkerClickListener(overlay);
                            overlay.setData(massTransitRouteResult.getRouteLines().get(position));

                            MassTransitRouteLine line = massTransitRouteResult.getRouteLines().get(position);
                            overlay.setData(line);
                            if (massTransitRouteResult.getOrigin().getCityId() == massTransitRouteResult.getDestination().getCityId()) {
                                // 同城
                                overlay.setSameCity(true);
                            } else {
                                // 跨城
                                overlay.setSameCity(false);

                            }
                            mBaiduMap.clear();
                            overlay.addToMap();
                            overlay.zoomToSpan();
                        }

                    });

                /* 防止多次进入退出，Activity已经释放，但是Dialog仍然弹出，导致的异常释放崩溃 */
                    if (!isFinishing()) {
                        myTransitDlg.show();
                        hasShownDialogue = true;
                    }
                }
            }
        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                showToast("抱歉，未找到结果");
            }
            if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                // result.getSuggestAddrInfo()
                return;
            }
            if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                if (drivingRouteResult.getRouteLines().size() > 1) {
                    if (!hasShownDialogue) {
                        MyTransitDlg myTransitDlg = new MyTransitDlg(HotelMapActivity.this,
                                drivingRouteResult.getRouteLines(),
                                RouteLineAdapter.Type.DRIVING_ROUTE);
                        myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                hasShownDialogue = false;
                            }
                        });
                        myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
                            public void onItemClick(int position) {
                                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                                mBaiduMap.setOnMarkerClickListener(overlay);
                                overlay.setData(drivingRouteResult.getRouteLines().get(position));
                                overlay.addToMap();
                                overlay.zoomToSpan();
                            }

                        });
                        myTransitDlg.show();
                        hasShownDialogue = true;
                    }
                } else if (drivingRouteResult.getRouteLines().size() == 1) {
                    DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(drivingRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                } else {
                    Log.d("route result", "结果数<0");
                    return;
                }

            }
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
            if (bikingRouteResult == null || bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                showToast("抱歉，未找到结果");
            }
            if (bikingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                // result.getSuggestAddrInfo()
                showToast("检索地址有歧义");
                return;
            }
            if (bikingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                if (bikingRouteResult.getRouteLines().size() > 1) {
                    if (!hasShownDialogue) {
                        MyTransitDlg myTransitDlg = new MyTransitDlg(HotelMapActivity.this,
                                bikingRouteResult.getRouteLines(),
                                RouteLineAdapter.Type.DRIVING_ROUTE);
                        myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                hasShownDialogue = false;
                            }
                        });
                        myTransitDlg.setOnItemInDlgClickLinster(new OnItemInDlgClickListener() {
                            public void onItemClick(int position) {
                                BikingRouteOverlay overlay = new BikingRouteOverlay(mBaiduMap);
                                mBaiduMap.setOnMarkerClickListener(overlay);
                                overlay.setData(bikingRouteResult.getRouteLines().get(position));
                                overlay.addToMap();
                                overlay.zoomToSpan();
                            }

                        });
                        myTransitDlg.show();
                        hasShownDialogue = true;
                    }
                } else if (bikingRouteResult.getRouteLines().size() == 1) {
                    BikingRouteOverlay overlay = new BikingRouteOverlay(mBaiduMap);
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(bikingRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                } else {
                    Log.d("route result", "结果数<0");
                    return;
                }

            }
        }
    };

    // 响应DLg中的List item 点击
    interface OnItemInDlgClickListener {
        public void onItemClick(int position);
    }

    // 供路线选择的Dialog
    class MyTransitDlg extends Dialog {

        private List<? extends RouteLine> mtransitRouteLines;
        private ListView transitRouteList;
        private RouteLineAdapter mTransitAdapter;

       OnItemInDlgClickListener onItemInDlgClickListener;

        public MyTransitDlg(Context context, int theme) {
            super(context, theme);
        }

        public MyTransitDlg(Context context, List<? extends RouteLine> transitRouteLines, RouteLineAdapter.Type
                type) {
            this(context, 0);
            mtransitRouteLines = transitRouteLines;
            mTransitAdapter = new RouteLineAdapter(context, mtransitRouteLines, type);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        @Override
        public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
            super.setOnDismissListener(listener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_transit_dialog);

            transitRouteList = (ListView) findViewById(R.id.transitList);
            transitRouteList.setAdapter(mTransitAdapter);

            transitRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onItemInDlgClickListener.onItemClick(position);
                    dismiss();
                    hasShownDialogue = false;
                }
            });
        }

        public void setOnItemInDlgClickLinster(OnItemInDlgClickListener itemListener) {
            onItemInDlgClickListener = itemListener;
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
        mSearch.destroy();
        mLocationClient.stop();
        mMapView.onDestroy();
        mMapView = null;
    }
}