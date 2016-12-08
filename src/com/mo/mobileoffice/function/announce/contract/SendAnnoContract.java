package com.mo.mobileoffice.function.announce.contract;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;

public interface SendAnnoContract {

	interface Presenter extends MvpPresenter<View> {
		/** 发公告 **/
		public void requestSendAnno(String title, String content);
		
		/** onActivityResult **/
		public void onActivityResult(int requestCode, int resultCode, Intent data);
		
		/** 移除选择的图片 **/
		public void removePath(String path);
		
		/** 请求跳转到图片选择页面 **/
		public void openPicSelector();
	}
	
	interface View extends MvpView {
		/** 通知图片缩略图显示刷新 **/
		public void notifyGridLayoutDraw(List<String> pathLists);
		
		/** 公告发送成功 **/
		public void SendSuccess();
		
		/** 跳转到图片选择页面 **/
		public void openPicSelector(Bundle bundle, int requestCode);
	}
}
