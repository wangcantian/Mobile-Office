package com.mo.mobileoffice.common.tool;

import android.content.Context;
import android.content.SharedPreferences;

public class PreTool {
	public static final String PRES_NAME = "config";
	
	public static String getString(Context context, String key, String defValue) {
		SharedPreferences sp = context.getSharedPreferences(PRES_NAME, Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}
	
	public static void setString(Context ct, String key, String value) {
		SharedPreferences sp = ct.getSharedPreferences(PRES_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}

	public static Boolean getBoolean(Context ct, String key, Boolean defaultValue) {
		SharedPreferences sp = ct.getSharedPreferences(PRES_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defaultValue);
	}

	public static void setBoolean(Context ct, String key, Boolean value) {
		SharedPreferences sp = ct.getSharedPreferences(PRES_NAME, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}
	public static int getInt(Context ct, String key, int defaultValue) {
		SharedPreferences sp = ct.getSharedPreferences(PRES_NAME, Context.MODE_PRIVATE);
		return sp.getInt(key, defaultValue);
	}
	
	public static void setInt(Context ct, String key, int value) {
		SharedPreferences sp = ct.getSharedPreferences(PRES_NAME, Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}
}
