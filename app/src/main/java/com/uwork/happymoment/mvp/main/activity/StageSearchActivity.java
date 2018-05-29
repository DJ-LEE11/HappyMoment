package com.uwork.happymoment.mvp.main.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.libvideo.NiceVideoPlayer;
import com.example.libvideo.NiceVideoPlayerManager;
import com.uwork.happymoment.R;
import com.uwork.happymoment.mvp.main.adapter.StageAdapter;
import com.uwork.happymoment.mvp.main.bean.StageItemBean;
import com.uwork.happymoment.mvp.main.contract.IStageListContract;
import com.uwork.happymoment.mvp.main.presenter.IStageListPresenter;
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

public class StageSearchActivity extends BaseActivity implements IStageListContract.View {

    @BindView(R.id.etSearchStage)
    EditText mEtSearch;
    @BindView(R.id.rvVideo)
    RecyclerView mRvVideo;

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
        mIStageListPresenter = new IStageListPresenter(this);
        list.add(mIStageListPresenter);
        return list;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_stage_search;
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
                if (view.getId() == R.id.llLocation) {
                    StageItemBean stageItemBean = (StageItemBean) adapter.getData().get(position);
                    String lngLat = stageItemBean.getLngLat();
                    if (!TextUtils.isEmpty(lngLat)) {
                        String[] location = lngLat.split(",");
                        if (location.length == 2) {
                            new IntentUtils.Builder(StageSearchActivity.this)
                                    .to(StageMapActivity.class)
                                    .putExtra(STAGE_LON, Double.valueOf(location[0]))
                                    .putExtra(STAGE_LAT, Double.valueOf(location[1]))
                                    .build()
                                    .start();
                        } else {
                            showToast("暂无活动地址");
                        }
                    } else {
                        showToast("暂无活动地址");
                    }
                }
            }
        });
        mStageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                StageItemBean stageItemBean = (StageItemBean) adapter.getData().get(position);
                goTo(StageActivityActivity.class, false, ACTIVITY_ID, stageItemBean.getId());
            }
        });
    }


    @OnClick({R.id.ivBack, R.id.tvSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvSearch:
                String searchText = mEtSearch.getText().toString();
                if (!TextUtils.isEmpty(searchText)) {
                    mIStageListPresenter.getStageList(searchText);
                } else {
                    showToast("请输入搜索驿站");
                }
                break;
        }
    }

    @Override
    public void showStageList(List<StageItemBean> stageItemBeanList) {
        mStageAdapter.setNewData(stageItemBeanList);
    }

    @Override
    public void showEmpty() {
        mStageAdapter.setEmptyView(this, "搜不到当前驿站");
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
