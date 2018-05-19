package com.uwork.happymoment.mvp.social.cirle.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.circle_base_ui.base.adapter.LayoutId;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.example.circle_base_ui.widget.imageview.ForceClickImageView;
import com.example.circle_common.common.entity.PhotoBrowseInfo;
import com.example.circle_common.util.BmobUrlUtil;
import com.socks.library.KLog;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.social.circleTest.activity.test.ActivityLauncher;
import com.uwork.happymoment.mvp.social.cirle.bean.MomentItemBean;

import java.util.ArrayList;
import java.util.List;

import razerdp.github.com.widget.PhotoContents;
import razerdp.github.com.widget.adapter.PhotoContentsBaseAdapter;

/**
 * <p>
 * 九宮格圖片的vh
 *
 *
 */

@LayoutId(id = R.layout.moment_multi_image)
public class MultiImageMomentsViewHolderMoment extends CircleMomentBaseViewHolder implements PhotoContents.OnItemClickListener {


    private PhotoContents imageContainer;
    private InnerContainerAdapter adapter;

    public MultiImageMomentsViewHolderMoment(View itemView, int viewType) {
        super(itemView, viewType);
    }


    @Override
    public void onFindView(@NonNull View rootView) {
        imageContainer = (PhotoContents) findView(imageContainer, R.id.circle_image_container);
        if (imageContainer.getmOnItemClickListener() == null) {
            imageContainer.setmOnItemClickListener(this);
        }
    }

    @Override
    public void onBindDataToView(@NonNull MomentItemBean data, int position, int viewType) {
        if (adapter == null) {
            adapter = new InnerContainerAdapter(getContext(), data.getContent().getPics());
            imageContainer.setAdapter(adapter);
        } else {
            adapter.updateData(data.getContent().getPics());
        }
    }

    @Override
    public void onItemClick(ImageView imageView, int i) {
        PhotoBrowseInfo info = PhotoBrowseInfo.create(adapter.datas, imageContainer.getContentViewsDrawableRects(), i);
        ActivityLauncher.startToPhotoBrosweActivity((Activity) getContext(), info);
    }


    private static class InnerContainerAdapter extends PhotoContentsBaseAdapter {


        private Context context;
        private List<String> datas;

        InnerContainerAdapter(Context context, List<String> datas) {
            this.context = context;
            this.datas = new ArrayList<>();
            this.datas.addAll(datas);
        }

        @Override
        public ImageView onCreateView(ImageView convertView, ViewGroup parent, int position) {
            if (convertView == null) {
                convertView = new ForceClickImageView(context);
                convertView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            return convertView;
        }

        @Override
        public void onBindData(int position, @NonNull ImageView convertView) {
            int width = convertView.getWidth();
            int height = convertView.getHeight();
            String imageUrl = datas.get(position);
            if (width > 0 && height > 0) {
                imageUrl = BmobUrlUtil.getThumbImageUrl(imageUrl, width, height);
            } else {
                imageUrl = BmobUrlUtil.getThumbImageUrl(imageUrl, 25);
            }
            KLog.i("处理的url  >>>  " + imageUrl);
            ImageLoadMnanger.INSTANCE.loadImage(convertView, imageUrl);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        public void updateData(List<String> datas) {
            this.datas.clear();
            this.datas.addAll(datas);
            notifyDataChanged();
        }

    }
}
