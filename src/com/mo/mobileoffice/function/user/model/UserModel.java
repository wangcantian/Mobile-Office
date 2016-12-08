package com.mo.mobileoffice.function.user.model;

import android.content.Context;

import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.common.tool.PreTool;
import com.mo.mobileoffice.function.user.bean.UserBean;

public class UserModel {
	
	private Context mContext;
	
	public UserModel(Context context) {
		mContext = context;
	}
	/** 
	 * 获得当前用户头像
	 */
	public String getUserHeadPic() {
		return GsonTool.getContent(PreTool.getString(mContext, "user_info", ""), "pic_url");
	}

	/**
	 * 获取当前用户ID
	 */
	public String getUserId() {
		String data = PreTool.getString(mContext, "user_info", "");
		return GsonTool.getContent(data, "id");
	}
	
	/**
	 * 获取当前用户token
	 */
	public String getUserToken() {
		String data = PreTool.getString(mContext, "user_info", "");
		return GsonTool.getContent(data, "token");
	}
	
	/**
	 * 获取当前用户的level
	 */
	public String getUserLevel() {
		String data = PreTool.getString(mContext, "user_info", "");
		return GsonTool.getContent(data, "level");
	}
	
	/**
	 * 获得当前用户对象
	 */
	public UserBean getCurrUserInfo() {
		String data = PreTool.getString(mContext, "user_info", "");
		return GsonTool.getData(data, UserBean.class);
	}
	
	/** 
	 * 修改当前用户信息
	 */
	public void notifyCurrUserInfo(UserBean bean) {
		String json = GsonTool.toJson(bean);
		PreTool.setString(mContext, "user_info", json);
	}
	
	/**
	 * 当前用户是否有发公告权限
	 */
	public boolean isCanSendAnno() {
		return getUserLevel().equals("1") ? true : false;
	}
}
