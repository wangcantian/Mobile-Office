package com.mo.mobileoffice.function.upload.bean;

import android.graphics.Bitmap;

public class BitmapInfo {

	public Bitmap bitmap;
	public int width;
	public int height;

	public BitmapInfo(Bitmap bitmap, int width, int height) {
		super();
		this.bitmap = bitmap;
		this.width = width;
		this.height = height;
	}

}
