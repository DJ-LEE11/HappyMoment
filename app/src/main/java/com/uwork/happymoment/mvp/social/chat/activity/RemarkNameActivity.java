package com.uwork.happymoment.mvp.social.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kw.rxbus.RxBus;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.event.RemarkNameEvent;
import com.uwork.happymoment.mvp.social.chat.contract.IUpdateFriendInfoContract;
import com.uwork.happymoment.mvp.social.chat.presenter.IUpdateFriendInfoPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.uwork.happymoment.mvp.social.chat.activity.FriendDetailActivity.FRIEND_ID;

public class RemarkNameActivity extends BaseTitleActivity implements IUpdateFriendInfoContract.View{

    public static final String PHONE = "PHONE";
    public static final String REMARK_NAME = "REMARK_NAME";
    public static final String NICK_NAME = "NICK_NAME";
    @BindView(R.id.etRemarkName)
    EditText mEtRemarkName;
    @BindView(R.id.iv1)
    ImageView mIv1;
    @BindView(R.id.etPhoto1)
    EditText mEtPhoto1;
    @BindView(R.id.rlPhoto1)
    RelativeLayout mRlPhoto1;
    @BindView(R.id.iv2)
    ImageView mIv2;
    @BindView(R.id.etPhoto2)
    EditText mEtPhoto2;
    @BindView(R.id.rlPhoto2)
    RelativeLayout mRlPhoto2;
    @BindView(R.id.iv3)
    ImageView mIv3;
    @BindView(R.id.etPhoto3)
    EditText mEtPhoto3;
    @BindView(R.id.rlPhoto3)
    RelativeLayout mRlPhoto3;
    @BindView(R.id.iv4)
    ImageView mIv4;
    @BindView(R.id.etPhoto4)
    EditText mEtPhoto4;
    @BindView(R.id.rlPhoto4)
    RelativeLayout mRlPhoto4;

