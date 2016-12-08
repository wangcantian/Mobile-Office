package com.mo.mobileoffice.common.tool;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;

public class ImageTool {
	public static Drawable changeBitmapSize(Drawable drawable, int width, int height) {
		drawable.setBounds(0, 0, width, height);
		return drawable;
	}
	
	/** 压缩图片并保存到newPath中，没有压缩返回oldPath中 **/
	public static String compressImage(String oldPath, String newPath, long sizeOfKB) {
		Bitmap bitmap = BitmapFactory.decodeFile(oldPath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 90, baos);
		byte[] bytes = baos.toByteArray();
		if (bytes.length / 1024 > sizeOfKB) {
			double comp = (double)sizeOfKB / (bytes.length / 1024);
			int options = (int) (comp * 100);
			baos.reset();
			bitmap.compress(CompressFormat.JPEG, options, baos);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(newPath);
				bytes = baos.toByteArray();
				fos.write(bytes, 0, bytes.length);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fos.close();
					baos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return newPath;
		} else {
			return oldPath;
		}
	}
	
	/** 按比例缩放图片并保存到newPath中，没有压缩返回oldPath中 **/
	public static String compressImage(String oldPath, String newPath, int width, int height) {
		Bitmap bitmap = getCompressBitmap(oldPath, width, height);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(newPath);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return newPath;
	}
	
	/** 获得压缩到指定大小的位图 **/
	public static Bitmap compressImage(Bitmap bitmap, long sizeOfKb) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > sizeOfKb) {
			options -= 10;
			baos.reset();
			bitmap.compress(CompressFormat.JPEG, options, baos);
		}
		byte[] bytes = baos.toByteArray();
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}
	
	/** 获得按比例缩放的位图 **/
	public static Bitmap getCompressBitmap(String path, int width, int height) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = caculateInSampleSize(opts, width, height);
		return BitmapFactory.decodeFile(path, opts);
	}
	
	/** 获得按比例缩放的图片流 **/
	public static Bitmap getCompressBitmap(InputStream is, int width, int height) {
		try {
			is = new BufferedInputStream(is);
			is.mark(is.available());
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, opts);
			opts.inJustDecodeBounds = false;
			is.reset();
			opts.inSampleSize = caculateInSampleSize(opts, width, height);
			return BitmapFactory.decodeStream(is, null, opts);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/** 计算图片的缩放比例 **/
	public static int caculateInSampleSize(Options opts, int width, int height) {
		int h = opts.outHeight;
		int w = opts.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth > beHeight) {
			be = beHeight;
		} else {
			be = beWidth;
		}
		if (be <= 1) {
			be = 1;
		}
		return be;
	}
}
