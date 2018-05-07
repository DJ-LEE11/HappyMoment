package com.uwork.happymoment.activity;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.view.View;
import android.widget.LinearLayout;

import com.uwork.happymoment.R;
import com.uwork.librx.mvp.BaseActivity;
import com.uwork.libtoolbar.ToolbarTitle;

import butterknife.ButterKnife;

/**
 * @author 李栋杰
 * @time 2017/12/6  上午10:36
 * @desc ${TODD}
 */
public abstract class BaseTitleActivity extends BaseActivity {

    // 内容视图
    private View mContentView;
    // 此界面根视图
    private LinearLayout mRootView;
    protected ToolbarTitle mToolbarTitle;

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_base_title;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRootView = (LinearLayout) findViewById(R.id.llRoot);
        int contentResId = getContentResId();
        if (contentResId > 0) {
            mContentView = getLayoutInflater().inflate(contentResId, mRootView, false);
            mRootView.addView(mContentView);
        }
        View view = findViewById(R.id.headerBtnLayout);
        mToolbarTitle = new ToolbarTitle(this, view);
        ButterKnife.bind(this);
        initContentView(savedInstanceState);
    }

    //标题下方的内容
    protected abstract int getContentResId();

    //初始化标题下方的View
    protected abstract void initContentView(Bundle savedInstanceState);

    //设置标题
    protected void setTitle(String title) {
        mToolbarTitle.initTitle(title);
    }

    //设置标题颜色
    protected void setTitleTextColor(@ColorRes int color) {
        mToolbarTitle.setTitleColor(color);
    }

    //设置标题背景
    protected void setToolbarBackgroundColor(@ColorRes int color) {
        mToolbarTitle.setTitleBackgroundColor(color);
    }

    //返回
    protected void setBackClick(){
        mToolbarTitle.initBackClick(R.mipmap.ic_back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void setBackClick(View.OnClickListener navigationOnClickListener){
        mToolbarTitle.initBackClick(R.mipmap.ic_back, navigationOnClickListener);
    }

    protected void setBackClick(int resLeft , View.OnClickListener navigationOnClickListener){
        mToolbarTitle.initBackClick(resLeft, navigationOnClickListener);
    }

    //标题菜单
    protected void setLeftClick(int resLeft, String textLeft, View.OnClickListener left){
        mToolbarTitle.initMenuClick(resLeft,textLeft,left,0,"",null);
    }

    protected void setRightClick(int resRight, String textRight, View.OnClickListener right){
        mToolbarTitle.initMenuClick(0,"",null,resRight,textRight,right);
    }

    protected void setMenuClick(int resLeft, String textLeft, View.OnClickListener left,
                                int resRight, String textRight, View.OnClickListener right){
        mToolbarTitle.initMenuClick(resLeft,textLeft,left,resRight,textRight,right);
    }
}
