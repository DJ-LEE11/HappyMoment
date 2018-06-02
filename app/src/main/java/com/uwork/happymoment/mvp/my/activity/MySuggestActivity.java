package com.uwork.happymoment.mvp.my.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.circle_base_library.interfaces.adapter.TextWatcherAdapter;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MySuggestActivity extends BaseTitleActivity {

    @BindView(R.id.tvSoftware)
    TextView mTvSoftware;
    @BindView(R.id.tvManage)
    TextView mTvManage;
    @BindView(R.id.tvCustomer)
    TextView mTvCustomer;
    @BindView(R.id.tvLogistics)
    TextView mTvLogistics;
    @BindView(R.id.tvOther)
    TextView mTvOther;
    @BindView(R.id.etSuggest)
    EditText mEtSuggest;
    @BindView(R.id.etPhone)
    EditText mEtPhone;
    @BindView(R.id.tvSend)
    TextView mTvSend;

    private String mType;

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
        return R.layout.activity_my_suggest;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("我的建议");
        setBackClick();
        mTvSoftware.setSelected(true);
        mType = "软件问题";
        mEtSuggest.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (!TextUtils.isEmpty(mEtSuggest.getText().toString())){
                    mTvSend.setEnabled(true);
                }else {
                    mTvSend.setEnabled(false);
                }
            }
        });
    }

    private void reset(){
        mTvSoftware.setSelected(false);
        mTvManage.setSelected(false);
        mTvCustomer.setSelected(false);
        mTvLogistics.setSelected(false);
        mTvOther.setSelected(false);
    }

    @OnClick({R.id.tvSoftware, R.id.tvManage, R.id.tvCustomer, R.id.tvLogistics, R.id.tvOther, R.id.tvSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSoftware:
                reset();
                mTvSoftware.setSelected(true);
                mType = "软件问题";
                break;
            case R.id.tvManage:
                reset();
                mTvManage.setSelected(true);
                mType = "管理服务";
                break;
            case R.id.tvCustomer:
                reset();
                mTvCustomer.setSelected(true);
                mType = "售后服务";
                break;
            case R.id.tvLogistics:
                reset();
                mTvLogistics.setSelected(true);
                mType = "物流问题";
                break;
            case R.id.tvOther:
                reset();
                mTvOther.setSelected(true);
                mType = "其他建议";
                break;
            case R.id.tvSend:
                showToast(mType);
                break;
        }
    }
}
