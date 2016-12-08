package com.mo.mobileoffice.function.upload.bean;

import java.util.ArrayList;
import java.util.List;

public class PicFolder {
	public static final int ALL = 0;// 标志该文件夹包含所有图片
	public static final int UNIT = 1;// 单位文件夹

	private int type;
	private String path;
	private String filename;
	private String firstPicPath;
	private ArrayList<String> picPaths;

	public PicFolder(int type, String path) {
		if (path != null) {
			this.path = path;
			int index = path.lastIndexOf("/");
			this.filename = path.substring(index + 1);
		}
		this.type = type;
		picPaths = new ArrayList<String>();
	}

	public int getType() {
		return type;
	}

	public List<String> getPicPaths() {
		return picPaths;
	}

	public void add(String path) {
		picPaths.add(path);
	}

	public String getPath() {
		return path;
	}

	public int getCount() {
		return picPaths.size();
	}

	public String getFirstPicPath() {
		return firstPicPath;
	}

	public void setFirstPicPath(String firstPicPath) {
		this.firstPicPath = firstPicPath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof PicFolder))
			return false;
		if (this.type == ALL)
			return false;

		PicFolder folder = (PicFolder) o;
		return this.path.equals(folder.path);
	}

	@Override
	public int hashCode() {
		if (this.type == ALL) {
			return -1;
		}
		return this.path.hashCode();
	}
}
