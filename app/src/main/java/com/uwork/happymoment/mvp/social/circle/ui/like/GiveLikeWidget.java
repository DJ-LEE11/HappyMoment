package com.uwork.happymoment.mvp.social.circle.ui.like;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;
import android.widget.TextView;

import com.example.circle_base_ui.widget.span.ClickableSpanEx;
import com.example.circle_base_ui.widget.span.CustomImageSpan;
import com.example.circle_base_ui.widget.span.SpannableStringBuilderCompat;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.social.circle.bean.MomentLikeBean;

import java.util.List;


/**
 * 点赞显示控件
 */
public class GiveLikeWidget extends TextView {
    private static final String TAG = "GiveLikeWidget";

    //点赞名字展示的默认颜色
    private int textColor = 0xff517fae;
    //点赞列表心心默认图标
    private int iconRes = R.drawable.icon_like;
    //默认字体大小
    private int textSize = 14;
    //默认点击背景
    private int clickBg = 0x00000000;

    private static final LruCache<String, SpannableStringBuilderCompat> praiseCache
            = new LruCache<String, SpannableStringBuilderCompat>(50) {
        @Override
        protected int sizeOf(String key, SpannableStringBuilderCompat value) {
            return 1;
        }
    };

    public GiveLikeWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GiveLikeWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GiveLikeWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PraiseWidget);
        textColor = a.getColor(R.styleable.PraiseWidget_font_color, 0xff517fae);
        textSize = a.getDimensionPixelSize(R.styleable.PraiseWidget_font_size, 14);
        clickBg = a.getColor(R.styleable.PraiseWidget_click_bg_color, 0x00000000);
        iconRes = a.getResourceId(R.styleable.PraiseWidget_like_icon, R.drawable.icon_like);
        a.recycle();
        //如果不设置，clickableSpan不能响应点击事件
        this.setMovementMethod(LinkMovementMethod.getInstance());
        setOnTouchListener(new ClickableSpanEx.ClickableSpanSelector());
        setTextSize(textSize);
    }

    public void setDatas(List<MomentLikeBean> data) {
        createSpanStringBuilder(data);
    }


    private void createSpanStringBuilder(List<MomentLikeBean> data) {
        if (data == null || data.size() == 0) return;
        String key = Integer.toString(data.hashCode() + data.size());
        SpannableStringBuilderCompat spanStrBuilder = praiseCache.get(key);
        if (spanStrBuilder == null) {
            CustomImageSpan icon = new CustomImageSpan(getContext(), iconRes);
            //因为spanstringbuilder不支持直接append span，所以通过spanstring转换
            SpannableString iconSpanStr = new SpannableString(" ");
            iconSpanStr.setSpan(icon, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            spanStrBuilder = new SpannableStringBuilderCompat(iconSpanStr);
            //给出两个空格，点赞图标后
            spanStrBuilder.append(" ");
            for (int i = 0; i < data.size(); i++) {
                GiveLikeClick giveLikeClick = new GiveLikeClick.Builder(getContext(), data.get(i))
                        .setTextSize(textSize)
                        .setColor(textColor)
                        .setClickEventColor(clickBg)
                        .build();
                try {
                    spanStrBuilder.append(data.get(i).getName(), giveLikeClick, 0);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Log.e(TAG, "praiseUserInfo是空的哦");
                }
                if (i != data.size() - 1) spanStrBuilder.append(", ");
                else spanStrBuilder.append("\0");
            }
            praiseCache.put(key, spanStrBuilder);
        }
        setText(spanStrBuilder);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        praiseCache.evictAll();
        if (praiseCache.size() == 0) {
            Log.d(TAG, "clear cache success!");
        }
    }
}
