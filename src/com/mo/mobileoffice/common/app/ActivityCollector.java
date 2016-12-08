package com.mo.mobileoffice.common.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityCollector {

	public static List<Activity> activities = new ArrayList<Activity>();
	
	/** 添加活动 **/
	public static void addActivity(Activity activity) {
		if (!activities.contains(activity)) {
			activities.add(activity);
		}
	}
	
	/** 移除活动 **/
	public static void removeActivity(Activity activity) {
		activities.remove(activity);
	}
	
	/** 结束所有的活动 **/
	public static void finishAll() {
		for (Activity activity : activities) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
		activities.clear();
	}
}
