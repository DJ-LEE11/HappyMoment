package com.uwork.happymoment.mvp.login.bean;

import java.io.Serializable;

/**
 * @author 李栋杰
 * @time 2017/8/17  17:04
 * @desc ${TODD}
 */
public class UploadBean implements Serializable {
    public String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