    private String mPhone;
    private String mNickName;
    private String mRemarkName;
    private String mModifyRemarkName;
    private String mFriendId;
    private List<String> mPhoneList = new ArrayList<>();
    private IUpdateFriendInfoPresenter mIUpdateFriendInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null){
            list = new ArrayList();
        }
        mIUpdateFriendInfoPresenter = new IUpdateFriendInfoPresenter(this);
        list.add(mIUpdateFriendInfoPresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_remark_name;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initTitle();
    }

    private void initTitle() {
        setTitle("设置备注");
        setMenuClick(0, "取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, 0, "完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsModifyRemarkName() || mIsModifyPhone()) {
                    mModifyRemarkName = mEtRemarkName.getText().toString();
                    if (!TextUtils.isEmpty(mModifyRemarkName)){
                        mIUpdateFriendInfoPresenter.updateFriendInfo(mFriendId,getModifyPhoneStr(),mEtRemarkName.getText().toString());
                    }
                } else {
                    showToast("请先设置");
                }
            }
        });
    }

    //修改成功
    @Override
    public void updateFriendInfoSuccess() {
        RxBus.getInstance().send(new RemarkNameEvent());
        showToast("修改成功");
        finish();
    }

    private boolean mIsModifyRemarkName(){
        if (TextUtils.isEmpty(mRemarkName)){
            if (TextUtils.isEmpty(mEtRemarkName.getText().toString())){
                return false;
            }else {
                return true;
            }
        }else {
            if (mRemarkName.equals(mEtRemarkName.getText().toString())){
                return false;
            }else {
                return true;
            }
        }
    }

    private boolean mIsModifyPhone(){
        if (TextUtils.isEmpty(mPhone)){
            if (mPhoneList.size()>0){
                return true;
            }else {
                return false;
            }
        }else {
            if (getModifyPhoneStr().equals(mPhone)) {
                return false;
            }else {
                return true;
            }
        }
    }

    private String  getModifyPhoneStr(){
        String modifyPhone = "";
        for (int i=0;i<mPhoneList.size();i++){
            if (i == mPhoneList.size()-1){
                modifyPhone = modifyPhone + mPhoneList.get(i);
            }else {
                modifyPhone = modifyPhone + mPhoneList.get(i)+",";
            }
        }
        return modifyPhone;
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        mFriendId = getIntent().getStringExtra(FRIEND_ID);
        mPhone = getIntent().getStringExtra(PHONE);
        mNickName = getIntent().getStringExtra(NICK_NAME);
        mRemarkName = getIntent().getStringExtra(REMARK_NAME);
        if (!TextUtils.isEmpty(mRemarkName)) {
            mEtRemarkName.setText(mRemarkName);
        }
        if (!TextUtils.isEmpty(mPhone)) {
            String[] phonesTemp = mPhone.split(",");
            for (String temp : phonesTemp) {
                mPhoneList.add(temp);
            }
        }
        initPhoneTextView();
    }

    private boolean mIsAdd1;
    private boolean mIsAdd2;
    private boolean mIsAdd3;
    private boolean mIsAdd4;

    private void initPhoneTextView() {
        mRlPhoto1.setVisibility(View.GONE);
        mRlPhoto2.setVisibility(View.GONE);
        mRlPhoto3.setVisibility(View.GONE);
        mRlPhoto4.setVisibility(View.GONE);
        mIsAdd1 = false;
        mIsAdd2 = false;
        mIsAdd3 = false;
        mIsAdd4 = false;
        switch (mPhoneList.size()) {
            case 0:
                setPhoneTextView1(mIsAdd1, "");
                break;
            case 1:
                mIsAdd1 = true;
                setPhoneTextView1(mIsAdd1, mPhoneList.get(0));
                setPhoneTextView2(mIsAdd2, "");
                break;
            case 2:
                mIsAdd1 = true;
                mIsAdd2 = true;
                setPhoneTextView1(mIsAdd1, mPhoneList.get(0));
                setPhoneTextView2(mIsAdd2, mPhoneList.get(1));
                setPhoneTextView3(mIsAdd3, "");
                break;
            case 3:
                mIsAdd1 = true;
                mIsAdd2 = true;
                mIsAdd3 = true;
                setPhoneTextView1(mIsAdd1, mPhoneList.get(0));
                setPhoneTextView2(mIsAdd2, mPhoneList.get(1));
                setPhoneTextView3(mIsAdd3, mPhoneList.get(2));
                setPhoneTextView4(mIsAdd4, "");
                break;
            case 4:
                mIsAdd1 = true;
                mIsAdd2 = true;
                mIsAdd3 = true;
                mIsAdd4 = true;
                setPhoneTextView1(mIsAdd1, mPhoneList.get(0));
                setPhoneTextView2(mIsAdd2, mPhoneList.get(1));
                setPhoneTextView3(mIsAdd3, mPhoneList.get(2));
                setPhoneTextView4(mIsAdd4, mPhoneList.get(3));
                break;
        }
    }

    private void setPhoneTextView1(boolean mIsAdd, String Phone) {
        mRlPhoto1.setVisibility(View.VISIBLE);
        if (!mIsAdd) {
            mIv1.setImageResource(R.mipmap.ic_add);
            mEtPhoto1.setText("");
        } else {
            mIv1.setImageResource(R.mipmap.ic_reduce);
            mEtPhoto1.setText(Phone);
        }

    }

    private void setPhoneTextView2(boolean mIsAdd, String Phone) {
        mRlPhoto2.setVisibility(View.VISIBLE);
        if (!mIsAdd) {
            mIv2.setImageResource(R.mipmap.ic_add);
            mEtPhoto2.setText("");
        } else {
            mIv2.setImageResource(R.mipmap.ic_reduce);
            mEtPhoto2.setText(Phone);
        }

    }

    private void setPhoneTextView3(boolean mIsAdd, String Phone) {
        mRlPhoto3.setVisibility(View.VISIBLE);
        if (!mIsAdd) {
            mIv3.setImageResource(R.mipmap.ic_add);
            mEtPhoto3.setText("");
        } else {
            mIv3.setImageResource(R.mipmap.ic_reduce);
            mEtPhoto3.setText(Phone);
        }
    }

    private void setPhoneTextView4(boolean mIsAdd, String Phone) {
        mRlPhoto4.setVisibility(View.VISIBLE);
        if (!mIsAdd) {
            mIv4.setImageResource(R.mipmap.ic_add);
            mEtPhoto4.setText("");
        } else {
            mIv4.setImageResource(R.mipmap.ic_reduce);
            mEtPhoto4.setText(Phone);
        }
    }

    @OnClick({R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv1:
                if (!mIsAdd1) {
                    if (!TextUtils.isEmpty(mEtPhoto1.getText().toString())) {
                        mPhoneList.add(mEtPhoto1.getText().toString());
                        initPhoneTextView();
                    } else {
                        showToast("请输入电话号码");
                    }
                } else {
                    mPhoneList.remove(mEtPhoto1.getText().toString());
                    initPhoneTextView();
                }
                break;
            case R.id.iv2:
                if (!mIsAdd2) {
                    if (!TextUtils.isEmpty(mEtPhoto2.getText().toString())) {
                        mPhoneList.add(mEtPhoto2.getText().toString());
                        initPhoneTextView();
                    } else {
                        showToast("请输入电话号码");
                    }
                } else {
                    mPhoneList.remove(mEtPhoto2.getText().toString());
                    initPhoneTextView();
                }
                break;
            case R.id.iv3:
                if (!mIsAdd3) {
                    if (!TextUtils.isEmpty(mEtPhoto3.getText().toString())) {
                        mPhoneList.add(mEtPhoto3.getText().toString());
                        initPhoneTextView();
                    } else {
                        showToast("请输入电话号码");
                    }
                } else {
                    mPhoneList.remove(mEtPhoto3.getText().toString());
                    initPhoneTextView();
                }
                break;
            case R.id.iv4:
                if (!mIsAdd4) {
                    if (!TextUtils.isEmpty(mEtPhoto4.getText().toString())) {
                        mPhoneList.add(mEtPhoto4.getText().toString());
                        mIsAdd4 = false;
                        mIv4.setImageResource(R.mipmap.ic_reduce);
                    } else {
                        showToast("请输入电话号码");
                    }
                } else {
                    mPhoneList.remove(mEtPhoto4.getText().toString());
                    initPhoneTextView();
                }
                break;
        }
    }
}
