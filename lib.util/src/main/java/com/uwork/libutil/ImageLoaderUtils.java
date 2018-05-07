package com.uwork.libutil;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Description : 图片加载工具类
 * Author : LiDongJie
 */
public class ImageLoaderUtils {

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(placeholder)
                .error(error).crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_error).crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, Uri uri) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(uri).placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_error).crossFade().into(imageView);
    }

    //加载圆形头像专用
    public static void circleImagePlay(Context context, ImageView imageView, String url) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).transform(new CircleImageLoaderUtils(context))
                .placeholder(R.drawable.ic_image_error).into(imageView);
    }

    public static void circleImagePlay(Context context, ImageView imageView, Uri uri) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(uri).transform(new CircleImageLoaderUtils(context))
                .placeholder(R.drawable.ic_image_error).into(imageView);
    }

}
