package com.uwork.happymoment.mvp.login.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.login.contract.IRegisterContract;
import com.uwork.happymoment.mvp.login.contract.ISendMessageContract;
import com.uwork.happymoment.mvp.login.presenter.IRegisterPresenter;
import com.uwork.happymoment.mvp.login.presenter.ISendMessagePresenter;
import com.uwork.happymoment.ui.PasswordEditText;
import com.uwork.happymoment.ui.SendCodeButton;
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

public class RegisterFragment extends BaseFragment implements ISendMessageContract.View, IRegisterContract.View {

    public static final String TAG = RegisterFragment.class.getSimpleName();

    private static RegisterFragment fragment;
    @BindView(R.id.etPhone)
    EditText mEtPhone;
    @BindView(R.id.etCode)
    EditText mEtCode;
    @BindView(R.id.btnSendCode)
    SendCodeButton mBtnSendCode;
    @BindView(R.id.etPassWord)
    PasswordEditText mEtPassWord;
    @BindView(R.id.etShareCode)
    EditText mEtShareCode;
    @BindView(R.id.tvRegister)
    TextView mTvRegister;
    Unbinder unbinder;

    private ISendMessagePresenter mISendMessagePresenter;
    private IRegisterPresenter mIRegisterPresenter;

    public static RegisterFragment newInstance() {
        if (null == fragment) {
            fragment = new RegisterFragment();
        }
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mISendMessagePresenter = new ISendMessagePresenter(getContext());
        mIRegisterPresenter = new IRegisterPresenter(getContext());
        list.add(mISendMessagePresenter);
        list.add(mIRegisterPresenter);
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setButtonEnable();
            }
        });

        mEtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setButtonEnable();
            }
        });

        mEtPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setButtonEnable();
            }
        });

        mEtPassWord.setText("");
    }

    private void setButtonEnable() {
        if (TextUtils.isEmpty(mEtPhone.getText().toString())
                || TextUtils.isEmpty(mEtCode.getText().toString())
                || TextUtils.isEmpty(mEtPassWord.getText().toString())) {
            mTvRegister.setEnabled(false);
        } else {
            mTvRegister.setEnabled(true);
        }
    }

    @OnClick({R.id.btnSendCode, R.id.tvRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSendCode:
                if (validatePhone()) {
                    mISendMessagePresenter.sendMessage(mEtPhone.getText().toString());
                }
                break;
            case R.id.tvRegister:
                if (validateInput()) {
                    mIRegisterPresenter.register(mEtCode.getText().toString()
                            , mEtPassWord.getText().toString()
                            , mEtPhone.getText().toString()
                            , mEtShareCode.getText().toString());
                }
                break;
        }
    }

    @Override
    public void startRegisterCountDown() {
        mBtnSendCode.startTime();
        showToast("验证码发送成功");
    }

    @Override
    public void registerSuccess() {
        mBtnSendCode.resumeTime();
        mEtCode.setText("");
        mEtPassWord.setText("");
        mEtPhone.setText("");
        mEtShareCode.setText("");
        showToast("注册成功");
    }

    private boolean validatePhone() {
        if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
            showToast("手机号不能为空");
            return false;
        }
        if (mEtPhone.getText().toString().length() != 11 || !mEtPhone.getText().toString().startsWith("1")) {
            showToast("手机格式不正确");
            return false;
        }
        return true;
    }

    //验证输入框
    private boolean validateInput() {
        if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
            showToast("手机号不能为空");
            return false;
        }
        if (mEtPhone.getText().toString().length() != 11 || !mEtPhone.getText().toString().startsWith("1")) {
            showToast("手机格式不正确");
            return false;
        }
        if (mEtCode.getText().toString().length() != 6) {
            showToast("验证码不够六位");
            return false;
        }
        if (TextUtils.isEmpty(mEtCode.getText().toString())) {
            showToast("验证码不能为空");
            return false;
        }
        if (mEtPassWord.getText().toString().length() < 6) {
            showToast("密码不能少于6位");
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        mBtnSendCode.close();
        super.onDestroyView();
        unbinder.unbind();
    }
}
