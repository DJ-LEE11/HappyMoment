package com.uwork.happymoment.manager;

/**
 * Created by jie on 2018/5/24.
 */

public class MapManager {
    private static MapManager mInstance;

    private MapManager() {
    }

    public static MapManager getInstance() {
        if (mInstance == null) {
            synchronized (UserManager.class) {
                if (mInstance == null) {
                    mInstance = new MapManager();
                }
            }
        }
        return mInstance;
    }

}
