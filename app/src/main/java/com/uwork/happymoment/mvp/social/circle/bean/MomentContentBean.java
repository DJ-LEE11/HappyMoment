package com.uwork.happymoment.mvp.social.circle.bean;

import android.text.TextUtils;

import com.example.circle_base_library.utils.StringUtil;
import com.example.circle_base_library.utils.ToolUtil;
import com.example.circle_common.common.MomentsType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 大灯泡 on 2016/10/28.
 * <p>
 * 朋友圈内容
 */

public class MomentContentBean implements Serializable {
    //文字
    private String text;
    //图片
    private List<String> pics;
    //web
    private String webUrl;
    //webTitle
    private String webTitle;
    //web封面
    private String webImage;

    public MomentContentBean() {
    }

    public MomentContentBean(String text, List<String> pics) {
        this.text = text;
        this.pics = pics;
    }

    public MomentContentBean(String text, List<String> pics, String webUrl, String webTitle, String webImage) {
        this.text = text;
        this.pics = pics;
        this.webUrl = webUrl;
        this.webTitle = webTitle;
        this.webImage = webImage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebImage() {
        return webImage;
    }

    public void setWebImage(String webImage) {
        this.webImage = webImage;
    }


    /**
     * 获取动态的类型
     *
     * @return
     * @see MomentsType
     */
    public int getMomentType() {
        int type = MomentsType.TEXT_ONLY;
        //图片列表为空，则只能是文字或者web
        if (ToolUtil.isListEmpty(pics)) {
            if (StringUtil.noEmpty(webUrl)) {
                type = MomentsType.WEB;
            } else {
                type = MomentsType.TEXT_ONLY;
            }
        } else {
            type = MomentsType.MULTI_IMAGES;
        }
        return type;
    }


    public MomentContentBean addText(String text) {
        this.text = text;
        return this;
    }

    public MomentContentBean addPicture(String pic) {
        if (pics == null) {
            pics = new ArrayList<>();
        }
        if (pics.size() < 9) {
            pics.add(pic);
        }
        return this;
    }

    public MomentContentBean addWebUrl(String webUrl) {
        this.webUrl = webUrl;
        return this;
    }

    public MomentContentBean addWebTitle(String webTitle) {
        this.webTitle = webTitle;
        return this;
    }

    public MomentContentBean addWebImage(String webImage) {
        this.webImage = webImage;
        return this;
    }

    public boolean isValided() {
        if (ToolUtil.isListEmpty(pics)) {
            if (TextUtils.isEmpty(text)) {
                if (!StringUtil.noEmpty(webUrl, webTitle)) return false;
            }
        }
        return true;

    }


}
