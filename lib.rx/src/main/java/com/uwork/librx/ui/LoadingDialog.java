package com.uwork.librx.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uwork.librx.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * @author 李栋杰
 * @time 2017/7/29  0:20
 * @desc 加载
 */
public class LoadingDialog extends Dialog {
    private static final long DEAFAULT_DELAY = 500;
    private Builder builder;

    private LoadingDialog(@NonNull Context context) {
        super(context);
    }

    private LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    private LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void showLoading(String message) {
        AVLoadingIndicatorView avi = (AVLoadingIndicatorView) this.getWindow().findViewById(R.id.avi);
        avi.setVisibility(View.VISIBLE);
        avi.show();

        ImageView imageView = (ImageView) this.getWindow().findViewById(R.id.img);
        imageView.setVisibility(View.GONE);
        TextView tipTextView = (TextView) this.getWindow().findViewById(R.id.tipTextView);// 提示文字
        tipTextView.setText(message);// 设置加载信息
        show();
    }

    public void showLoading() {
        showLoading(null);
    }

    public void showSuccess(@NonNull Bitmap bitmapSuccess, String messageSuccess, long delay) {
        showImageWithMsg(bitmapSuccess, messageSuccess, delay);
    }

    public void showSuccess(int bitmapSuccessId, String messageSuccess, long delay) {
        showImageWithMsg(BitmapFactory.decodeResource(builder.context.getResources(), bitmapSuccessId), messageSuccess, delay);
    }

    public void showSuccess(String messageSuccess) {
        showSuccess(builder.imgSuccess, messageSuccess, DEAFAULT_DELAY);
    }

    public void showSuccess(long delay) {
        showSuccess(builder.imgSuccess, builder.messageSuccess, delay);
    }

    public void showSuccess() {
        showSuccess(builder.imgSuccess, builder.messageSuccess, DEAFAULT_DELAY);
    }

    public void showFailure(long delay) {
        showSuccess(builder.imgFailure, builder.messageFailure, delay);
    }

    public void showFailure(String messageFailure) {
        showSuccess(builder.imgSuccess, messageFailure, DEAFAULT_DELAY);
    }

    public void showFailure() {
        showSuccess(builder.imgFailure, builder.messageFailure, DEAFAULT_DELAY);
    }

    public void showFailure(int bitmapFailureId, String messageSuccess, long delay) {
        showImageWithMsg(BitmapFactory.decodeResource(builder.context.getResources(), bitmapFailureId), messageSuccess, delay);
    }

    public void showFailure(@NonNull Bitmap bitmapFailure, String messageFailure, long delay) {
        showImageWithMsg(bitmapFailure, messageFailure, delay);
    }

    public void showImageWithMsg(@NonNull Bitmap bitmap, String message, long delay) {
        if (bitmap == null) {
            finish(delay);
            return;
        }
        AVLoadingIndicatorView avi = (AVLoadingIndicatorView) this.getWindow().findViewById(R.id.avi);
        avi.hide();
        ImageView imageView = (ImageView) this.getWindow().findViewById(R.id.img);
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.VISIBLE);
        TextView tipTextView = (TextView) this.getWindow().findViewById(R.id.tipTextView);// 提示文字
        tipTextView.setText(message);
        show();
        finish(delay);
    }

    public void finish(long delay) {
        //
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(builder.context,
                        R.anim.anim_dialog_alpha);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                if (isShowing()) {
                    LoadingDialog.this.builder.contentView.startAnimation(animation);
                }
            }
        }, delay);
    }

    public void finish() {
        if (isShowing()) {
            dismiss();
        }
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public static class Builder {

        private Context context;
        private String message;
        private String messageSuccess;
        private String messageFailure;
        private Bitmap imgSuccess;
        private Bitmap imgFailure;
        private View contentView;
        private boolean cancelable;
        private OnKeyListener onKeyListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder messageSuccess(String messageSuccess) {
            this.messageSuccess = messageSuccess;
            return this;
        }

        public Builder messageFailure(String messageFailure) {
            this.messageFailure = messageFailure;
            return this;
        }

        public Builder imgSuccess(Bitmap imgSuccess) {
            this.imgSuccess = imgSuccess;
            return this;
        }

        public Builder imgFailure(Bitmap imgFailure) {
            this.imgFailure = imgFailure;
            return this;
        }

        public Builder imgSuccess(int imgSuccessId) {
            this.imgSuccess = BitmapFactory.decodeResource(context.getResources(), imgSuccessId);
            return this;
        }

        public Builder imgFailure(int imgFailureId) {
            this.imgFailure = BitmapFactory.decodeResource(context.getResources(), imgFailureId);
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder keyClick(OnKeyListener onKeyListener) {
            this.onKeyListener = onKeyListener;
            return this;
        }

        ImageView mImageView;
        AVLoadingIndicatorView avi;

        public LoadingDialog build() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            LoadingDialog dialog = new LoadingDialog(context, R.style.dialog);
            contentView = inflater.inflate(R.layout.dialog_loading, null);

            mImageView = (ImageView) contentView.findViewById(R.id.img);
            mImageView.setVisibility(View.GONE);
            avi = (AVLoadingIndicatorView) contentView.findViewById(R.id.avi);
            avi.setIndicator(new ThinLineSpinFadeLoaderIndicator());
            avi.setIndicatorColor(Color.WHITE); // 修改颜色
            TextView tipTextView = (TextView) contentView.findViewById(R.id.tipTextView);// 提示文字
            tipTextView.setText(message);// 设置加载信息
            dialog.setCancelable(cancelable);// 不可以用“返回键”取消
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.setContentView(contentView, layoutParams);// 设置布局
            if (onKeyListener != null) {
                dialog.setOnKeyListener(onKeyListener);
            }
            dialog.setBuilder(this);
            return dialog;
        }
    }
}
