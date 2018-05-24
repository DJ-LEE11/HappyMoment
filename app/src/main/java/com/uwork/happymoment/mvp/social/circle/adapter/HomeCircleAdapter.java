package com.uwork.happymoment.mvp.social.circle.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.uwork.happymoment.R;
import com.uwork.happymoment.adapter.BaseAdapter;
import com.uwork.happymoment.mvp.social.circle.activity.CirclePhotoActivity;
import com.uwork.happymoment.mvp.social.circle.bean.HomeCircleBean;
import com.uwork.libutil.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import static com.uwork.happymoment.mvp.social.circle.activity.CirclePhotoActivity.PHOTO_LIST;
import static com.uwork.happymoment.mvp.social.circle.activity.CirclePhotoActivity.POSITION;

/**
 * Created by jie on 2018/5/23.
 */

public class HomeCircleAdapter extends BaseAdapter<HomeCircleBean> {

    public HomeCircleAdapter(@Nullable List<HomeCircleBean> data) {
        super(R.layout.home_circle_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeCircleBean item) {
        helper.setText(R.id.tvDay, TimeUtils.parseDd(item.getCreateTime()) + "");
        helper.setText(R.id.tvMonth, TimeUtils.parseMm(item.getCreateTime()) + "月");
        helper.setText(R.id.tvContent, item.getContent());

        helper.setGone(R.id.tvNumber, false);
        helper.setGone(R.id.rlSinglePhotoContent, false);
        helper.setGone(R.id.rlPhotoContent, false);
        ArrayList<String> picture = (ArrayList<String>) item.getPicture();
        if (picture != null && picture.size() > 0) {
            helper.setVisible(R.id.tvNumber, true);
            helper.setText(R.id.tvNumber, "共" + picture.size() + "张");
            if (picture.size() < 4) {
                helper.setVisible(R.id.rlSinglePhotoContent, true);
                ImageLoadMnanger.INSTANCE.loadImage(helper.getView(R.id.ivSingle), picture.get(0));

                helper.getView(R.id.ivSingle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPhotoActivity(0, picture);
                    }
                });
            }else {
                helper.setVisible(R.id.rlPhotoContent, true);
                ImageLoadMnanger.INSTANCE.loadImage(helper.getView(R.id.ivPhoto1), picture.get(0));
                ImageLoadMnanger.INSTANCE.loadImage(helper.getView(R.id.ivPhoto2), picture.get(1));
                ImageLoadMnanger.INSTANCE.loadImage(helper.getView(R.id.ivPhoto3), picture.get(2));
                ImageLoadMnanger.INSTANCE.loadImage(helper.getView(R.id.ivPhoto4), picture.get(3));
                helper.getView(R.id.rlPhotoContent).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPhotoActivity(0, picture);
                    }
                });
            }
        }
    }

    private void startPhotoActivity(int position, ArrayList<String> photos) {
        Intent intent = new Intent(mContext, CirclePhotoActivity.class);
        intent.putExtra(POSITION, position);
        intent.putStringArrayListExtra(PHOTO_LIST, photos);
        (mContext).startActivity(intent);
    }
}
