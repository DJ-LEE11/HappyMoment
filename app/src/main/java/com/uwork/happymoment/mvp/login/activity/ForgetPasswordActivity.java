package com.uwork.happymoment.mvp.login.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.mvp.login.contract.IResetPasswordContract;
import com.uwork.happymoment.mvp.login.presenter.IResetPasswordPresenter;
import com.uwork.happymoment.ui.PasswordEditText;
import com.uwork.happymoment.ui.SendCodeButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseTitleActivity implements IResetPasswordContract.View {


    @BindView(R.id.etPhone)
    EditText mEtPhone;
    @BindView(R.id.etCode)
    EditText mEtCode;
    @BindView(R.id.btnSendCode)
    SendCodeButton mBtnSendCode;
    @BindView(R.id.etPassWord)
    PasswordEditText mEtPassWord;
    @BindView(R.id.tvLogin)
    TextView mTvLogin;

    private IResetPasswordPresenter mIResetPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mIResetPasswordPresenter = new IResetPasswordPresenter(this);
        list.add(mIResetPasswordPresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("忘记密码");
        setBackClick();
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
            mTvLogin.setEnabled(false);
        } else {
            mTvLogin.setEnabled(true);
        }
    }

    @OnClick({R.id.btnSendCode, R.id.tvLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSendCode:
                if (validatePhone()) {
                    mBtnSendCode.startTime();
                }
                break;
            case R.id.tvLogin:
                if (validateInput()) {
//                    mIResetPasswordPresenter.resetPassword(mEtPhone.getText().toString()
//                            , mEtCode.getText().toString()
//                            , mEtPassWord.getText().toString());
                    resetPasswordSuccess();
                }
                break;
        }
    }

    @Override
    public void resetPasswordSuccess() {
        setResult(RESULT_OK);
        finish();
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
        if (TextUtils.isEmpty(mEtCode.getText().toString())) {
            showToast("验证码不能为空");
            return false;
        }
        if (mEtCode.getText().toString().length() != 6) {
            showToast("验证码不够六位");
            return false;
        }
        if (mEtPassWord.getText().toString().length() < 6) {
            showToast("密码不能少于6位");
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        mBtnSendCode.close();
        super.onDestroy();
    }
}
