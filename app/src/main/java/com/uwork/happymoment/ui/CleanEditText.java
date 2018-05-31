package com.uwork.happymoment.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.uwork.happymoment.R;


/**
 * 清空编辑框
 */
public class CleanEditText extends AppCompatEditText {


    // 模式的显示图标
    private int mCleanIcon = R.mipmap.ic_close;


    private Drawable mDrawableSide; // 显示隐藏指示器

    public CleanEditText(Context context) {
        this(context, null);
    }

    public CleanEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFields(attrs, 0);
    }

    @TargetApi(21)
    public CleanEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFields(attrs, defStyleAttr);
    }

    // 初始化布局
    public void initFields(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            // 获取属性信息
            TypedArray styles = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CleanEditText, defStyleAttr, 0);
            try {
                // 根据参数, 设置Icon
                mCleanIcon = styles.getResourceId(R.styleable.CleanEditText_iconClean, mCleanIcon);
            } finally {
                styles.recycle();
            }
        }

        // 密码状态
//        setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_CLASS_PHONE);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    // 有文字时显示指示器
                    showCleanIcon(true);
                } else {
                    showCleanIcon(false); // 隐藏指示器
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // 显示密码提示标志
    private void showCleanIcon(boolean shouldShowIcon) {
        if (shouldShowIcon) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), mCleanIcon);
            // 在最右侧显示图像
            setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            mDrawableSide = drawable;
        } else {
            // 不显示周边的图像
            setCompoundDrawables(null, null, null, null);
            mDrawableSide = null;
        }
    }


    // 变换状态
    private void cleanContent() {
        showCleanIcon(false);
        setText("");
        // 移动光标
        setSelection(getText().length());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDrawableSide == null) {
            return super.onTouchEvent(event);
        }
        final Rect bounds = mDrawableSide.getBounds();
        final int x = (int) event.getRawX(); // 点击的位置

        int iconX = (int) getTopRightCorner().x;

        // Icon的位置
        int leftIcon = iconX - bounds.width();


        // 大于Icon的位置, 才能触发点击
        if (x >= leftIcon) {
            cleanContent(); // 清除内容
            event.setAction(MotionEvent.ACTION_CANCEL);
            return false;
        }
        return super.onTouchEvent(event);
    }

    // 获取上右角的距离
    public PointF getTopRightCorner() {
        float src[] = new float[8];
        float[] dst = new float[]{0, 0, getWidth(), 0, 0, getHeight(), getWidth(), getHeight()};
        getMatrix().mapPoints(src, dst);
        return new PointF(getX() + src[2], getY() + src[3]);
    }
}
