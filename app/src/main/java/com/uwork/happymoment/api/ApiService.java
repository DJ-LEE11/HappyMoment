package com.uwork.happymoment.api;

import com.uwork.happymoment.mvp.login.bean.LoginRequestBean;
import com.uwork.happymoment.mvp.login.bean.RegisterRequestBean;
import com.uwork.happymoment.mvp.login.bean.ResetPasswordRequestBean;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.mvp.social.chat.bean.AddCreateGroupRequestBean;
import com.uwork.happymoment.mvp.social.chat.bean.AddGroupBean;
import com.uwork.happymoment.mvp.social.chat.bean.ApplyAddFriendRequestBean;
import com.uwork.happymoment.mvp.social.chat.bean.FriendDetailBean;
import com.uwork.happymoment.mvp.social.chat.bean.FriendIndexBean;
import com.uwork.happymoment.mvp.social.chat.bean.NewFriendResponseBean;
import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendBean;
import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendForCodeRequestBean;
import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendForPhoneRequestBean;
import com.uwork.happymoment.mvp.social.chat.bean.UpdateFriendInfoRequestBean;
import com.uwork.happymoment.mvp.social.cirle.bean.MomentsItemResponseBean;
import com.uwork.librx.bean.PageResponseBean;
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

    /**
     * 创建群聊
     */
    @POST("/group/createGroup")
    Flowable<BaseResult<AddGroupBean>> addGroup(@Body AddCreateGroupRequestBean addCreateGroupRequestBean);

    /**
     * 新朋友
     */
    @POST("/friend/newFriends")
    Flowable<BaseResult<NewFriendResponseBean>> getNewFriend();

    /**
     * 搜索新朋友(通过手机)
     */
    @POST("/user/searchUser")
    Flowable<BaseResult<SearchNewFriendBean>> searchNewFriendForPhone(@Body SearchNewFriendForPhoneRequestBean searchNewFriendForPhoneRequestBean);

    /**
     * 搜索新朋友(通过二维码)
     */
    @POST("/user/searchUser")
    Flowable<BaseResult<SearchNewFriendBean>> searchNewFriendForCode(@Body SearchNewFriendForCodeRequestBean searchNewFriendForCodeRequestBean);

    /**
     * 申请添加好友
     */
    @POST("/apply/insert")
    Flowable<BaseResult<String>> applyAddFriend(@Body ApplyAddFriendRequestBean applyAddFriendRequestBean);

    /**
     * 通过验证添加好友
     */
    @POST("/friend/makeFriend")
    Flowable<BaseResult<String>> makeFriend(@Query("friendIs") Integer friendIs);

    /**
     * 查看朋友圈列表
     */
    @POST("/api/moments/getMomentsList")
    Flowable<BaseResult<PageResponseBean<MomentsItemResponseBean>>> getMomentsList(@Query("pageNum") Integer pageNum, @Query("pageSize") Integer pageSize);

    /**
     * 点赞
     */
    @POST("/api/moments/favourMoments")
    Flowable<BaseResult<Boolean>> giveLikeMoment(@Query("messageId") Integer messageId);

    /**
     * 评论朋友
     */
    @POST("/api/moments/discussMoments")
    Flowable<BaseResult<Boolean>> discussMoment(@Query("toUserId") Integer toUserId,
                                                @Query("messageId") Integer messageId,
                                                @Query("comment") String comment);

    /**
     * 删动态
     */
    @POST("/api/moments/favourMoments")
    Flowable<BaseResult<Boolean>> deleteMoment(@Query("messageId") Integer messageId);
}
