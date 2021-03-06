package com.example.circle_base_ui.widget.popup;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;

import com.example.circle_base_ui.R;
import com.example.circle_base_ui.util.ViewUtil;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by 大灯泡 on 2017/3/2.
 * <p>
 * 选择照片popup
 */

public class SelectPhotoMenuPopup extends BasePopupWindow implements View.OnClickListener {

    private View shoot;
    private View album;
    private View cancel;

    private OnSelectPhotoMenuClickListener listener;

    public SelectPhotoMenuPopup(Activity context) {
        super(context);

        shoot = findViewById(R.id.shoot);
        album = findViewById(R.id.album);
        cancel = findViewById(R.id.cancel);

        ViewUtil.setViewsClickListener(this, shoot, album, cancel);
        //背景模糊
//        setBlurBackgroundEnable(true);
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 500);
    }

    @Override
    protected Animation initExitAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 500);
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_select_photo);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_container);
    }

    public OnSelectPhotoMenuClickListener getOnSelectPhotoMenuClickListener() {
        return listener;
    }

    public SelectPhotoMenuPopup setOnSelectPhotoMenuClickListener(OnSelectPhotoMenuClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.shoot) {
            if (listener != null) {
                listener.onShootClick();
            }
            dismissWithOutAnima();

        } else if (i == R.id.album) {
            if (listener != null) {
                listener.onAlbumClick();
            }
            dismissWithOutAnima();

        } else if (i == R.id.cancel) {
            dismiss();

        }
    }

    public interface OnSelectPhotoMenuClickListener {
        void onShootClick();

        void onAlbumClick();
    }
}
