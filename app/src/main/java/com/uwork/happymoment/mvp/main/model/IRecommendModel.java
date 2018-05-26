package com.uwork.happymoment.mvp.main.model;

import android.content.Context;
import android.provider.MediaStore;

import com.uwork.happymoment.BuildConfig;
import com.uwork.happymoment.api.ApiService;
import com.uwork.happymoment.mvp.main.bean.VideoBean;
import com.uwork.happymoment.mvp.main.bean.VideoRespondBean;
import com.uwork.happymoment.mvp.main.contract.IRecommendContract;
import com.uwork.happymoment.util.CreateVideoBitmapUtil;
import com.uwork.librx.mvp.model.IBaseModelImpl;
import com.uwork.librx.rx.http.ApiServiceFactory;
import com.uwork.librx.rx.http.CustomResourceSubscriber;
import com.uwork.librx.rx.http.HttpResultFunc;
import com.uwork.librx.rx.http.ServerResultFunc;
import com.uwork.librx.rx.interfaces.OnModelCallBack;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by jie on 2018/5/25.
 */

public class IRecommendModel extends IBaseModelImpl implements IRecommendContract.Model {

    public IRecommendModel(Context context) {
        super(context);
    }


    @Override
    public ResourceSubscriber getRecommend(OnModelCallBack<List<VideoBean>> callBack) {
        return startObservable(ApiServiceFactory.INSTANCE
                .create(mContext, BuildConfig.API_BASE_URL, ApiService.class)
                .getRecommend()
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .map(new Function<List<VideoRespondBean>, List<VideoBean>>() {
                    @Override
                    public List<VideoBean> apply(List<VideoRespondBean> videoRespondBeans) throws Exception {
                        List<VideoBean> videoBeans = null;
                        if (videoRespondBeans != null && videoRespondBeans.size() > 0) {
                            videoBeans = new ArrayList<>();
                            for (VideoRespondBean video : videoRespondBeans) {
                                videoBeans.add(new VideoBean(video.getTitle(), video.getImgLink()//获取视频第一帧
                                        , CreateVideoBitmapUtil.createVideoThumbnail(video.getImgLink(), MediaStore.Images.Thumbnails.MINI_KIND)));

                            }
                        }

                        return videoBeans;
                    }
                }), new CustomResourceSubscriber<List<VideoBean>>(callBack));
    }
}
