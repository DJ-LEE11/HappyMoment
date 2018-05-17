package com.example.circle_common.common.manager;

import com.example.circle_base_library.helper.AppSetting;
import com.example.circle_common.common.entity.UserInfo;


/**
 * Created by 大灯泡 on 2016/10/28.
 * <p>
 * 本地用户管理
 */

public enum LocalHostManager {

    INSTANCE;

    private UserInfo localHostInfo = new UserInfo();

    public boolean init() {
        localHostInfo.setUsername(AppSetting.loadStringPreferenceByKey(AppSetting.HOST_NAME, "razerdp"));
        localHostInfo.setAvatar(AppSetting.loadStringPreferenceByKey(AppSetting.HOST_AVATAR,
                                                                     "http://upload.jianshu.io/users/upload_avatars/684042/bd1b2f796e3a.jpg?imageMogr/thumbnail/90x90/quality/100"));
        localHostInfo.setNick(AppSetting.loadStringPreferenceByKey(AppSetting.HOST_NICK, "羽翼君"));
        localHostInfo.setObjectId(AppSetting.loadStringPreferenceByKey(AppSetting.HOST_ID, "MMbKLCCU"));
        localHostInfo.setCover(AppSetting.loadStringPreferenceByKey(AppSetting.HOST_COVER, "http://d.hiphotos.baidu.com/zhidao/pic/item/bf096b63f6246b601ffeb44be9f81a4c510fa218.jpg"));
        return true;
    }

    public String getUsername() {
        return localHostInfo.getUsername();
    }

    public void setUsername(String username) {
        localHostInfo.setUsername(username);
    }

    public String getUserid() {
        return localHostInfo.getObjectId();
    }

    public String getNick() {
        return localHostInfo.getNick();
    }

    public void setNick(String nick) {
        localHostInfo.setNick(nick);
    }

    public String getAvatar() {
        return localHostInfo.getAvatar();
    }

    public void setAvatar(String avatar) {
        localHostInfo.setAvatar(avatar);
    }


    public UserInfo getLocalHostUser() {
        return localHostInfo;
    }

}
