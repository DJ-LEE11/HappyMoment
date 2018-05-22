package com.uwork.happymoment.mvp.social.circle.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.circle_base_ui.util.UIHelper;
import com.uwork.happymoment.R;

/**
 * Created by jie on 2018/5/21.
 */

public class MomentRecyclerView extends FrameLayout {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public MomentRecyclerView(Context context) {
        this(context, null);
    }

    public MomentRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MomentRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (isInEditMode()) return;
        GradientDrawable background = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0xff323232, 0xff323232, 0xffffffff, 0xffffffff});
        setBackground(background);

        if (recyclerView == null) {
            recyclerView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.view_recyclerview, this, false);
            //new出来的recyclerview并没有滚动条，原因：没有走到View.initializeScrollbars(TypedArray a)
            //recyclerView = new RecyclerView(context);
            recyclerView.setBackgroundColor(Color.WHITE);
            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            //渲染优化，放到render thread做，（prefetch在v25之后可用），机型在萝莉炮(lollipop)后才可以享受此优化（事实上默认是开启的）
            //linearLayoutManager.setItemPrefetchEnabled(true);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        //取消默认item变更动画
        recyclerView.setItemAnimator(null);

        FrameLayout.LayoutParams iconParam = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iconParam.leftMargin = UIHelper.dipToPx(12);

        addView(recyclerView, RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);

    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 2) {
            throw new IllegalStateException("咳咳，不能超过两个view哦");
        }
        super.onFinishInflate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (onPreDispatchTouchListener != null) {
            onPreDispatchTouchListener.onPreTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public OnPreDispatchTouchListener getOnPreDispatchTouchListener() {
        return onPreDispatchTouchListener;
    }

    public void setOnPreDispatchTouchListener(OnPreDispatchTouchListener onPreDispatchTouchListener) {
        this.onPreDispatchTouchListener = onPreDispatchTouchListener;
    }

    private OnPreDispatchTouchListener onPreDispatchTouchListener;

    public interface OnPreDispatchTouchListener {
        boolean onPreTouch(MotionEvent ev);
    }
}
