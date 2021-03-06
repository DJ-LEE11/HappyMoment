package com.example.circle_base_library.manager.compress;

import android.text.TextUtils;

import com.example.circle_base_library.helper.AppFileHelper;
import com.example.circle_base_library.utils.EncryUtil;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import static com.example.circle_base_library.manager.compress.CompressManager.BOTH;


/**
 * Created by 大灯泡 on 2018/1/9.
 * 压缩配置
 */
public class CompressOption implements Serializable {
    private WeakReference<CompressManager> mManagerWeakReference;

    @CompressManager.CompressType
    int compressType = BOTH;
    int sizeTarget = 5 * 1024;//默认最大5M
    int maxWidth = 720;//默认最大720p
    int maxHeight = 1280;

    boolean autoRotate = true;

    @CompressManager.ImageType
    String suffix = CompressManager.JPG;
    String originalImagePath;
    String saveCompressImagePath;

    OnCompressListener mOnCompressListener;

    public CompressOption(CompressManager manager) {
        mManagerWeakReference = new WeakReference<CompressManager>(manager);
    }

    CompressManager getManager() {
        return mManagerWeakReference == null ? null : mManagerWeakReference.get();
    }


    public CompressOption setCompressType(@CompressManager.CompressType int compressType) {
        this.compressType = compressType;
        return this;
    }


    public CompressOption setSizeTarget(int sizeTarget) {
        this.sizeTarget = sizeTarget;
        return this;
    }

    public CompressOption setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public CompressOption setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public CompressOption setAutoRotate(boolean autoRotate) {
        this.autoRotate = autoRotate;
        return this;
    }


    public CompressOption setOriginalImagePath(String originalImagePath) {
        this.originalImagePath = originalImagePath;
        if (TextUtils.isEmpty(saveCompressImagePath)) {
            saveCompressImagePath = AppFileHelper.getAppTempPath().concat(EncryUtil.MD5(originalImagePath) + suffix);
        }
        return this;
    }

    public CompressOption setSaveCompressImagePath(String saveCompressImagePath) {
        this.saveCompressImagePath = saveCompressImagePath;
        return this;
    }

    public CompressOption setOnCompressListener(OnCompressListener onCompressListener) {
        mOnCompressListener = onCompressListener;
        return this;
    }

    public CompressOption setSuffix(@CompressManager.ImageType String suffix) {
        this.suffix = suffix;
        return this;
    }

    public CompressOption addTask() {
        if (getManager() == null) {
            throw new NullPointerException("CompressManager must not be null");
        }
        return getManager().addTaskInternal(this);
    }

    public void start() {
        start(null);
    }

    public void start(OnCompressListener listener) {
        if (getManager() == null) {
            throw new NullPointerException("CompressManager must not be null");
        }
        getManager().start(listener);
    }
}
