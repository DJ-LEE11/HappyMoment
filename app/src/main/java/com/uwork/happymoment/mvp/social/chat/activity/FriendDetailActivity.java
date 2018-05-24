package com.uwork.happymoment.mvp.social.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.kw.rxbus.RxBus;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.event.RemarkNameEvent;
import com.uwork.happymoment.manager.IMRongManager;
import com.uwork.happymoment.mvp.social.chat.adapter.FriendDetailPhoneAdapter;
import com.uwork.happymoment.mvp.social.chat.bean.FriendDetailBean;
import com.uwork.happymoment.mvp.social.chat.contract.IGetFriendDetailContract;
import com.uwork.happymoment.mvp.social.chat.presenter.IGetFriendDetailPresenter;
import com.uwork.happymoment.mvp.social.circle.activity.OtherHomeActivity;
import com.uwork.libutil.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static com.uwork.happymoment.mvp.social.chat.activity.RemarkNameActivity.NICK_NAME;
import static com.uwork.happymoment.mvp.social.chat.activity.RemarkNameActivity.PHONE;
import static com.uwork.happymoment.mvp.social.chat.activity.RemarkNameActivity.REMARK_NAME;
import static com.uwork.happymoment.mvp.social.circle.activity.OtherHomeActivity.FRIEND_NAME;
import static com.uwork.happymoment.mvp.social.circle.activity.OtherHomeActivity.USER_ID;

public class FriendDetailActivity extends BaseTitleActivity implements IGetFriendDetailContract.View{

    public static final String FRIEND_ID = "FRIEND_ID";
    public static final String AVATAR = "AVATAR";

    @BindView(R.id.ivAvatar)
    ImageView mIvAvatar;
    @BindView(R.id.tvNameFirst)
    TextView mTvNameFirst;
    @BindView(R.id.tvAccount)
    TextView mTvAccount;
    @BindView(R.id.tvNameSecond)
    TextView mTvNameSecond;
    @BindView(R.id.llSetRemark)
    LinearLayout mLlSetRemark;
    @BindView(R.id.rvPhone)
    RecyclerView mRvPhone;
    @BindView(R.id.tvLocation)
    TextView mTvLocation;
    @BindView(R.id.rlLocation)
    RelativeLayout mRlLocation;
    @BindView(R.id.ivPhoto1)
    ImageView mIvPhoto1;
    @BindView(R.id.ivPhoto2)
    ImageView mIvPhoto2;
    @BindView(R.id.ivPhoto3)
    ImageView mIvPhoto3;
    @BindView(R.id.ivPhoto4)
    ImageView mIvPhoto4;
    @BindView(R.id.rlPhoto)
    RelativeLayout mRlPhoto;
    @BindView(R.id.tvSendMessage)
    TextView mTvSendMessage;

    private String mFriendId;
    private String mAvatar;
    private String mRemarkName;
    private String mNickName;
    private String mPhone;
    private FriendDetailPhoneAdapter mFriendDetailPhoneAdapter;
    private IGetFriendDetailPresenter mIGetFriendDetailPresenter;
    private CompositeDisposable mDisposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null){
            list = new ArrayList();
        }
        mIGetFriendDetailPresenter = new IGetFriendDetailPresenter(this);
        list.add(mIGetFriendDetailPresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_friend_detail;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("详细信息");
        setBackClick();
        initEvent();
    }

    //设置完备注后刷新
    private void initEvent() {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(RxBus.getInstance().register(RemarkNameEvent.class, new Consumer<RemarkNameEvent>() {
            @Override
            public void accept(RemarkNameEvent remarkNameEvent) throws Exception {
                mIGetFriendDetailPresenter.getFriendDetail(mFriendId);
            }
        }));
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        mFriendId = getIntent().getStringExtra(FRIEND_ID);
        mAvatar = getIntent().getStringExtra(AVATAR);
        mIGetFriendDetailPresenter.getFriendDetail(mFriendId);
    }

    @Override
    public void showDetail(FriendDetailBean friendDetailBean) {
        ImageLoadMnanger.INSTANCE.loadImage(mIvAvatar,mAvatar);
        mRemarkName = friendDetailBean.getRemarksName();
        mNickName = friendDetailBean.getNickName();
        if (!TextUtils.isEmpty(mRemarkName)){
            mTvNameFirst.setText(mRemarkName);
            mTvNameSecond.setText("昵称："+mNickName);
        }else {
            mTvNameFirst.setText(mNickName);
            mTvNameSecond.setVisibility(View.GONE);
        }
        mTvAccount.setText("微信号："+ friendDetailBean.getAccount());
        mTvLocation.setText(friendDetailBean.getProvince()+friendDetailBean.getCity());
        List<String> images = friendDetailBean.getImages();
        if (images!=null && images.size()>0){
            for (int i=0;i<images.size();i++){
                switch (i){
                    case 0:
                        ImageLoadMnanger.INSTANCE.loadImage(mIvPhoto1,images.get(i));
                        break;
                    case 1:
                        ImageLoadMnanger.INSTANCE.loadImage(mIvPhoto2,images.get(i));
                        break;
                    case 2:
                        ImageLoadMnanger.INSTANCE.loadImage(mIvPhoto3,images.get(i));
                        break;
                    case 3:
                        ImageLoadMnanger.INSTANCE.loadImage(mIvPhoto4,images.get(i));
                        break;
                }
            }
        }
        mPhone = friendDetailBean.getRemarkPhone();
        if (!TextUtils.isEmpty(mPhone)){
            String[] phonesTemp = mPhone.split(",");
            List<String> phones = new ArrayList<>();
            for (String temp:phonesTemp){
                phones.add(temp);
            }
            mRvPhone.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mFriendDetailPhoneAdapter = new FriendDetailPhoneAdapter(phones);
            mRvPhone.setAdapter(mFriendDetailPhoneAdapter);
        }
    }

    @Override
    public void getDetailFail() {
        showToast("获取信息失败");
        finish();
    }

    @OnClick({R.id.llSetRemark, R.id.rlPhoto, R.id.tvSendMessage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llSetRemark:
                new IntentUtils.Builder(FriendDetailActivity.this)
                        .to(RemarkNameActivity.class)
                        .putExtra(PHONE,mPhone)
                        .putExtra(REMARK_NAME,mRemarkName)
                        .putExtra(NICK_NAME,mNickName)
                        .putExtra(FRIEND_ID,mFriendId)
                        .putExtra(AVATAR,mAvatar)
                        .build()
                        .start();
                break;
            case R.id.rlPhoto:
                new IntentUtils.Builder(FriendDetailActivity.this)
                        .to(OtherHomeActivity.class)
                        .putExtra(USER_ID,Integer.valueOf(mFriendId))
                        .putExtra(FRIEND_NAME,mTvNameFirst.getText().toString())
                        .build()
                        .start();

                break;
            case R.id.tvSendMessage:
                String title = TextUtils.isEmpty(mRemarkName) ? mNickName : mRemarkName;
                IMRongManager.privateChat(FriendDetailActivity.this, mFriendId + "", title);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
        super.onDestroy();
    }
}
