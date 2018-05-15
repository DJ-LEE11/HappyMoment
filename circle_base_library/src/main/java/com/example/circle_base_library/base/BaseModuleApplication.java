package com.example.circle_base_library.base;

import android.app.Application;

import com.example.circle_base_library.api.AppContext;
import com.example.circle_base_library.manager.localphoto.LocalPhotoManager;


/**
 * Created by 大灯泡 on 2017/4/1.
 * <p>
 * module的application父类..主要用来初始ARouter等
 */

public class BaseModuleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.initARouter();
        LocalPhotoManager.INSTANCE.registerContentObserver(null);
    }

}
