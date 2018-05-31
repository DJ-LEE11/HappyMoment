package com.uwork.happymoment.mvp.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.libvideo.NiceVideoPlayer;
import com.example.libvideo.NiceVideoPlayerManager;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.main.adapter.StageAdapter;
import com.uwork.happymoment.mvp.main.bean.BannerBean;
import com.uwork.happymoment.mvp.main.bean.StageItemBean;
import com.uwork.happymoment.mvp.main.contract.IBannerContract;
import com.uwork.happymoment.mvp.main.contract.IStageListContract;
import com.uwork.happymoment.mvp.main.presenter.IBannerPresenter;
import com.uwork.happymoment.mvp.main.presenter.IStageListPresenter;
import com.uwork.happymoment.ui.banner.HomeTopBanner;
import com.uwork.librx.mvp.BaseActivity;
import com.uwork.libutil.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.uwork.happymoment.mvp.main.activity.StageActivityActivity.ACTIVITY_ID;
import static com.uwork.happymoment.mvp.main.activity.StageMapActivity.STAGE_LAT;
import static com.uwork.happymoment.mvp.main.activity.StageMapActivity.STAGE_LON;

public class StageActivity extends BaseActivity implements IBannerContract.View, IStageListContract.View {

    @BindView(R.id.homeTopBanner)
    HomeTopBanner mHomeTopBanner;
    @BindView(R.id.rvVideo)
    RecyclerView mRvVideo;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    public static final String LOCATION = "LOCATION";
    private String mLocation;
    private IBannerPresenter mIBannerPresenter;
    private IStageListPresenter mIStageListPresenter;
    private StageAdapter mStageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mIBannerPresenter = new IBannerPresenter(this);
        mIStageListPresenter = new IStageListPresenter(this);
        list.add(mIBannerPresenter);
        list.add(mIStageListPresenter);
        return list;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_stage;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initList();
    }

    private void initList() {
        mRvVideo.setNestedScrollingEnabled(false);
        mRvVideo.setLayoutManager(new LinearLayoutManager(this));
        mRvVideo.setHasFixedSize(true);
        mStageAdapter = new StageAdapter(new ArrayList<>());
        mRvVideo.setAdapter(mStageAdapter);
        mRvVideo.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                NiceVideoPlayer niceVideoPlayer = ((BaseViewHolder) holder).getView(R.id.video);
                if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
            }
        });
        mStageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.llLocation){
                    StageItemBean stageItemBean = (StageItemBean) adapter.getData().get(position);
                    String lngLat = stageItemBean.getLngLat();
                    if (!TextUtils.isEmpty(lngLat)){
                        String[] location = lngLat.split(",");
                        if (location.length==2){
                            new IntentUtils.Builder(StageActivity.this)
                                    .to(StageMapActivity.class)
                                    .putExtra(STAGE_LON,Double.valueOf(location[0]))//经度
                                    .putExtra(STAGE_LAT,Double.valueOf(location[1]))//维度
                                    .build()
                                    .start();
                        }else {
                            showToast("暂无活动地址");
                        }
                    }else {
                        showToast("暂无活动地址");
                    }
                }else if (view.getId() == R.id.tvDetail){
                    StageItemBean stageItemBean = (StageItemBean) adapter.getData().get(position);
                    goTo(StageActivityActivity.class,false,ACTIVITY_ID,stageItemBean.getId());
                }
            }
        });
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {
        mLocation = intent.getStringExtra(LOCATION);
        mIBannerPresenter.getBanner(4);
        mIStageListPresenter.getStageList(mLocation,"");
    }

    @OnClick({R.id.llFinish, R.id.llSearch, R.id.tvCall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llFinish:
                finish();
                break;
            case R.id.llSearch:
                goTo(StageSearchActivity.class,false,LOCATION,mLocation);
                break;
            case R.id.tvCall:
                showToast("一键管家");
                break;
        }
    }

    @Override
    public void shoBanner(Integer type, List<BannerBean> bannerBeanList) {
        mHomeTopBanner.setIndicatorStyle(BaseIndicatorBanner.STYLE_DRAWABLE_RESOURCE)
                .setIndicatorSelectorRes(R.mipmap.banner_point_un_select, R.mipmap.banner_point_select)
                .setSource(bannerBeanList)
                .startScroll();
    }

    @Override
    public void showStageList(List<StageItemBean> stageItemBeanList) {
        mStageAdapter.setNewData(stageItemBeanList);
    }

    @Override
    public void showEmpty() {
        mStageAdapter.setEmptyView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }
}
