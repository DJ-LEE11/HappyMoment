package com.uwork.happymoment.manager;

import android.content.Context;
import android.text.TextUtils;

import com.uwork.happymoment.mvp.login.bean.UserBean;
import com.uwork.librx.rx.http.HTTPInterceptor;
import com.uwork.libutil.SharedFileUtils;

public class UserManager {
	public static final String LOGIN_TOKEN = "LOGIN_TOKEN";
	public static final String USER_ID = "USER_ID";
	public static final String RONGIM_TOKEN = "RONGIM_TOKEN";
	public static final String PHONE = "PHONE";
	public static final String PHOTO = "PHOTO";
	public static final String USER = "USER";



	private SharedFileUtils mSharedFileUtils;
	private static UserManager mInstance;


	private UserManager() {
	}

	public static UserManager getInstance() {
		if (mInstance == null) {
			synchronized (UserManager.class) {
				if (mInstance == null) {
					mInstance = new UserManager();
				}
			}
		}
		return mInstance;
	}

	/**
	 * 判断是否是登录状态
	 * @param context
	 * @return
	 */
	public boolean isLogin(Context context) {
		return !TextUtils.isEmpty(getToken(context));
	}

	/**
	 * 获取token
	 * @param context
	 * @return
	 */
	public String getToken(Context context) {
		if (mSharedFileUtils == null){
			mSharedFileUtils = new SharedFileUtils(context);
		}
		String token = mSharedFileUtils.getString(LOGIN_TOKEN);
		HTTPInterceptor.setToken(token);
		return token;
	}

	/**
	 * 保存token
	 * @param context
	 * @param token
	 */
	public void saveToken(Context context, String token) {
		if (mSharedFileUtils == null){
			mSharedFileUtils = new SharedFileUtils(context);
		}
		mSharedFileUtils.putString(LOGIN_TOKEN,token);
		HTTPInterceptor.setToken(token);
	}

	/**
	 * 获取用户Id
	 * @param context
	 * @return
	 */
	public int getUserId(Context context) {
		if (mSharedFileUtils == null){
			mSharedFileUtils = new SharedFileUtils(context);
		}
		return mSharedFileUtils.getInt(USER_ID);
	}

	/**
	 * 保存用户Id
	 * @param context
	 * @param userId
	 */
	public void saveUserId(Context context, int userId) {
		if (mSharedFileUtils == null){
			mSharedFileUtils = new SharedFileUtils(context);
		}
		mSharedFileUtils.putInt(USER_ID,userId);
	}

	/**
	 * 获取融云Token
	 * @param context
	 * @return
	 */
	public String getRongIMToken(Context context) {
		if (mSharedFileUtils == null){
			mSharedFileUtils = new SharedFileUtils(context);
		}
		return mSharedFileUtils.getString(RONGIM_TOKEN);
	}

	/**
	 * 保存融云Token
	 * @param context
	 * @param rongIMToken
	 */
	public void saveRongIMToken(Context context, String rongIMToken) {
		if (mSharedFileUtils == null){
			mSharedFileUtils = new SharedFileUtils(context);
		}
		mSharedFileUtils.putString(RONGIM_TOKEN,rongIMToken);
	}

	/**
	 * 获取用户手机
	 * @param context
	 * @return
	 */
	public String getPhone(Context context) {
		if (mSharedFileUtils == null){
			mSharedFileUtils = new SharedFileUtils(context);
		}
		return mSharedFileUtils.getString(PHONE);
	}

	/**
	 * 保存用户手机
	 * @param context
	 * @param phone
	 */
	public void savePhone(Context context, String phone) {
		if (mSharedFileUtils == null){
			mSharedFileUtils = new SharedFileUtils(context);
		}
		mSharedFileUtils.putString(PHONE,phone);
	}

	/**
	 * 获取用户头像
	 * @param context
	 * @return
	 */
	public String getHeader(Context context) {
		if (mSharedFileUtils == null){
			mSharedFileUtils = new SharedFileUtils(context);
		}
		return mSharedFileUtils.getString(PHOTO);
	}

	/**
	 * 保存用户头像
	 * @param context
	 * @param photo
	 */
	public void saveHeader(Context context, String photo) {
		if (mSharedFileUtils == null){
			mSharedFileUtils = new SharedFileUtils(context);
		}
		mSharedFileUtils.putString(PHOTO,photo);
	}



	/**
	 * 保存用户
	 */
	public void saveUser(Context context, UserBean userBean){
		if (mSharedFileUtils == null){
			mSharedFileUtils = new SharedFileUtils(context);
		}
		mSharedFileUtils.putBean(USER,userBean);
	}

	/**
	 * 获取用户
	 */
	public UserBean getUser(Context context){
		if (mSharedFileUtils == null){
			mSharedFileUtils = new SharedFileUtils(context);
		}
		return (UserBean) mSharedFileUtils.getBean(USER);
	}

	/**
	 *保存用户信息
	 */
	public void saveUserInfo(Context context, UserBean userBean){
		saveToken(context,userBean.getToken());
		saveRongIMToken(context,userBean.getImtoken());
		saveUserId(context,userBean.getId());
		savePhone(context,userBean.getPhone());
		saveHeader(context,userBean.getAvatar());
		saveUser(context,userBean);
	}

	/**
	 * 保存用户信息（Token除外）
	 */
	public void saveUserInfoBesidesToken(Context context, UserBean userBean){
		saveRongIMToken(context,userBean.getImtoken());
		saveUserId(context,userBean.getId());
		savePhone(context,userBean.getPhone());
		saveHeader(context,userBean.getAvatar());
		saveUser(context,userBean);
	}

	/**
	 * 退出登录
	 * @param context
	 */
	public void logout(Context context) {
		if (mSharedFileUtils == null){
			mSharedFileUtils = new SharedFileUtils(context);
		}
		mSharedFileUtils.remove(LOGIN_TOKEN);
		mSharedFileUtils.remove(USER_ID);
		mSharedFileUtils.remove(RONGIM_TOKEN);
		mSharedFileUtils.remove(PHONE);
		mSharedFileUtils.remove(PHOTO);
		mSharedFileUtils.remove(USER);
		HTTPInterceptor.setToken("");
	}
}
