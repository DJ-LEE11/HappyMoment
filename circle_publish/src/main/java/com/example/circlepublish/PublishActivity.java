package com.example.circlepublish;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.circle_base_library.common.entity.ImageInfo;
import com.example.circle_base_library.helper.AppSetting;
import com.example.circle_base_library.interfaces.adapter.TextWatcherAdapter;
import com.example.circle_base_library.manager.compress.CompressManager;
import com.example.circle_base_library.manager.compress.OnCompressListener;
import com.example.circle_base_library.network.base.OnResponseListener;
import com.example.circle_base_library.utils.StringUtil;
import com.example.circle_base_library.utils.ToolUtil;
import com.example.circle_base_ui.base.BaseTitleBarActivity;
import com.example.circle_base_ui.helper.PhotoHelper;
import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.example.circle_base_ui.util.SwitchActivityTransitionUtil;
import com.example.circle_base_ui.util.UIHelper;
import com.example.circle_base_ui.util.ViewUtil;
import com.example.circle_base_ui.widget.common.TitleBar;
import com.example.circle_base_ui.widget.imageview.PreviewImageView;
import com.example.circle_base_ui.widget.popup.PopupProgress;
import com.example.circle_base_ui.widget.popup.SelectPhotoMenuPopup;
import com.example.circle_common.common.entity.photo.PhotoBrowserInfo;
import com.example.circle_common.common.manager.LocalHostManager;
import com.example.circle_common.common.request.AddMomentsRequest;
import com.example.circle_common.common.router.RouterList;
import com.socks.library.KLog;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadBatchListener;


/**
 * Created by 大灯泡 on 2017/3/1.
 * <p>
 * 发布朋友圈页面
 */

@Route(path = RouterList.PublishActivity.path)
public class PublishActivity extends BaseTitleBarActivity {
    @Autowired(name = RouterList.PublishActivity.key_mode)
    int mode = -1;

    private boolean canTitleRightClick = false;
    private List<ImageInfo> selectedPhotos;

    private EditText mInputContent;
    private PreviewImageView<ImageInfo> mPreviewImageView;

    private SelectPhotoMenuPopup mSelectPhotoMenuPopup;
    private PopupProgress mPopupProgress;

