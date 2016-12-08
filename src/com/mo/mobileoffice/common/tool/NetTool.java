package com.mo.mobileoffice.common.tool;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetTool {

	/** 判断网络是否连接 **/
	public static boolean isNetWorkAvailable(Context context) {
		boolean isNetWork = false;
		ConnectivityManager conn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conn == null || conn.getActiveNetworkInfo() == null
				|| !conn.getActiveNetworkInfo().isAvailable()) {
			isNetWork = false;
		} else {
			isNetWork = true;
		}
		return isNetWork;
	}

}
