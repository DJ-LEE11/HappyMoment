package com.example.circle_base_ui.helper;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.circle_base_library.base.BaseActivity;
import com.example.circle_base_library.base.BaseFragment;
import com.example.circle_base_library.helper.AppFileHelper;
import com.example.circle_base_library.helper.PermissionHelper;
import com.example.circle_base_library.utils.ImageSelectUtil;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.circle_base_library.utils.ImageSelectUtil.getDataColumn;
import static com.example.circle_base_library.utils.ImageSelectUtil.isDownloadsDocument;
import static com.example.circle_base_library.utils.ImageSelectUtil.isExternalStorageDocument;
import static com.example.circle_base_library.utils.ImageSelectUtil.isGooglePhotosUri;
import static com.example.circle_base_library.utils.ImageSelectUtil.isMediaDocument;


/**
 * Created by 大灯泡 on 2017/4/10.
 * <p>
 * 图片拍照和选择
 * <p>
 * 务必注意fragment和activity，因为fragment调用getActivity不会调用到onActivityResult
 */

public class PhotoHelper {
    private static String curTempPhotoPath;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({REQUEST_FROM_CAMERA, REQUEST_FROM_ALBUM, REQUEST_FROM_CROP})
    public @interface PhotoType {
    }

    public static final int REQUEST_FROM_CAMERA = 0x10;
    public static final int REQUEST_FROM_ALBUM = 0x11;
    public static final int REQUEST_FROM_CROP = 0x12;
    private static final int REQUEST_FROM_CAMERA_WITHO_OUT_CROP = 0x13;//不截图的request code
    private static final int REQUEST_FROM_ALBUM_WITHO_OUT_CROP = 0x14;//不截图的request code


    private static String cropPath;


    public static void fromCamera(Activity activity) {
        fromCamera(activity, true);
    }

    public static void fromCamera(Activity activity, boolean needCrop) {
        checkAndRequestCameraPermission(activity, needCrop);
    }

    public static void fromAlbum(Activity activity) {
        fromAlbum(activity, true);
    }

    public static void fromAlbum(Activity activity, boolean needCrop) {
        fromAlbumInternal(activity, needCrop);
    }

    public static String getCropImagePath() {
        return AppFileHelper.getAppTempPath() + AppFileHelper.createCropImageName();
    }

    public static void toCrop(Activity activity, Uri imageUri) {
        toCropInternal(activity, imageUri);
    }

    public static void fromCamera(Fragment fragment) {
        fromCamera(fragment, true);
    }

    public static void fromCamera(Fragment fragment, boolean needCrop) {
        checkAndRequestCameraPermission(fragment, needCrop);
    }

    public static void fromAlbum(Fragment fragment) {
        fromAlbum(fragment, true);
    }

    public static void fromAlbum(Fragment fragment, boolean needCrop) {
        fromAlbumInternal(fragment, needCrop);
    }

    public static void toCrop(Fragment fragment, Uri imageUri) {
        toCropInternal(fragment, imageUri);
    }


    private static void fromAlbumInternal(Object obj, boolean needCrop) {
        if (obj == null) return;
        cropPath = getCropImagePath();
        // 跳转到系统相册
        Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        albumIntent.setType("image/*");
        if (obj instanceof Fragment) {
            ((Fragment) obj).startActivityForResult(albumIntent, needCrop ? REQUEST_FROM_ALBUM : REQUEST_FROM_ALBUM_WITHO_OUT_CROP);
        } else if (obj instanceof Activity) {
            ((Activity) obj).startActivityForResult(albumIntent, needCrop ? REQUEST_FROM_ALBUM : REQUEST_FROM_ALBUM_WITHO_OUT_CROP);
        }
    }