    @Override
    public void onHandleIntent(Intent intent) {
        mode = intent.getIntExtra(RouterList.PublishActivity.key_mode, -1);
        selectedPhotos = intent.getParcelableArrayListExtra(RouterList.PublishActivity.key_photoList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        if (mode == -1) {
            finish();
            return;
        } else if (mode == RouterList.PublishActivity.MODE_MULTI && selectedPhotos == null) {
            finish();
            return;
        }
        initView();
    }

    private void initView() {
        initTitle();
        mInputContent = findView(R.id.publish_input);
        mPreviewImageView = findView(R.id.preview_image_content);
        ViewUtil.setViewsVisible(mode == RouterList.PublishActivity.MODE_TEXT ? View.GONE : View.VISIBLE, mPreviewImageView);
        mInputContent.setHint(mode == RouterList.PublishActivity.MODE_MULTI ? "这一刻的想法..." : null);
        mInputContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                refreshTitleRightClickable();
            }
        });

        if (mode == RouterList.PublishActivity.MODE_TEXT) {
            UIHelper.showInputMethod(mInputContent, 300);
        }

        if (mode == RouterList.PublishActivity.MODE_MULTI) {
            initPreviewImageView();
            loadImage();
        }
        refreshTitleRightClickable();
    }

    private void initPreviewImageView() {
        mPreviewImageView.setOnPhotoClickListener(new PreviewImageView.OnPhotoClickListener<ImageInfo>() {
            @Override
            public void onPhotoClickListener(int pos, ImageInfo data, @NonNull ImageView imageView) {
                PhotoBrowserInfo info = PhotoBrowserInfo.create(pos, null, selectedPhotos);
                ARouter.getInstance()
                        .build(RouterList.PhotoMultiBrowserActivity.path)
                        .withParcelable(RouterList.PhotoMultiBrowserActivity.key_browserinfo, info)
                        .withInt(RouterList.PhotoMultiBrowserActivity.key_maxSelectCount, selectedPhotos.size())
                        .navigation(PublishActivity.this, RouterList.PhotoMultiBrowserActivity.requestCode);
            }
        });
        mPreviewImageView.setOnAddPhotoClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPhotoPopup();
            }
        });
    }

    private void loadImage() {
        mPreviewImageView.setDatas(selectedPhotos, new PreviewImageView.OnLoadPhotoListener<ImageInfo>() {
            @Override
            public void onPhotoLoading(int pos, ImageInfo data, @NonNull ImageView imageView) {
                KLog.i(data.getImagePath());
                ImageLoadMnanger.INSTANCE.loadImage(imageView, data.getImagePath());
            }
        });
    }

    private void showSelectPhotoPopup() {
        if (mSelectPhotoMenuPopup == null) {
            mSelectPhotoMenuPopup = new SelectPhotoMenuPopup(this);
            mSelectPhotoMenuPopup.setOnSelectPhotoMenuClickListener(new SelectPhotoMenuPopup.OnSelectPhotoMenuClickListener() {
                @Override
                public void onShootClick() {
                    PhotoHelper.fromCamera(PublishActivity.this, false);
                }

                @Override
                public void onAlbumClick() {
                    ARouter.getInstance()
                            .build(RouterList.PhotoSelectActivity.path)
                            .withInt(RouterList.PhotoSelectActivity.key_maxSelectCount, mPreviewImageView.getRestPhotoCount())
                            .navigation(PublishActivity.this, RouterList.PhotoSelectActivity.requestCode);
                }
            });
        }
        mSelectPhotoMenuPopup.showPopupWindow();
    }

    //title init
    private void initTitle() {
        setTitle(mode == RouterList.PublishActivity.MODE_TEXT ? "发表文字" : null);
        setTitleRightTextColor(mode != RouterList.PublishActivity.MODE_TEXT);
        setTitleMode(TitleBar.MODE_BOTH);
        setTitleLeftText("取消");
        setTitleLeftIcon(0);
        setTitleRightText("发送");
        setTitleRightIcon(0);
    }


    private void setTitleRightTextColor(boolean canClick) {
        setRightTextColor(canClick ? UIHelper.getResourceColor(R.color.wechat_green_bg) : UIHelper.getResourceColor(R.color.wechat_green_transparent));
        canTitleRightClick = canClick;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoHelper.handleActivityResult(this, requestCode, resultCode, data, new PhotoHelper.PhotoCallback() {
            @Override
            public void onFinish(String filePath) {
                mPreviewImageView.addData(new ImageInfo(filePath, null, null, 0, 0));
                refreshTitleRightClickable();
            }

            @Override
            public void onError(String msg) {
                UIHelper.ToastMessage(msg);
            }
        });
        if (requestCode == RouterList.PhotoSelectActivity.requestCode && resultCode == RESULT_OK) {
            List<ImageInfo> result = data.getParcelableArrayListExtra(RouterList.PhotoSelectActivity.key_result);
            if (result != null) {
                mPreviewImageView.addData(result);
            }
            refreshTitleRightClickable();
        }
    }

    @Override
    public void onTitleRightClick() {
        if (!canTitleRightClick) return;
        publish();
    }

    private void refreshTitleRightClickable() {
        String inputContent = mInputContent.getText().toString();
        switch (mode) {
            case RouterList.PublishActivity.MODE_MULTI:
                setTitleRightTextColor(!ToolUtil.isListEmpty(mPreviewImageView.getDatas()) && StringUtil.noEmpty(inputContent));
                break;
            case RouterList.PublishActivity.MODE_TEXT:
                setTitleRightTextColor(StringUtil.noEmpty(inputContent));
                break;
        }

    }

    @Override
    public void finish() {
        super.finish();
        if (mPopupProgress != null) {
            mPopupProgress.dismiss();
        }
        SwitchActivityTransitionUtil.transitionVerticalOnFinish(this);
    }

    private void publish() {
        UIHelper.hideInputMethod(mInputContent);
        List<ImageInfo> datas = mPreviewImageView.getDatas();
        final boolean hasImage = !ToolUtil.isListEmpty(datas);
        final String inputContent = mInputContent.getText().toString();
        if (mPopupProgress == null) {
            mPopupProgress = new PopupProgress(this);
        }

        final String[] uploadTaskPaths;
        if (hasImage) {
            uploadTaskPaths = new String[datas.size()];
            for (int i = 0; i < datas.size(); i++) {
                uploadTaskPaths[i] = datas.get(i).getImagePath();
            }
            doCompress(uploadTaskPaths, new OnCompressListener.OnCompressListenerAdapter() {
                @Override
                public void onSuccess(List<String> imagePath) {
                    if (!ToolUtil.isListEmpty(imagePath)) {
                        for (int i = 0; i < imagePath.size(); i++) {
                            uploadTaskPaths[i] = imagePath.get(i);
                        }
                        doUpload(uploadTaskPaths, inputContent);
                    } else {
                        publishInternal(inputContent, null);
                    }
                }
            });
        } else {
            publishInternal(inputContent, null);
        }
    }

    private void doCompress(String[] uploadPaths, final OnCompressListener.OnCompressListenerAdapter listener) {
        CompressManager compressManager = CompressManager.create(this);
        for (String uploadPath : uploadPaths) {
            compressManager.addTask().setOriginalImagePath(uploadPath);
        }
        mPopupProgress.showPopupWindow();
        compressManager.start(new OnCompressListener.OnCompressListenerAdapter() {
            @Override
            public void onSuccess(List<String> imagePath) {
                if (listener != null) {
                    listener.onSuccess(imagePath);
                }
                mPopupProgress.dismiss();
            }

            @Override
            public void onCompress(long current, long target) {
                float progress = (float) current / target;
                mPopupProgress.setProgressTips("正在压缩第" + current + "/" + target + "张图片");
                mPopupProgress.setProgress((int) (progress * 100));
            }

            @Override
            public void onError(String tag) {
                mPopupProgress.dismiss();
                UIHelper.ToastMessage(tag);
            }
        });
    }

    private void doUpload(final String[] uploadTaskPaths, final String inputContent) {
        BmobFile.uploadBatch(uploadTaskPaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                //1、有多少个文件上传，onSuccess方法就会执行多少次;
                //2、通过onSuccess回调方法中的files或urls集合的大小与上传的总文件个数比较，如果一样，则表示全部文件上传成功。
                if (!ToolUtil.isListEmpty(list1) && list1.size() == uploadTaskPaths.length) {
                    publishInternal(inputContent, list1);
                }
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
                mPopupProgress.setProgressTips("正在上传第" + curIndex + "/" + total + "张图片");
                mPopupProgress.setProgress(totalPercent);
                if (!mPopupProgress.isShowing()) {
                    mPopupProgress.showPopupWindow();
                }
            }

            @Override
            public void onError(int i, String s) {
                mPopupProgress.dismiss();
                UIHelper.ToastMessage(s);
            }
        });
    }

    private void publishInternal(String input, List<String> uploadPicPaths) {
        mPopupProgress.setProgressTips("正在发布");
        if (!mPopupProgress.isShowing()) {
            mPopupProgress.showPopupWindow();
        }
        AddMomentsRequest addMomentsRequest = new AddMomentsRequest();
        addMomentsRequest.setAuthId(LocalHostManager.INSTANCE.getUserid())
                //暂时Host强制使用开发者id
                .setHostId(AppSetting.loadStringPreferenceByKey(AppSetting.HOST_ID, "MMbKLCCU"))
                .addText(input);
        if (!ToolUtil.isListEmpty(uploadPicPaths)) {
            for (String uploadPicPath : uploadPicPaths) {
                addMomentsRequest.addPicture(uploadPicPath);
            }
        }
        addMomentsRequest.setOnResponseListener(new OnResponseListener.SimpleResponseListener<String>() {
            @Override
            public void onSuccess(String response, int requestType) {
                mPopupProgress.dismiss();
                UIHelper.ToastMessage("发布成功");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(BmobException e, int requestType) {
                UIHelper.ToastMessage(e.toString());
            }
        });
        addMomentsRequest.execute();
    }
}
