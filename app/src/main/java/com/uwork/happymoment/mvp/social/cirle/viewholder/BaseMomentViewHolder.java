package com.uwork.happymoment.mvp.social.cirle.viewholder;

import android.support.annotation.NonNull;
import android.view.View;

/**
 *
 * 抽象出的vh接口
 */

public interface BaseMomentViewHolder<T> {

    void onFindView(@NonNull View rootView);

    void onBindDataToView(@NonNull final T data, int position, int viewType);
}
