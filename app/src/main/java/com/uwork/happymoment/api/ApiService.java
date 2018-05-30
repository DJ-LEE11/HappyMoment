package com.uwork.happymoment.api;

import com.uwork.happymoment.mvp.hotel.bean.HotCityLabelBean;
import com.uwork.happymoment.mvp.hotel.bean.HotelItemBean;
import com.uwork.happymoment.mvp.login.bean.LoginRequestBean;
import com.uwork.happymoment.mvp.login.bean.RegisterRequestBean;
import com.uwork.happymoment.mvp.login.bean.ResetPasswordRequestBean;
import com.uwork.happymoment.mvp.login.bean.UploadBean;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.happymoment.mvp.main.bean.BannerBean;
import com.uwork.happymoment.mvp.main.bean.JoinStageActivityRequestBean;
import com.uwork.happymoment.mvp.main.bean.StageActivityDetailBean;
import com.uwork.happymoment.mvp.main.bean.StageItemBean;
import com.uwork.happymoment.mvp.main.bean.VideoRespondBean;
import com.uwork.happymoment.mvp.social.chat.bean.AddCreateGroupRequestBean;
import com.uwork.happymoment.mvp.social.chat.bean.AddGroupBean;
import com.uwork.happymoment.mvp.social.chat.bean.ApplyAddFriendRequestBean;
import com.uwork.happymoment.mvp.social.chat.bean.FriendDetailBean;
import com.uwork.happymoment.mvp.social.chat.bean.FriendIndexBean;
import com.uwork.happymoment.mvp.social.chat.bean.GroupBean;
import com.uwork.happymoment.mvp.social.chat.bean.GroupMemberBean;
import com.uwork.happymoment.mvp.social.chat.bean.NewFriendResponseBean;
import com.uwork.happymoment.mvp.social.chat.bean.SearchFriendBean;
import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendBean;
import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendForCodeRequestBean;
import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendForIdRequestBean;
import com.uwork.happymoment.mvp.social.chat.bean.SearchNewFriendForPhoneRequestBean;
import com.uwork.happymoment.mvp.social.chat.bean.UpdateFriendInfoRequestBean;
import com.uwork.happymoment.mvp.social.circle.bean.HomeCircleBean;
import com.uwork.happymoment.mvp.social.circle.bean.MomentsItemResponseBean;
import com.uwork.happymoment.mvp.social.circle.bean.SendCircleRequestBean;
import com.uwork.librx.bean.PageResponseBean;
import com.uwork.librx.rx.BaseResult;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    /**
     * 上传图片
     */
    @Multipart
    @POST("/file/uploadImage")
    Flowable<BaseResult<UploadBean>> uploadImage(@Part() MultipartBody.Part image);

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
     * 搜索好友
     */
    @POST("/friend/searchFriend")
    Flowable<BaseResult<List<SearchFriendBean>>> getSearchFriend(@Query("keyWord") String keyWord);

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
     * 群聊列表
     */
    @POST("/group/groupList")
    Flowable<BaseResult<List<GroupBean>>> getGroupList();

    /**
     * 群聊成员
     */
    @POST("/group/groupUsers")
    Flowable<BaseResult<List<GroupMemberBean>>> getGroupMember(@Query("groupId") Integer groupId);

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
     * 搜索新朋友(通过id)
     */
    @POST("/user/searchUser")
    Flowable<BaseResult<SearchNewFriendBean>> searchNewFriendForId(@Body SearchNewFriendForIdRequestBean searchNewFriendForIdRequestBean);

    /**
     * 申请添加好友
     */
    @POST("/apply/insert")
    Flowable<BaseResult<String>> applyAddFriend(@Body ApplyAddFriendRequestBean applyAddFriendRequestBean);

    /**
     * 通过验证添加好友
     */
    @POST("/friend/makeFriend")
    Flowable<BaseResult<String>> makeFriend(@Query("friendId") Integer friendId);

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
     * 评论动态
     */
    @POST("/api/moments/discussMoments")
    Flowable<BaseResult<Boolean>> discussMoment(@Query("messageId") Integer messageId,
                                                @Query("comment") String comment);

    /**
     * 回复动态
     */
    @POST("/api/moments/discussMoments")
    Flowable<BaseResult<Boolean>> replyMoment(@Query("messageId") Integer messageId,
                                              @Query("toUserId") Integer toUserId,
                                              @Query("comment") String comment);

    /**
     * 删动态
     */
    @POST("/api/moments/deleteMoments")
    Flowable<BaseResult<Boolean>> deleteMoment(@Query("messageId") Integer messageId);

    /**
     * 发动态
     */
    @POST("/api/moments/issueMoments")
    Flowable<BaseResult<Boolean>> sendMoment(@Body SendCircleRequestBean sendCircleRequestBean);

    /**
     * 查看自己动态
     */
    @POST("/api/moments/getMomentsListSelf")
    Flowable<BaseResult<PageResponseBean<HomeCircleBean>>> getMomentsListSelf(@Query("pageNum") Integer pageNum, @Query("pageSize") Integer pageSize);

    /**
     * 查看他人动态
     */
    @POST("/api/moments/getMomentsListOther")
    Flowable<BaseResult<PageResponseBean<HomeCircleBean>>> getMomentsListOther(@Query("userId") Integer userId,@Query("pageNum") Integer pageNum, @Query("pageSize") Integer pageSize);

    //===========================================================================>我的

    /**
     * 退出登录
     */
    @POST("/user/logout")
    Flowable<BaseResult<String>> logout();

    //===========================================================================>首页

    /**
     * 轮播图
     * type:1为首页头部 2为首页中部 4为幸福驿站 8为桃源客栈
     */
    @POST("/banner/banners")
    Flowable<BaseResult<List<BannerBean>>> getBanner(@Query("type") Integer type);

    /**
     * 热门推荐
     */
    @POST("/recommend/recommends")
    Flowable<BaseResult<List<VideoRespondBean>>> getRecommend();

    /**
     * 幸福驿站
     * name不传返回全部
     */
    @POST("/stage/list")
    Flowable<BaseResult<List<StageItemBean>>> getStageList(@Query("name") String name);

    /**
     * 驿站活动详情
     */
    @POST("/stage/detail")
    Flowable<BaseResult<StageActivityDetailBean>> getStageActivityDetail(@Query("id") Integer id);

    /**
     * 参加活动
     */
    @POST("/stage/join")
    Flowable<BaseResult<String>> joinStageActivity(@Body JoinStageActivityRequestBean joinStageActivityRequestBean);

    //===========================================================================>客栈

    /**
     * 热门城市标签
     */
    @POST("/hostel/classify")
    Flowable<BaseResult<List<HotCityLabelBean>>> getHotCityLabel();

    /**
     * 客栈列表
     */
    @POST("/hostel/hostels")
    Flowable<BaseResult<List<HotelItemBean>>> hotelList(@Query("classifyId") Integer classifyId);
}
