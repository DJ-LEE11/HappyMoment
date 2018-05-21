package com.uwork.happymoment.mvp.social.circle.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.circle_base_library.interfaces.MultiClickListener;
import com.example.circle_base_ui.widget.common.TitleBar;
import com.uwork.happymoment.R;

/**
 * Created by jie on 2018/5/21.
 */

public abstract class PublishTitleActivity extends PublishBaseActivity{
    protected TitleBar titleBar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initTitlebar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initTitlebar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        initTitlebar();
    }

    private void initTitlebar() {
        if (titleBar == null) titleBar = (TitleBar) findViewById(R.id.title_bar_view);
        if (titleBar != null) {
            titleBar.setOnClickListener(new MultiClickListener() {
                @Override
                public void onSingleClick() {
                    onTitleSingleClick();
                }

                @Override
                public void onDoubleClick() {
                    onTitleDoubleClick();
                }
            });
            titleBar.setOnTitleBarClickListener(onTitleClickListener);
        }
    }


    // titlebar相关事件
    private TitleBar.OnTitleBarClickListener onTitleClickListener = new TitleBar.OnTitleBarClickListener() {

        @Override
        public boolean onLeftClick(View v, boolean isLongClick) {
            if (!isLongClick) {
                onTitleLeftClick();
                return false;
            } else {
                return onTitleLeftLongClick();
            }
        }

        @Override
        public boolean onRightClick(View v, boolean isLongClick) {
            if (!isLongClick) {
                onTitleRightClick();
                return false;
            } else {
                return onTitleRightLongClick();
            }
        }
    };

    public boolean onTitleLeftLongClick() {
        return false;
    }

    public boolean onTitleRightLongClick() {
        return false;
    }

    public void onTitleLeftClick() {
        finish();
    }

    public void onTitleRightClick() {
    }

    public void onTitleDoubleClick() {
    }

    public void onTitleSingleClick() {
    }

    public void setTitle(int resId) {
        if (titleBar != null && resId != 0) {
            titleBar.setTitle(resId);
        }
    }

    public void setTitle(String title) {
        if (titleBar != null && !TextUtils.isEmpty(title)) {
            titleBar.setTitle(title);
        }
    }

    public void setTitleMode(@TitleBar.TitleBarMode int mode) {
        if (titleBar != null) {
            titleBar.setTitleBarMode(mode);
        }
    }

    public void setTitleRightText(String text) {
        if (titleBar != null) {
            titleBar.setRightText(text);
        }
    }

    public void setTitleRightIcon(int resid) {
        if (titleBar != null) {
            titleBar.setRightIcon(resid);
        }
    }

    public void setTitleLeftText(String text) {
        if (titleBar != null) {
            titleBar.setLeftText(text);
        }
    }

    public void setTitleLeftIcon(int resid) {
        if (titleBar != null) {
            titleBar.setLeftIcon(resid);
        }
    }

    public void setLeftTextColor(int color) {
        if (titleBar != null) {
            titleBar.setLeftTextColor(color);
        }
    }

    public void setRightTextColor(int color) {
        if (titleBar != null) {
            titleBar.setRightTextColor(color);
        }
    }

    public void setTitleBarBackground(int color) {
        if (titleBar != null) {
            titleBar.setTitleBarBackground(color);
        }
    }


    public String getBarTitle() {
        if (titleBar != null) {
            return titleBar.getTitleView().getText().toString();
        }
        return null;
    }
}
