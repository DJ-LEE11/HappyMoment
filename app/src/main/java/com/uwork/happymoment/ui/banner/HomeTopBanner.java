package com.uwork.happymoment.ui.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.main.bean.BannerBean;
import com.uwork.happymoment.util.ViewFindUtils;

public class HomeTopBanner extends BaseIndicatorBanner<BannerBean, HomeTopBanner> {
    private ColorDrawable colorDrawable;

    public HomeTopBanner(Context context) {
        this(context, null, 0);
    }

    public HomeTopBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeTopBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        colorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
        final BannerBean item = mDatas.get(position);

    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(mContext, R.layout.banner_simple_image, null);
        ImageView iv = ViewFindUtils.find(inflate, R.id.ivSimpleBanner);

        final BannerBean item = mDatas.get(position);
        int itemWidth = mDisplayMetrics.widthPixels;
        int itemHeight = (int) (itemWidth *0.5625);
        iv.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, itemHeight));

        String imgUrl = item.getPicture();

        if (!TextUtils.isEmpty(imgUrl)) {
            ImageLoadMnanger.INSTANCE.loadImage(iv, imgUrl);
        } else {
            iv.setImageDrawable(colorDrawable);
        }

        return inflate;
    }
}
