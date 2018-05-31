package com.uwork.happymoment.mvp.my.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.circle_base_ui.imageloader.ImageLoadMnanger;
import com.kw.rxbus.RxBus;
import com.uwork.happymoment.R;
import com.uwork.happymoment.activity.BaseTitleActivity;
import com.uwork.happymoment.event.RefreshUserInfoEvent;
import com.uwork.happymoment.manager.IMRongManager;
import com.uwork.happymoment.manager.UserManager;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.mvp.login.contract.IUploadImageContract;
import com.uwork.happymoment.mvp.login.presenter.IUploadImagePresenter;
import com.uwork.happymoment.mvp.my.contract.IUpdateUserInfoContract;
import com.uwork.happymoment.mvp.my.presenter.IUpdateUserInfoPresenter;
import com.uwork.happymoment.ui.dialog.ChoosePhotoDialog;
import com.uwork.happymoment.util.PhotoUtil;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdateAvatarActivity extends BaseTitleActivity implements IUpdateUserInfoContract.View, IUploadImageContract.View {

    @BindView(R.id.ivHeader)
    ImageView mIvHeader;
    private UserBean mUser;
    private IUpdateUserInfoPresenter mIUpdateUserInfoPresenter;
    private IUploadImagePresenter mIUploadImagePresenter;

    private Uri mResultUri;
    private File mFile;
    private MultipartBody.Part mBody;
    private RequestBody mRequestFile;
    private String mUrl;
    private ChoosePhotoDialog mChoosePhotoDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List createPresenter(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        mIUpdateUserInfoPresenter = new IUpdateUserInfoPresenter(this);
        mIUploadImagePresenter = new IUploadImagePresenter(this);
        list.add(mIUpdateUserInfoPresenter);
        list.add(mIUploadImagePresenter);
        return list;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_update_avatar;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initTitle();
        mUser = UserManager.getInstance().getUser(this);
        initAvatar();
    }


    private void initTitle() {
        setTitle("个人头像");
        setBackClick();
        setRightClick(R.mipmap.ic_update_avatar, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoDialog();
            }
        });
    }

    private void initAvatar() {
        ViewGroup.LayoutParams params = mIvHeader.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels;
        params.height = getResources().getDisplayMetrics().widthPixels;
        mIvHeader.setLayoutParams(params);
        ImageLoadMnanger.INSTANCE.loadImage(mIvHeader, mUser.getAvatar());
    }

    private static final int PERMISSION_REQUEST = 100;

    private void showPhotoDialog() {
        //打开相机、读取文件权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        } else {
            showDialog();
        }
    }

    private void showDialog() {
        if (mChoosePhotoDialog == null) {
            mChoosePhotoDialog = new ChoosePhotoDialog(this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mChoosePhotoDialog != null) {
                        mChoosePhotoDialog.cancel();
                    }
                    if (!TextUtils.isEmpty(mUser.getAvatar())){
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                savePhoto(getBitmapFromUrl(mUser.getAvatar()));
                            }
                        }.start();
                    }else {
                        showToast("当前为无效照片");
                    }

                }
            });
        }
        mChoosePhotoDialog.show();
    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    showDialog();
                } else {
                    // 没有获取到权限，做特殊处理
                    showToast("获取相机权限失败，请手动开启");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtil.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
                    initUCrop(data.getData());
                    //使用系统裁剪图片
                    //PhotoUtil.cropImage(this, PhotoUtil.imageUriFromCamera);
                }
                break;
            case PhotoUtil.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    initUCrop(PhotoUtil.imageUriFromCamera);
                    //使用系统裁剪图片
                    //PhotoUtil.cropImage(this, PhotoUtil.imageUriFromCamera);
                }
                break;
            case PhotoUtil.CROP_IMAGE://普通裁剪后的处理
                ImageLoadMnanger.INSTANCE.loadImage(mIvHeader, PhotoUtil.cropImageUri);
                //TODO上传图片处理
                //RequestUpdateAvatar(new File(RxPhotoTool.getRealFilePath(mContext, RxPhotoTool.cropImageUri)));
                break;
            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    showToast("正在上传头像");
                    mResultUri = UCrop.getOutput(data);
                    //转化成File文件上传到后台
                    mFile = new File(PhotoUtil.getImageAbsolutePath(this, mResultUri));
                    //将图片设置成上传格式
                    mRequestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFile);
                    //image为请求参数
                    mBody = MultipartBody.Part.createFormData("image", mFile.getName(), mRequestFile);
                    mIUploadImagePresenter.uploadImage(mBody);
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initUCrop(Uri uri) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.theme_color_yellow));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.theme_color_yellow));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        //        options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        //         options.setShowCropFrame(true);
        //设置裁剪框横竖线的宽度
        //        options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //        options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //        options.setCropGridColumnCount(4);
        //设置横线的数量
        //options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(this);
    }

    @Override
    public void loadHead(String Url) {
        mUrl = Url;
        mIUpdateUserInfoPresenter.updateUserInfo(Url, mUser.getNickName(), mUser.getSex());
    }

    @Override
    public void updateUserInfoSuccess() {
        ImageLoadMnanger.INSTANCE.loadImage(mIvHeader, mUrl);
        mUser.setAvatar(mUrl);
        UserManager.getInstance().saveUser(this, mUser);
        IMRongManager.updateUserInfo(this, mUser.getId() + "", mUser.getNickName(), mUser.getAvatar());
        RxBus.getInstance().send(new RefreshUserInfoEvent());
        showToast("修改成功");
    }

    public static final String TMP_PATH = "header_temp.png";

    //保存图片到本地相册
    private void savePhoto(Bitmap bitmap) {
        if (bitmap == null)
            return;
        //截图保存的文件夹和路径
        File dir = new File(Environment.getExternalStorageDirectory() + "/happy/");
        if (!dir.exists()) dir.mkdirs();

        File file = new File(dir, TMP_PATH);
        String path = file.getPath();
        //保存截图
        saveBitmap(bitmap, path);
        //通知系统刷新裁剪图片
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(path))));
        Message msg = Message.obtain();
        msg.what = 1;
        mHandler.sendMessage(msg);
    }

    private void saveBitmap(Bitmap bitmap, String path) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }

        FileOutputStream fOut = null;
        try {
            f.createNewFile();
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, fOut);
            fOut.flush();
        } catch (IOException e1) {
            dismissLoading();
            showToast("照片保存失败");
            e1.printStackTrace();
        } finally {
            try {
                if (fOut != null)
                    fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //url转Bitmap
    public Bitmap getBitmapFromUrl(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            dismissLoading();
            showToast("照片保存失败");
            e.printStackTrace();
        }
        return bm;
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    dismissLoading();
                    showToast("照片保存成功");
                    break;
            }
        }
    };


}
