package com.mo.mobileoffice.function.announce.contract;

import java.util.List;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.announce.bean.AnnounceBean;

public interface AnnoContract {

	interface Presenter extends MvpPresenter<View> {
		/** 请求所有的公告 **/
		public void requestAllAnno(boolean isPullToRefresh);
		/** 请求将公告标记为已读状态 **/
		public void requestChangeAnnoState(int anno_id);
		/** 初始化适配器的数据 **/
		public void initAdapter();
		/** 筛选栏的点击事件 **/
		public void onSwitchTextClick(int id);
		/** 请求删除公共 **/
		public void requestDelAnno(int id);
	}
	
	interface View extends MvpView {
		/** 初始化适配器数据 **/
		public void initAdapter(List<AnnounceBean> lists);
		/** 通知适配器数据更改 **/
		public void notifyDataSetChange();
		/** 重置下拉刷新头布局 **/
		public void resetListViewHead();
		/** 改变列表的状态 **/
		public void setTextViewChecked(int id);
	}
}
