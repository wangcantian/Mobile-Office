package com.mo.mobileoffice.common.base;

import android.app.Application;

import com.mo.mobileoffice.common.net.RequestArr;
import com.mo.mobileoffice.common.tool.AssetsTool;

public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		String url = AssetsTool.readAssets(getApplicationContext(), "ipconfig");
		RequestArr.mainUrl = url;
	}
}
