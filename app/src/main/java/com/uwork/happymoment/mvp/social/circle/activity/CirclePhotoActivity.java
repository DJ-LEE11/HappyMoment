package com.uwork.happymoment.mvp.social.circle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.mvp.social.circle.ui.ViewpagerFix;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CirclePhotoActivity extends BaseTitleActivity {

    @BindView(R.id.viewpagerPhoto)
    ViewpagerFix mViewpagerPhoto;

    public static final String PHOTO_LIST = "PHOTO_LIST";
    public static final String POSITION = "POSITION";
    private ArrayList<View> mPhotoViews = null;
    private PhotoAdapter mPhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        return null;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_circle_photo;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initTitle();
    }

    private void initTitle() {
        setTitle("");
        setToolbarBackgroundColor(R.color.circle_title);
        setBackClick(R.mipmap.ic_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        ArrayList<String> photos = intent.getStringArrayListExtra(PHOTO_LIST);
        int position = intent.getIntExtra(POSITION, 0);

        for (int i = 0; i < photos.size(); i++) {
            //初始化照片布局
            initPhotoViews(photos.get(i));
        }
        mPhotoAdapter = new PhotoAdapter();
        mViewpagerPhoto.setAdapter(mPhotoAdapter);
        mViewpagerPhoto.setCurrentItem(position);
    }

    private void initPhotoViews(String url) {
        if (mPhotoViews == null) {
            mPhotoViews = new ArrayList<>();
        }
        PhotoView photoView = new PhotoView(this);
        photoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        photoView.enable();
        photoView.setMaxScale(3);
        ImageLoadMnanger.INSTANCE.loadImage(photoView,url);
        mPhotoViews.add(photoView);
    }

    class PhotoAdapter extends PagerAdapter {

        int size;

        public int getCount() {
            size = mPhotoViews == null ? 0 : mPhotoViews.size();
            return size;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            try {
                container.addView(mPhotoViews.get(position % size), 0);

            } catch (Exception e) {
            }
            return mPhotoViews.get(position % size);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mPhotoViews.get(position % size));
        }
    }

}
