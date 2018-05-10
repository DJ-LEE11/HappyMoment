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

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.login.activity.ForgetPasswordActivity;
import com.uwork.happymoment.mvp.login.contract.ILoginContract;
import com.uwork.happymoment.mvp.login.presenter.ILoginPresenter;
import com.uwork.happymoment.ui.PasswordEditText;
import com.uwork.librx.mvp.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.uwork.happymoment.mvp.login.activity.LoginRegisterActivity.RESET_PASSWORD;

/**
 * Created by jie on 2018/5/9.
 */

public class LoginFragment extends BaseFragment implements ILoginContract.View{

    public static final String TAG = LoginFragment.class.getSimpleName();

    private static LoginFragment fragment;
    @BindView(R.id.etPhone)
    EditText mEtPhone;
    @BindView(R.id.etPassWord)
    PasswordEditText mEtPassWord;
    @BindView(R.id.etShareCode)
    EditText mEtShareCode;
    @BindView(R.id.tvLogin)
    TextView mTvLogin;
    Unbinder unbinder;

    private ILoginPresenter mILoginPresenter;

    public static LoginFragment newInstance() {
        if (null == fragment) {
            fragment = new LoginFragment();
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
        mILoginPresenter = new ILoginPresenter(getContext());
        list.add(mILoginPresenter);
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
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

    @OnClick({R.id.tvForgetPassword, R.id.tvLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvForgetPassword:
                goTo(ForgetPasswordActivity.class,RESET_PASSWORD);
                break;
            case R.id.tvLogin:
                if (validateInput()) {
                    mILoginPresenter.login(mEtPhone.getText().toString(), mEtPassWord.getText().toString());
                }
                break;
        }
    }

    @Override
    public void loginSuccess() {
        showToast("登录成功");
        getActivity().finish();
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
        if (mEtPhone.getText().toString().length() != 11 || !mEtPhone.getText().toString().startsWith("1")) {
            showToast("手机格式不正确");
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
        super.onDestroyView();
        unbinder.unbind();
    }
}