    private static void checkAndRequestCameraPermission(final Object object, final boolean needCrop) {
        PermissionHelper permissionHelper = null;
        PermissionHelper.OnPermissionGrantListener onPermissionGrantListener = new PermissionHelper.OnPermissionGrantListener() {
            @Override
            public void onPermissionGranted(@PermissionHelper.PermissionResultCode int requestCode) {
                fromCameraInternal(object, needCrop);
            }

            @Override
            public void onPermissionsDenied(@PermissionHelper.PermissionResultCode int requestCode) {

            }
        };
        if (object instanceof Activity) {
            permissionHelper = object instanceof BaseActivity ? ((BaseActivity) object).getPermissionHelper() : new PermissionHelper((Activity) object);
        } else if (object instanceof Fragment) {
            permissionHelper = object instanceof BaseFragment ? ((BaseFragment) object).getPermissionHelper() : new PermissionHelper(((Fragment) object).getActivity());
        }
        if (permissionHelper != null) {
            permissionHelper.requestPermission(PermissionHelper.CODE_CAMERA, onPermissionGrantListener);
        }

    }

    private static void checkAndRequestCameraPermission(final Activity activity, final boolean needCrop) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            fromCameraInternal(activity, needCrop);
        }
    }

    public static Uri imageUriFromCamera;
    private static void fromCameraInternal(Activity activity, boolean needCrop) {
        if (activity == null) return;
        imageUriFromCamera = createImagePathUri(activity);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
        activity.startActivityForResult(intent, REQUEST_FROM_CAMERA);
    }

    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @param context
     * @return 图片的uri
     */
    public static Uri createImagePathUri(final Context context) {
        final Uri[] imageFilePath = {null};

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            imageFilePath[0] = Uri.parse("");
        } else {
            String status = Environment.getExternalStorageState();
            SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
            long time = System.currentTimeMillis();
            String imageName = timeFormatter.format(new Date(time));
            // ContentValues是我们希望这条记录被创建时包含的数据信息
            ContentValues values = new ContentValues(3);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
            values.put(MediaStore.Images.Media.DATE_TAKEN, time);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

            if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
                imageFilePath[0] = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                imageFilePath[0] = context.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
            }
        }

        Log.i("", "生成的照片输出路径：" + imageFilePath[0].toString());
        return imageFilePath[0];
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param context
     * @param imageUri
     * @author yaoxing
     * @date 2014-10-12
     */
    @TargetApi(19)
    public static String getImageAbsolutePath(Context context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    private static void fromCameraInternal(Object object, boolean needCrop) {
        if (object == null) return;
        cropPath = getCropImagePath();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        curTempPhotoPath = AppFileHelper.getCameraPath() + AppFileHelper.createShareImageName();
        Uri uri = CompatHelper.getUri(new File(curTempPhotoPath));
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (CompatHelper.isOverM()) {
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        if (object instanceof Activity) {
            ((Activity) object).startActivityForResult(cameraIntent, needCrop ? REQUEST_FROM_CAMERA : REQUEST_FROM_CAMERA_WITHO_OUT_CROP);
        } else if (object instanceof Fragment) {
            ((Fragment) object).startActivityForResult(cameraIntent, needCrop ? REQUEST_FROM_CAMERA : REQUEST_FROM_CAMERA_WITHO_OUT_CROP);
        }
    }


    private static void toCropInternal(Object obj, Uri imageUri) {
        if (obj == null || imageUri == null) return;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", imageCover.getWidth());// 输出图片大小
//        intent.putExtra("outputY", imageCover.getHeight());

        //此处使用uri.fromFile，而不要用fileProvider，因为非export，而后面自动授权，所以不会出现安全异常
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cropPath)));
        intent.putExtra("return-data", false);
        if (CompatHelper.isOverM()) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        if (obj instanceof Activity) {
            ((Activity) obj).startActivityForResult(intent, REQUEST_FROM_CROP);
        } else if (obj instanceof Fragment) {
            ((Fragment) obj).startActivityForResult(intent, REQUEST_FROM_CROP);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void handleActivityResult(Activity act, int requestCode, int resultCode, Intent data, @Nullable PhotoCallback callback) {
        boolean needCrop = true;
        switch (requestCode) {
            case REQUEST_FROM_CAMERA_WITHO_OUT_CROP:
                needCrop = false;
            case REQUEST_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
//                    File file = new File(curTempPhotoPath);
                    callFinish(callback, curTempPhotoPath);

//                    if (file.exists()) {
//                        if (needCrop) {
//                            toCrop(act, CompatHelper.getUri(file));
//                        } else {
//                            callFinish(callback, curTempPhotoPath);
//                        }
//                    } else {
//                        callError(callback, "can not get the taked photo");
//                    }
                }
                break;
            case REQUEST_FROM_ALBUM_WITHO_OUT_CROP:
                needCrop = false;
            case REQUEST_FROM_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        final Uri photoUri = data.getData();
                        if (photoUri == null) {
                            callError(callback, "加载图片失败");
                            return;
                        }
                        try {
                            if (CompatHelper.isOverN()) {
                                if (needCrop) {
                                    //7.0用的是content uri
                                    toCrop(act, photoUri);
                                } else {
                                    callFinish(callback, ImageSelectUtil.getPath(act, photoUri));
                                }
                            } else {
                                //非7.0用的file uri
                                String path = ImageSelectUtil.getPath(act, photoUri);
                                if (needCrop) {
                                    toCrop(act, CompatHelper.getUri(new File(path)));
                                } else {
                                    callFinish(callback, path);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            callError(callback, "图片不存在");
                        }
                    } else {
                        callError(callback, "图片不存在");
                    }
                }
                break;
            case REQUEST_FROM_CROP:
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        callError(callback, "图片不存在");
                        return;
                    }
                    callFinish(callback, cropPath);
                } else {
                    callError(callback, "取消");
                }
                break;

        }
    }

    public static void handleActivityResult(Fragment fragment, int requestCode, int resultCode, Intent data, @Nullable PhotoCallback callback) {
        boolean needCrop = true;
        switch (requestCode) {
            case REQUEST_FROM_CAMERA_WITHO_OUT_CROP:
                needCrop = false;
            case REQUEST_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    File file = new File(curTempPhotoPath);
                    if (file.exists()) {
                        if (needCrop) {
                            toCrop(fragment, CompatHelper.getUri(file));
                        } else {
                            callFinish(callback, curTempPhotoPath);
                        }
                    } else {
                        callError(callback, "can not get the taked photo");
                    }
                }
                break;
            case REQUEST_FROM_ALBUM_WITHO_OUT_CROP:
                needCrop = false;
            case REQUEST_FROM_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        final Uri photoUri = data.getData();
                        if (photoUri == null) {
                            callError(callback, "加载图片失败");
                            return;
                        }
                        try {
                            if (CompatHelper.isOverN()) {
                                if (needCrop) {
                                    //7.0用的是content uri
                                    toCrop(fragment, photoUri);
                                } else {
                                    callFinish(callback, ImageSelectUtil.getPath(fragment.getContext(), photoUri));
                                }
                            } else {
                                //非7.0用的file uri
                                String path = ImageSelectUtil.getPath(fragment.getContext(), photoUri);
                                if (needCrop) {
                                    toCrop(fragment, CompatHelper.getUri(new File(path)));
                                } else {
                                    callFinish(callback, path);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            callError(callback, "图片不存在");
                        }
                    } else {
                        callError(callback, "图片不存在");
                    }
                }
                break;
            case REQUEST_FROM_CROP:
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        callError(callback, "图片不存在");
                        return;
                    }
                    callFinish(callback, cropPath);
                } else {
                    callError(callback, "取消");
                }
                break;

        }
    }

    private static void callFinish(PhotoCallback callback, String filePath) {
        if (callback != null) {
            callback.onFinish(filePath);
        }
    }

    private static void callError(PhotoCallback callback, String errorMsg) {
        if (callback != null) {
            callback.onError(errorMsg);
        }
    }

    public interface PhotoCallback {
        void onFinish(String filePath);

        void onError(String msg);
    }

}
