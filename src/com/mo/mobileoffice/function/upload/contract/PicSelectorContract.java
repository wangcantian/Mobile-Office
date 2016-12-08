package com.mo.mobileoffice.function.upload.contract;

import java.util.List;

import android.widget.BaseAdapter;

import com.mo.mobileoffice.common.mvp.MvpPresenter;
import com.mo.mobileoffice.common.mvp.MvpView;
import com.mo.mobileoffice.function.upload.bean.PicFolder;

public interface PicSelectorContract {

	interface Presenter extends MvpPresenter<View> {
		/** 获得手机所有图片的文件夹MAP集合 **/
		public List<PicFolder> getAllPicFolders();
		/** 更新数据并通知适配器 **/
		public <E> void updataAndNotifyAdapter(BaseAdapter adapter, List<E> adapterDatas, List<E> newDatas);
		/** 将路径添加到集合中 **/
		public void addPicToPicFolder(List<PicFolder> lists, String path);
	}
	
	interface View extends MvpView {
		
	}
}
