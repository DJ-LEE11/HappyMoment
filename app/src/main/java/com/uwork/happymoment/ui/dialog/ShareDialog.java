package com.uwork.happymoment.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uwork.happymoment.R;


public class ShareDialog extends BaseDialog {

    public ShareDialog(Context context, View.OnClickListener wechatListener
            , View.OnClickListener circleListener) {
        super(context);
        initView(context, wechatListener, circleListener, null,null);
    }

    public ShareDialog(Context context, View.OnClickListener wechatListener
            , View.OnClickListener circleListener, View.OnClickListener qqListener) {
        super(context);
        initView(context, wechatListener,circleListener ,qqListener,null);
    }

    public ShareDialog(Context context, View.OnClickListener wechatListener
            , View.OnClickListener circleListener, View.OnClickListener qqListener, View.OnClickListener cancelListener) {
        super(context);
        initView(context, wechatListener, circleListener,qqListener,cancelListener);
    }

    private void initView(Context context, View.OnClickListener wechatListener
            , View.OnClickListener circleListener, View.OnClickListener qqListener, View.OnClickListener cancelListener) {
        View dialog_view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_share, null);
        TextView tvWechat = dialog_view.findViewById(R.id.tvShareWeChat);
        TextView tvQQ = dialog_view.findViewById(R.id.tvShareQQ);
        TextView tvWechatCircle = dialog_view.findViewById(R.id.tvWeChatCircle);
        RelativeLayout rlWechat = dialog_view.findViewById(R.id.rlWeChat);
        RelativeLayout rlQq = dialog_view.findViewById(R.id.rlQq);
        RelativeLayout rlWeChatCircle = dialog_view.findViewById(R.id.rlWeChatCircle);
        TextView tvCancel = dialog_view.findViewById(R.id.tvCancel);

        rlWechat.setVisibility(View.GONE);
        rlQq.setVisibility(View.GONE);
        rlWeChatCircle.setVisibility(View.GONE);

        if (wechatListener !=null){
            rlWechat.setVisibility(View.VISIBLE);
            tvWechat.setOnClickListener(wechatListener);
        }

        if (qqListener !=null ){
            rlQq.setVisibility(View.VISIBLE);
            tvQQ.setOnClickListener(qqListener);
        }

        if (circleListener !=null){
            rlWeChatCircle.setVisibility(View.VISIBLE);
            tvWechatCircle.setOnClickListener(circleListener);
        }



        if (cancelListener!=null){
            tvCancel.setVisibility(View.VISIBLE);
            tvCancel.setOnClickListener(cancelListener);
        }else {
            tvCancel.setVisibility(View.VISIBLE);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    cancel();
                }
            });
        }
        setContentView(dialog_view);
        mLayoutParams.gravity = Gravity.BOTTOM;
    }
}
