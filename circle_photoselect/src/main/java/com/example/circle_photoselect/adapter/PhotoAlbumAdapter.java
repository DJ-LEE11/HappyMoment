package com.example.circle_photoselect.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.circle_base_ui.base.adapter.BaseRecyclerViewAdapter;
import com.example.circle_base_ui.base.adapter.BaseRecyclerViewHolder;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.example.circle_common.common.entity.photo.AlbumInfo;
import com.example.circle_photoselect.R;

import java.util.List;
import java.util.Locale;


/**
 * Created by 大灯泡 on 2017/3/24.
 * <p>
 * 相册adapter
 */

public class PhotoAlbumAdapter extends BaseRecyclerViewAdapter<AlbumInfo> {
    private static final String TAG = "PhotoAlbumAdapter";

    public PhotoAlbumAdapter(@NonNull Context context, @NonNull List<AlbumInfo> datas) {
        super(context, datas);
    }


    @Override
    protected int getViewType(int position, @NonNull AlbumInfo data) {
        return 0;
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_photo_album;
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(ViewGroup parent, View rootView, int viewType) {
        return new InnerViewHolder(rootView, viewType);
    }

    private class InnerViewHolder extends BaseRecyclerViewHolder<AlbumInfo> {

        private ImageView albumThumb;
        private TextView albumName;
        private TextView photoCounts;



        public InnerViewHolder(View itemView, int viewType) {
            super(itemView, viewType);
            albumThumb= (ImageView) findViewById(R.id.album_thumb);
            albumName= (TextView) findViewById(R.id.album_name);
            photoCounts= (TextView) findViewById(R.id.album_photo_counts);
        }

        @Override
        public void onBindData(AlbumInfo data, int position) {
            photoCounts.setText(String.format(Locale.getDefault(),"(%d)",data.getPhotoCounts()));
            albumName.setText(data.getAlbumName());
            ImageLoadMnanger.INSTANCE.loadImage(albumThumb,data.getFirstPhoto());
        }
    }

}
