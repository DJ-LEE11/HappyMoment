package com.uwork.happymoment.api;

import com.uwork.happymoment.mvp.login.bean.LoginRequestBean;
import com.uwork.happymoment.mvp.login.bean.RegisterRequestBean;
import com.uwork.happymoment.mvp.login.bean.ResetPasswordRequestBean;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.mvp.social.chat.bean.FriendDetailBean;
import com.uwork.happymoment.mvp.social.chat.bean.FriendIndexBean;
import com.uwork.happymoment.mvp.social.chat.bean.UpdateFriendInfoRequestBean;
import com.uwork.librx.rx.BaseResult;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author 李栋杰
 * @time 2018/5/7  下午4:49
 * @desc 接口
 */
public interface ApiService {

    //===========================================================================>登录注册模块
    /**
     * 发送验证码
     */
    @POST("user/sendMessage")
    Flowable<BaseResult<String>> sendMessage(@Query("phone") String phone);

    /**
     * 注册
     */
    @POST("/user/register")
    Flowable<BaseResult<String>> register(@Body RegisterRequestBean registerRequestBean);

    /**
     * 登录
     */
    @POST("/user/login")
    Flowable<BaseResult<UserBean>> login(@Body LoginRequestBean loginRequestBean);

    /**
     * 重置密码
     */
    @POST("/user/resetPassword")
    Flowable<BaseResult<String>> resetPassword(@Body ResetPasswordRequestBean resetPasswordRequestBean);

    //===========================================================================>幸福时刻模块
    /**
     * 好友通讯录列表
     */
    @POST("/friend/friends")
    Flowable<BaseResult<List<FriendIndexBean>>> getFriendsIndex();

    /**
     * 好友详情
     */
    @POST("/friend/detail")
    Flowable<BaseResult<FriendDetailBean>> getFriendDetail(@Query("friendId") String friendId);

    /**
     * 更改好友信息
     */
    @POST("/friend/update")
    Flowable<BaseResult<String>> updateFriendInfo(@Body UpdateFriendInfoRequestBean updateFriendInfoRequestBean);

}
