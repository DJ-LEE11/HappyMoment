package com.uwork.happymoment.mvp.social.circle.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.view.View;

import com.example.circle_base_ui.util.UIHelper;
import com.example.circle_base_ui.widget.span.ClickableSpanEx;
import com.uwork.happymoment.mvp.social.circle.bean.MomentLikeBean;


/**
 * 点击事件
 */
public class GiveLikeClick extends ClickableSpanEx {
    private static final int DEFAULT_COLOR = 0xff517fae;
    private int color;
    private Context mContext;
    private int textSize;
    private MomentLikeBean mMomentLikeBean;

    private GiveLikeClick() {}


    private GiveLikeClick(Builder builder) {
        super(builder.color,builder.clickBgColor);
        mContext = builder.mContext;
        mMomentLikeBean = builder.mMomentLikeBean;
        this.textSize = builder.textSize;
    }

    @Override
    public void onClickEx(View widget, CharSequence text) {

    }

    @Override
    public void onClick(View widget) {
        if (mMomentLikeBean!=null)
            UIHelper.ToastMessage("当前用户名是： " + mMomentLikeBean.getName() + "   它的ID是： " + mMomentLikeBean.getId());
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setTextSize(textSize);
        ds.setTypeface(Typeface.DEFAULT_BOLD);
    }


    public static class Builder {
        private int color;
        private Context mContext;
        private int textSize=16;
        private MomentLikeBean mMomentLikeBean;
        private int clickBgColor;

        public Builder(Context context, @NonNull MomentLikeBean info) {
            mContext = context;
            mMomentLikeBean=info;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = UIHelper.sp2px(textSize);
            return this;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setClickEventColor(int color){
            this.clickBgColor=color;
            return this;
        }

        public GiveLikeClick build() {
            return new GiveLikeClick(this);
        }
    }
}
