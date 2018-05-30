package com.uwork.happymoment.mvp.main.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.example.libvideo.NiceVideoPlayer;
import com.example.libvideo.TxVideoPlayerController;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseAdapter;
import com.uwork.happymoment.mvp.main.bean.StageItemBean;
import com.uwork.libutil.DisplayUtil;

import java.util.List;

/**
 * Created by jie on 2018/5/28.
 */

public class StageAdapter extends BaseAdapter<StageItemBean>{

    public StageAdapter(@Nullable List<StageItemBean> data) {
        super(R.layout.adapter_stage, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StageItemBean item) {
        helper.setText(R.id.tvTitle,item.getName());
        helper.addOnClickListener(R.id.llLocation);
        helper.addOnClickListener(R.id.tvDetail);
        NiceVideoPlayer videoPlayer = helper.getView(R.id.video);
        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams params = videoPlayer.getLayoutParams();
        params.width = mContext.getResources().getDisplayMetrics().widthPixels- DisplayUtil.dp2px(mContext,20); // 宽度为屏幕宽度
        params.height = (int) (params.width * 9f / 16f);    // 高度为宽度的9/16
        videoPlayer.setLayoutParams(params);
        //控制器
        TxVideoPlayerController controller = new TxVideoPlayerController(mContext);
        videoPlayer.setController(controller);
        ImageLoadMnanger.INSTANCE.loadImage(controller.imageView(),item.getPictureUrl());
        controller.setTitle("");
        videoPlayer.setUp(item.getVideoUrl(), null);
    }
}
