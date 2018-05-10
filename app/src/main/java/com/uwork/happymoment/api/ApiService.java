package com.uwork.happymoment.api;

import com.uwork.happymoment.mvp.login.bean.LoginRequestBean;
import com.uwork.happymoment.mvp.login.bean.RegisterRequestBean;
import com.uwork.happymoment.mvp.login.bean.ResetPasswordRequestBean;
import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.librx.rx.BaseResult;

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

    //发送验证码
    @POST("user/sendMessage")
    Flowable<BaseResult<String>> sendMessage(@Query("phone") String phone);

    //注册
    @POST("/user/register")
    Flowable<BaseResult<String>> register(@Body RegisterRequestBean registerRequestBean);

    //登录
    @POST("/user/login")
    Flowable<BaseResult<UserBean>> login(@Body LoginRequestBean loginRequestBean);

    //登录
    @POST("/user/resetPassword")
    Flowable<BaseResult<String>> resetPassword(@Body ResetPasswordRequestBean resetPasswordRequestBean);
}
