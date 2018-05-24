package com.uwork.happymoment.mvp.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.util.createCode.CreateCodeUtil;

import java.util.List;

import butterknife.BindView;

public class MyCodeActivity extends BaseTitleActivity {

    @BindView(R.id.ivAvatar)
    ImageView mIvAvatar;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tvLocation)
    TextView mTvLocation;
    @BindView(R.id.ivCode)
    ImageView mIvCode;

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
        return R.layout.activity_my_code;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initTitle();
        initView();
    }

    private void initTitle() {
        setTitle("我的二维码");
        setTitleTextColor(R.color.white_color);
        setToolbarBackgroundColor(R.color.circle_title);
        setBackClick(R.mipmap.ic_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        UserBean user = UserManager.getInstance().getUser(this);
        mTvName.setText(user.getNickName());
        mTvLocation.setText(user.getCountry()+" "+user.getCity());
        ImageLoadMnanger.INSTANCE.loadImage(mIvAvatar,user.getAvatar());
        CreateCodeUtil.builder(user.getPhone()).
                backColor(getResources().getColor(R.color.white)).
                codeColor(getResources().getColor(R.color.black)).
                codeSide(1200).
                into(mIvCode);

    }
}
