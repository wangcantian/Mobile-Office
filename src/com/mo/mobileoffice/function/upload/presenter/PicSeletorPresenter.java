package com.mo.mobileoffice.function.upload.presenter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.BaseAdapter;

import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.function.upload.bean.PicFolder;
import com.mo.mobileoffice.function.upload.contract.PicSelectorContract;

public class PicSeletorPresenter extends
		BaseMvpPresenter<PicSelectorContract.View> implements
		PicSelectorContract.Presenter {

	public PicSeletorPresenter(Context context) {
		super(context);
	}

	@Override
	public void detachView(boolean retainInstance) {
		
	}

	@Override
	public List<PicFolder> getAllPicFolders() {
		Map<String, PicFolder> folderMap = new LinkedHashMap<String, PicFolder>();
		PicFolder allPicFolder = new PicFolder(PicFolder.ALL, null);
		folderMap.put("AllPic", allPicFolder);
		
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
		while (cursor.moveToNext()) {
			String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			allPicFolder.add(path);
			
			File parentFile = new File(path).getParentFile();
			if (parentFile == null)
				continue;
			
			PicFolder folder = null;
			String parentPath = parentFile.getPath();
			if (folderMap.containsKey(parentPath)) {
				folder = folderMap.get(parentPath);
				folder.add(path);
			} else {
				folder = new PicFolder(PicFolder.UNIT, parentPath);
				folder.add(path);
				folder.setFirstPicPath(path);
				folderMap.put(parentPath, folder);
			}
		}
		cursor.close();
		return translation(folderMap);
	}
	
	private List<PicFolder> translation(Map<String, PicFolder> folderMap) {
		List<PicFolder> list = new ArrayList<PicFolder>();
		list.addAll(folderMap.values());
		return list;
	}

	@Override
	public <E> void updataAndNotifyAdapter(BaseAdapter adapter,
			List<E> adapterDatas, List<E> newDatas) {
		adapterDatas.clear();
		adapterDatas.addAll(newDatas);
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}

	@Override
	public void addPicToPicFolder(List<PicFolder> lists, String path) {
		boolean isNoFolder = true;
		File parentFile = new File(path).getParentFile();
		String parentPath = null;
		if (parentFile != null) 
			parentPath = parentFile.getPath();
		for (PicFolder folder : lists) {
			if (folder.getType() == PicFolder.ALL) {
				folder.add(path);
			} else {
				if (parentPath != null && folder.getPath().equals(parentPath)) {
					isNoFolder = false;
					folder.add(path);
				}
			}
		}
		if (isNoFolder && parentPath != null) {
			PicFolder folder = new PicFolder(PicFolder.UNIT, parentPath);
			folder.add(path);
			folder.setFirstPicPath(path);
			lists.add(folder);
		}
	}

}
