package com.mo.mobileoffice.function.upload.tool;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.util.LruCache;

import com.mo.mobileoffice.function.upload.bean.BitmapInfo;

public class BitmapCache {
	private final int mMaxSize = (int) (Runtime.getRuntime().maxMemory() / 8);

	private BitmapLruCache mLruCache;
	private SoftCache<String, BitmapInfo> mSoftCache;

	public BitmapCache() {
		mLruCache = new BitmapLruCache(mMaxSize);
		mSoftCache = new SoftCache<String, BitmapInfo>();
	}

	public synchronized BitmapInfo getBitmapFromCache(String path) {
		BitmapInfo bitmap = mLruCache.get(path);

		if (bitmap == null) {
			bitmap = mSoftCache.get(path);
		}

		return bitmap;
	}

	public synchronized void addBitmapToCache(String path, BitmapInfo bitmap) {
		if (getBitmapFromCache(path) == null && bitmap != null)
			mLruCache.put(path, bitmap);
	}

	/** 清除所有的缓存 **/
	public void clearCache() {
		mLruCache.evictAll();
		mSoftCache.clearAllCache();
	}

	private class BitmapLruCache extends LruCache<String, BitmapInfo> {

		public BitmapLruCache(int maxSize) {
			super(maxSize);
		}

		@Override
		protected int sizeOf(String key, BitmapInfo value) {
//			if (value != null && value.bitmap != null) {
//				return value.bitmap.getByteCount();
//			} else {
//				return -1;
//			}\return value.bitmap.getByteCount();
			return value.bitmap.getByteCount();
		}

		@Override
		protected void entryRemoved(boolean evicted, String key,
				BitmapInfo oldValue, BitmapInfo newValue) {
			super.entryRemoved(evicted, key, oldValue, newValue);
			mSoftCache.put(key, oldValue);
		}
	}

	private List<BitmapInfo> bitmapInfo = new ArrayList<>();

	public List<BitmapInfo> getBitmapInfo() {
		return bitmapInfo;
	}

	public void setBitmapInfo(List<BitmapInfo> bitmapInfo) {
		this.bitmapInfo = bitmapInfo;
	}

}