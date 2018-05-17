package com.uwork.happymoment.mvp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.circle_base_library.common.entity.ImageInfo;
import com.example.circle_base_ui.helper.PhotoHelper;
import com.example.circle_base_ui.util.UIHelper;
import com.example.circle_common.common.router.RouterList;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kw.rxbus.RxBus;
import com.uwork.happymoment.R;
import com.uwork.happymoment.bean.TabEntity;
import com.uwork.happymoment.event.RefreshCircleEvent;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.hotel.fragment.HotelFragment;
import com.uwork.happymoment.mvp.login.activity.LoginRegisterActivity;
import com.uwork.happymoment.mvp.main.fragment.HomePageFragment;
import com.uwork.happymoment.mvp.my.fragment.MyFragment;
import com.uwork.happymoment.mvp.social.circleTest.activity.test.ActivityLauncher;
import com.uwork.happymoment.mvp.social.fragment.SocialFragment;
import com.uwork.librx.bean.LoginEvent;
import com.uwork.librx.mvp.BaseActivity;
import com.uwork.libutil.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottomTabLayout)
    CommonTabLayout mBottomTabLayout;
    private FragmentManager mFragmentManager;

    private CompositeDisposable mDisposables;


    private int mCurrentBottom = 0;

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        //判断是否为android6.0系统版本，如果是，需要动态添加权限
        if (Build.VERSION.SDK_INT >= 23) {
            showContacts();
        }
        mFragmentManager = getSupportFragmentManager();
        initBottom();
        initEvent();
    }

    private static final int PERMISSION_REQUEST = 100;

    public void showContacts() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        } else {
            //TODO
        }
    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    //TODO
                } else {
                    // 没有获取到权限，做特殊处理
                    showToast("获取位置权限失败，请手动开启");
                }
                break;
            default:
                break;
        }
    }

    private void initEvent() {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        //出现权限错误跳出主页，返回到引导页
        mDisposables.add(RxBus.getInstance().register(LoginEvent.class, new Consumer<LoginEvent>() {
            @Override
            public void accept(LoginEvent event) throws Exception {
                new IntentUtils.Builder(MainActivity.this)
                        .to(SplashActivity.class)
                        .putExtra(SplashActivity.CLEAN_TOKEN, true)
                        .finish(event.isFinish())
                        .build()
                        .start();
            }
        }));
    }


    @OnClick(R.id.ivSpeech)
    public void onViewClicked() {
        if (!UserManager.getInstance().isLogin(this)){
            goTo(LoginRegisterActivity.class);
        }else {
            showToast("电话");
        }
    }

    private void initBottom() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

        String[] mTitles = getResources().getStringArray(R.array.bottom_menu);
        int[] mIconUnSelectIds = {
                R.mipmap.ic_home_default, R.mipmap.ic_social_default, 0,
                R.mipmap.ic_hotel_default, R.mipmap.ic_my_default};
        int[] mIconSelectIds = {
                R.mipmap.ic_home_select, R.mipmap.ic_social_select, 0,
                R.mipmap.ic_hotel_select, R.mipmap.ic_my_select};

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        mBottomTabLayout.setTabData(mTabEntities);
        mBottomTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                goToFragment(position);
            }

            @Override
            public void onTabReselect(int position) {
                goToFragment(position);
            }
        });
        goToFragment(0);
    }

    public void goToFragment(int position) {
        switch (position) {
            case 0:
                switchHomePage();
                mCurrentBottom = 0;
                break;
            case 1:
                if (UserManager.getInstance().isLogin(this)){
                    switchSocial();
                    mCurrentBottom = 1;
                }else {
                    goTo(LoginRegisterActivity.class);
                }
                break;
            case 2:

                break;
            case 3:
                if (UserManager.getInstance().isLogin(this)){
                    switchHotel();
                    mCurrentBottom = 3;
                }else {
                    goTo(LoginRegisterActivity.class);
                }
                break;
            case 4:
                if (UserManager.getInstance().isLogin(this)){
                    switchMy();
                    mCurrentBottom = 4;
                }else {
                    goTo(LoginRegisterActivity.class);
                }
                break;
        }
        if (mBottomTabLayout != null) {
            mBottomTabLayout.setCurrentTab(mCurrentBottom);
        }
    }

    //主页
    private void switchHomePage() {
        Fragment fragment = mFragmentManager.findFragmentByTag(HomePageFragment.TAG);
        if (fragment == null) {
            fragment = HomePageFragment.newInstance();
        }
        switchContent(mFragment, fragment, HomePageFragment.TAG);
    }

    //幸福时刻
    private void switchSocial() {
        Fragment fragment = mFragmentManager.findFragmentByTag(SocialFragment.TAG);
        if (fragment == null) {
            fragment = SocialFragment.newInstance();
        }
        switchContent(mFragment, fragment, SocialFragment.TAG);
    }

    //桃园客栈
    private void switchHotel() {
        Fragment fragment = mFragmentManager.findFragmentByTag(HotelFragment.TAG);
        if (fragment == null) {
            fragment = HotelFragment.newInstance();
        }
        switchContent(mFragment, fragment, HotelFragment.TAG);
    }

    //我的
    private void switchMy() {
        Fragment fragment = mFragmentManager.findFragmentByTag(MyFragment.TAG);
        if (fragment == null) {
            fragment = MyFragment.newInstance();
        }
        switchContent(mFragment, fragment, MyFragment.TAG);
    }

    //切换Fragment
    private Fragment mFragment;

    public void switchContent(Fragment from, Fragment to, String tag) {
        if (mFragment != to) {
            mFragment = to;
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (from != null && from.isAdded()) {
                //隐藏当前的fragment
                transaction.hide(from);
            }
            if (!to.isAdded()) {// 先判断是否被add过
                //add下一个到Activity中
                transaction.add(R.id.frameLayout, to, tag);
                transaction.commitAllowingStateLoss();
            } else {
                //显示到Activity中并回复状态
                transaction.show(to).commitAllowingStateLoss();
            }
        }
    }

    //客服
    @Override
    public void onBackPressed() {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        if (fragment!=null){
            if(!fragment.onBackPressed()) {
                finish();
            }
        }
    }

    //发朋友圈回调
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoHelper.handleActivityResult(this, requestCode, resultCode, data, new PhotoHelper.PhotoCallback() {
            @Override
            public void onFinish(String filePath) {
                List<ImageInfo> selectedPhotos = new ArrayList<ImageInfo>();
                selectedPhotos.add(new ImageInfo(filePath, null, null, 0, 0));
                ActivityLauncher.startToPublishActivityWithResult(MainActivity.this,
                        RouterList.PublishActivity.MODE_MULTI,
                        selectedPhotos,
                        RouterList.PublishActivity.requestCode);
            }

            @Override
            public void onError(String msg) {
                UIHelper.ToastMessage(msg);
            }
        });
        if (requestCode == RouterList.PhotoSelectActivity.requestCode && resultCode == RESULT_OK) {
            List<ImageInfo> selectedPhotos = data.getParcelableArrayListExtra(RouterList.PhotoSelectActivity.key_result);
            if (selectedPhotos != null) {
                ActivityLauncher.startToPublishActivityWithResult(this, RouterList.PublishActivity.MODE_MULTI, selectedPhotos, RouterList.PublishActivity.requestCode);
            }
        }

        if (requestCode == RouterList.PublishActivity.requestCode && resultCode == RESULT_OK) {
            RxBus.getInstance().send(new RefreshCircleEvent());
        }
    }

    @Override
    protected void onDestroy() {
        if (RongIM.getInstance()!=null){
            RongIM.getInstance().disconnect();
        }
        super.onDestroy();
    }
}
