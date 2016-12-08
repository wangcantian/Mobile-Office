package com.mo.mobileoffice.function.checkin.contract;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import com.mo.mobileoffice.common.adapter.CommonAdapter;
import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.checkin.bean.PoiAddressSave;

public interface MapContract {

	interface Presenter extends MvpPresenter<View> {
		public void initMapStyle();

		public void initMyLocation();

		// 初始化adapter
		public CommonAdapter<PoiAddressSave> initAdapter(Context context, List<PoiAddressSave> list, int viewId);

		public <T> void adapterListView(ListView listView, CommonAdapter<T> adapter);

		public void setForResult(Activity activity, Intent intent);
	}

	interface View extends MvpView {
		// 初始化界面的风格的接口
		public void initMapLocationStyle();

		// 初始化定位功能
		public void initLocation();
	}
}
