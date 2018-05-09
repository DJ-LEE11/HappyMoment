package com.uwork.happymoment.mvp.login.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.mvp.MainActivity;
import com.uwork.happymoment.ui.PasswordEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseTitleActivity {


    @BindView(R.id.etPhone)
    EditText mEtPhone;
    @BindView(R.id.etPassWord)
    PasswordEditText mEtPassWord;
    @BindView(R.id.tvLogin)
    TextView mTvLogin;

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
        return R.layout.activity_login;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("登录");
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
        mEtPhone.setText(BuildConfig.phone);
        mEtPassWord.setText(BuildConfig.password);
    }

    private void setButtonEnable() {
        if (TextUtils.isEmpty(mEtPhone.getText().toString()) || TextUtils.isEmpty(mEtPassWord.getText().toString())) {
            mTvLogin.setEnabled(false);
        } else {
            mTvLogin.setEnabled(true);
        }
    }

    @OnClick({R.id.tvLogin, R.id.tvForgetPassword, R.id.tvRegister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvLogin:
                if (validateInput()) {
                    goToFinish(MainActivity.class);
                }
                break;
            case R.id.tvForgetPassword:
                goTo(ForgetPasswordActivity.class);
                break;
            case R.id.tvRegister:
                goTo(RegisterActivity.class);
                break;
        }
    }

    //验证输入框
    private boolean validateInput() {
        if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
            showToast("手机号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mEtPassWord.getText().toString())) {
            showToast("密码不能为空");
            return false;
        }
        if (mEtPhone.getText().toString().length() != 11) {
            showToast("手机格式不正确");
            return false;
        }
        if (mEtPassWord.getText().toString().length() < 6) {
            showToast("密码不能少于6位");
            return false;
        }
        return true;
    }
}
