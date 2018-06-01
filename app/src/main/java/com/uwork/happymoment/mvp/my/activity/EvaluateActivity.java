package com.uwork.happymoment.mvp.my.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.circle_base_library.interfaces.adapter.TextWatcherAdapter;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.ui.StarBarView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EvaluateActivity extends BaseTitleActivity {

    @BindView(R.id.ivOrder)
    ImageView mIvOrder;
    @BindView(R.id.tvRoom)
    TextView mTvRoom;
    @BindView(R.id.etEvaluate)
    EditText mEtEvaluate;
    @BindView(R.id.starAccord)
    StarBarView mStarAccord;
    @BindView(R.id.starAttitude)
    StarBarView mStarAttitude;
    @BindView(R.id.tvSend)
    TextView mTvSend;

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
        return R.layout.activity_evaluate;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("发表评价");
        setBackClick();
        mEtEvaluate.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (!TextUtils.isEmpty(mEtEvaluate.getText().toString())){
                    mTvSend.setEnabled(true);
                }else {
                    mTvSend.setEnabled(false);
                }
            }
        });
    }

    @OnClick(R.id.tvSend)
    public void onViewClicked() {
        if (mStarAccord.getStarRating() == 0){
            showToast("请对描述相符进行评分");
            return;
        }
        if (mStarAttitude.getStarRating()  == 0){
            showToast("请对服务态度进行评分");
            return;
        }
        showToast(mStarAccord.getStarRating() +"，"+mStarAttitude.getStarRating() );
    }
}
