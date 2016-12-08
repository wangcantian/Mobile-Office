package com.mo.mobileoffice.common.tool;

import java.io.File;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.Environment;

public class FileTool {
	/** 外置缓存的文件夹名 **/
	public static final String CACHE_FILENAME = "MoblieOffice/";
	/** 外置压缩缓存文件夹名 **/
	public static final String COMPRESS_PATH = CACHE_FILENAME + "Compress";
	/** 外置相机缓存文件夹名 **/
	public static final String CAMERA_PATH = CACHE_FILENAME + "Camera";
	/** 外置存储文件夹名 **/
	public static final String CACHE_PATH = CACHE_FILENAME + "Cache";
	
	/** 检查SD卡的状态 **/
	public static boolean getStorageEnable() {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return false;
		return true;
	}
	
	/** 获得压缩图片文件夹路径 **/
	public static String getPublicCompressDir() {
		return getPublicCompressFile().getPath();
	}
	
	/** 获得缓存文件夹（外置SD卡） **/
	public static File getPublicCacheFile() {
		File file = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES), CACHE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	
	/** 获得相机图片文件夹 **/
	public static File getPublicCameraFile() {
		File file = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES), CAMERA_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	
	/** 获得压缩图片文件夹 **/
	public static File getPublicCompressFile() {
		File file = new File(Environment.getExternalStoragePublicDirectory(
								Environment.DIRECTORY_PICTURES), COMPRESS_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	
	/** 获得缓存文件 **/
	public static File getDiskCacheDir(Context context, String filename) {
		return new File(context.getCacheDir(), filename);
	}
	
	/** 清除CD卡上的缓存 **/
	public static boolean clearCDCardCache() {
		boolean flag = true;
		if (!deleteDirectory(getPublicCacheFile())) flag = false;
		if (!deleteDirectory(getPublicCompressFile())) flag = false;
		return flag;
	}
	
	public static boolean deleteDirectory(File deleteFile) {
		boolean flag = false;
		if (!deleteFile.exists() || !deleteFile.isDirectory()) {
			return false;
		}
		flag = true;
		File[] files = deleteFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = files[i].delete();
				if (!flag) break;
			} else {
				flag = deleteDirectory(files[i]);
				if (!flag) break;
			}
		}
		if (!flag) return false;
		return deleteFile.delete();
	}
	
	/** 通过路径获得文件数组 **/
	public static File[] getFiles(String[] paths) {
		File[] files = new File[paths.length];
		for (int i = 0; i < paths.length; i++) {
			files[i] = new File(paths[i]);
		}
		return files;
	}
	
	/** 获得文件名的数组 **/
	public static String[] getFilaNames(String[] paths) {
		String[] names = new String[paths.length];
		for (int i = 0; i < names.length; i++) {
			names[i] = getFileName(paths[i]);
		}
		return names;
	}
	
	/** 将集合转换成数组 **/
	public static String[] getStringArray(List<String> list) {
		String[] paths = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			paths[i] = list.get(i);
		}
		return paths;
	}
	
	/** 通过路径获得文件的文件名 **/
	public static String getFileName(String path) {
		int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
	}
	
	/** 获得压缩后的路径集合数组 **/
	public static String[] getPicCompressPaths(String[] path) {
		String[] paths = new String[path.length];
		for (int i = 0; i < path.length; i++) {
			String newPath = FileTool.getPublicCompressDir() + "/" + getFileName(path[i]);
			paths[i] = ImageTool.compressImage(path[i], newPath, 300);
		}
		return paths;
	}
	
	/** 以当前时间为文件名获得拍照图片存储文件 **/
	public static File getPublicCameraUri() {
		String timeStamp = StringTool.DataToString3(new Date());
		return new File(FileTool.getPublicCameraFile(), timeStamp + ".jpg");
	}
}
