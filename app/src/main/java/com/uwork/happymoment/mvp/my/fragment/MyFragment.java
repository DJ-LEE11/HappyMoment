package com.uwork.happymoment.mvp.my.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.kw.rxbus.RxBus;
import com.uwork.happymoment.R;
import com.uwork.happymoment.event.RefreshUserInfoEvent;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.mvp.my.activity.HappyValueActivity;
import com.uwork.happymoment.mvp.my.activity.InterestMeActivity;
import com.uwork.happymoment.mvp.my.activity.MapActivity;
import com.uwork.happymoment.mvp.my.activity.MyActivityActivity;
import com.uwork.happymoment.mvp.my.activity.MyEvaluateActivity;
import com.uwork.happymoment.mvp.my.activity.MyFileActivity;
import com.uwork.happymoment.mvp.my.activity.MyIntegralActivity;
import com.uwork.happymoment.mvp.my.activity.MyOrderActivity;
import com.uwork.happymoment.mvp.my.activity.MyStewardActivity;
import com.uwork.happymoment.mvp.my.activity.MySuggestActivity;
import com.uwork.happymoment.mvp.my.activity.MyWalletActivity;
import com.uwork.happymoment.mvp.my.activity.PersonInfoActivity;
import com.uwork.happymoment.ui.CircleImageView;
import com.uwork.librx.mvp.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by jie on 2018/5/9.
 */

public class MyFragment extends BaseFragment {

    public static final String TAG = MyFragment.class.getSimpleName();

    private static MyFragment fragment;
    Unbinder unbinder;
    @BindView(R.id.ivAvatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.tvName)
    TextView mTvName;
    private CompositeDisposable mDisposables;


    public static MyFragment newInstance() {
        if (null == fragment) {
            fragment = new MyFragment();
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
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        refreshUserInfo();
        initEvent();
        return view;
    }

    //修改完信息后刷新
    private void initEvent() {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(RxBus.getInstance().register(RefreshUserInfoEvent.class, new Consumer<RefreshUserInfoEvent>() {
            @Override
            public void accept(RefreshUserInfoEvent refreshUserInfoEvent) throws Exception {
                refreshUserInfo();
            }
        }));
    }

    private void refreshUserInfo() {
        UserBean user = UserManager.getInstance().getUser(getContext());
        ImageLoadMnanger.INSTANCE.loadImage(mIvAvatar, user.getAvatar());
        mTvName.setText(user.getNickName());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llAccountInfo, R.id.llMyIntegral, R.id.llHappyValue, R.id.llMyWallet, R.id.llMyOrder, R.id.llMyEvaluate, R.id.llMySuggest, R.id.llMyFile, R.id.llMyActivity, R.id.llMySteward, R.id.llInterestMe})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llAccountInfo:
                goTo(PersonInfoActivity.class);
                break;
            case R.id.llMyIntegral:
                goTo(MyIntegralActivity.class);
                break;
            case R.id.llHappyValue:
                goTo(HappyValueActivity.class);
                break;
            case R.id.llMyWallet:
                goTo(MyWalletActivity.class);
                break;
            case R.id.llMyOrder:
                goTo(MyOrderActivity.class);
                break;
            case R.id.llMyEvaluate:
                goTo(MyEvaluateActivity.class);
                break;
            case R.id.llMySuggest:
                goTo(MySuggestActivity.class);
                break;
            case R.id.llMyFile:
                goTo(MyFileActivity.class);
                break;
            case R.id.llMyActivity:
                goTo(MyActivityActivity.class);
                break;
            case R.id.llMySteward:
                goTo(MyStewardActivity.class);
                break;
            case R.id.llInterestMe:
                goTo(MapActivity.class);
                break;
        }
    }
}
