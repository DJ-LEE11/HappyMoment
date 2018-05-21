package com.uwork.happymoment.mvp.social.circle.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.circle_base_library.common.entity.ImageInfo;
import com.example.circle_base_library.interfaces.adapter.TextWatcherAdapter;
import com.example.circle_base_library.manager.compress.CompressManager;
import com.example.circle_base_library.manager.compress.OnCompressListener;
import com.example.circle_base_library.utils.StringUtil;
import com.example.circle_base_library.utils.ToolUtil;
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
import com.example.circle_common.common.router.RouterList;
import com.socks.library.KLog;
import com.uwork.happymoment.R;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.login.contract.IUploadImageContract;
import com.uwork.happymoment.mvp.login.presenter.IUploadImagePresenter;
import com.uwork.happymoment.mvp.social.circle.contract.ISendMomentContract;
import com.uwork.happymoment.mvp.social.circle.presenter.ISendMomentPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@Route(path = RouterList.PublishMomentActivity.path)
public class PublishMomentActivity extends PublishTitleActivity implements ISendMomentContract.View, IUploadImageContract.View {
    @Autowired(name = RouterList.PublishMomentActivity.key_mode)
    int mode = -1;

    private boolean canTitleRightClick = false;
    private List<ImageInfo> selectedPhotos;

    private EditText mInputContent;
    private PreviewImageView<ImageInfo> mPreviewImageView;

    private SelectPhotoMenuPopup mSelectPhotoMenuPopup;
    private PopupProgress mPopupProgress;

    private ISendMomentPresenter mISendMomentPresenter;
    private IUploadImagePresenter mIUploadImagePresenter;


    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mISendMomentPresenter = new ISendMomentPresenter(this);
        mIUploadImagePresenter = new IUploadImagePresenter(this);
        list.add(mISendMomentPresenter);
        list.add(mIUploadImagePresenter);
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_moment);
        if (mode == -1) {
            finish();
            return;
        } else if (mode == RouterList.PublishMomentActivity.MODE_MULTI && selectedPhotos == null) {
            finish();
            return;
        }
        initView();
    }


    @Override
    public void onHandleIntent(Intent intent) {
        mode = intent.getIntExtra(RouterList.PublishMomentActivity.key_mode, -1);
        selectedPhotos = intent.getParcelableArrayListExtra(RouterList.PublishMomentActivity.key_photoList);
    }

    private void initView() {
        initTitle();
        mInputContent = findView(R.id.publish_input);
        mPreviewImageView = findView(R.id.preview_image_content);
        ViewUtil.setViewsVisible(mode == RouterList.PublishMomentActivity.MODE_TEXT ? View.GONE : View.VISIBLE, mPreviewImageView);
        mInputContent.setHint(mode == RouterList.PublishMomentActivity.MODE_MULTI ? "这一刻的想法..." : null);
        mInputContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                refreshTitleRightClickable();
            }
        });

        if (mode == RouterList.PublishMomentActivity.MODE_TEXT) {
            UIHelper.showInputMethod(mInputContent, 300);
        }

        if (mode == RouterList.PublishMomentActivity.MODE_MULTI) {
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
                        .navigation(PublishMomentActivity.this, RouterList.PhotoMultiBrowserActivity.requestCode);
            }
        });
        mPreviewImageView.setOnAddPhotoClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeInput();
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
                    PhotoHelper.fromCamera(PublishMomentActivity.this, false);
                }

                @Override
                public void onAlbumClick() {
                    ARouter.getInstance()
                            .build(RouterList.PhotoSelectActivity.path)
                            .withInt(RouterList.PhotoSelectActivity.key_maxSelectCount, mPreviewImageView.getRestPhotoCount())
                            .navigation(PublishMomentActivity.this, RouterList.PhotoSelectActivity.requestCode);
                }
            });
        }
        mSelectPhotoMenuPopup.showPopupWindow();
    }

    //title init
    private void initTitle() {
        setTitle(mode == RouterList.PublishMomentActivity.MODE_TEXT ? "发表文字" : null);
        setTitleRightTextColor(mode != RouterList.PublishMomentActivity.MODE_TEXT);
        setTitleMode(TitleBar.MODE_BOTH);
        setTitleLeftText("取消");
        setTitleLeftIcon(0);
        setTitleRightText("发送");
        setTitleRightIcon(0);
    }


    private void setTitleRightTextColor(boolean canClick) {
        setRightTextColor(canClick ? UIHelper.getResourceColor(com.example.circlepublish.R.color.wechat_green_bg) : UIHelper.getResourceColor(com.example.circlepublish.R.color.wechat_green_transparent));
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
            case RouterList.PublishMomentActivity.MODE_MULTI:
                setTitleRightTextColor(!ToolUtil.isListEmpty(mPreviewImageView.getDatas()) && StringUtil.noEmpty(inputContent));
                break;
            case RouterList.PublishMomentActivity.MODE_TEXT:
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
        closeInput();
        SwitchActivityTransitionUtil.transitionVerticalOnFinish(this);
    }

    //关闭键盘
    private void closeInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
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

    //压缩图片
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

    private File mFile;
    private MultipartBody.Part mBody;
    private RequestBody mRequestFile;
    private int mUploadNum;
    private List<String> mResultUrlList =new ArrayList<>();

    //上传图片
    private void doUpload(final String[] uploadTaskPaths, final String inputContent) {
        showToast("正上传图片");
        if (uploadTaskPaths != null && uploadTaskPaths.length > 0) {
            mUploadNum = uploadTaskPaths.length;
            mResultUrlList.clear();
            for (int i = 0; i < uploadTaskPaths.length; i++) {
                //将相册返回的uri转成file
                mFile = new File(uploadTaskPaths[i]);
                //将图片设置成上传格式
                mRequestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFile);
                //image为请求参数
                mBody = MultipartBody.Part.createFormData("image", mFile.getName(), mRequestFile);
                mIUploadImagePresenter.uploadImage(mBody);
            }
        }
    }


    //上传成功
    @Override
    public void loadHead(String url) {
        mResultUrlList.add(url);
        if (mResultUrlList.size() == mUploadNum) {
            publishInternal(mInputContent.getText().toString(),mResultUrlList);
        }
    }

    //发送朋友圈
    private void publishInternal(String input, List<String> uploadPicPaths) {
        String photoList = "";
        if (uploadPicPaths!=null&&uploadPicPaths.size()>0){
            for (int i=0;i<uploadPicPaths.size();i++){
                if (i == uploadPicPaths.size()-1){
                    photoList = photoList+uploadPicPaths.get(i);
                }else {
                    photoList = photoList+uploadPicPaths.get(i)+",";
                }
            }
        }
        mISendMomentPresenter.sendMoment(UserManager.getInstance().getUserId(this),
                input, photoList, "", "", "", "", new ArrayList<>());

    }


    //发送朋友圈成功
    @Override
    public void sendMomentSuccess() {
        UIHelper.ToastMessage("发布成功");
        setResult(RESULT_OK);
        finish();
    }
}
