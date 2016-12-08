package com.mo.mobileoffice.function.upload.tool;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 软引用缓存类
 */
public class SoftCache<K, V> {

	/** 建立线程安全,支持高并发的容器 **/
	private ConcurrentHashMap<K, SoftRef<K, V>> mCache;
	/** 引用队列 **/
	private ReferenceQueue<V> mQueue;
	
	public SoftCache() {
		mCache = new ConcurrentHashMap<K, SoftRef<K, V>>();
		mQueue = new ReferenceQueue<V>();
	}
	
	/**
	 * 通过key获得value实例，如果返回null，表示该value已经被垃圾回收
	 */
	public V get(K key) {
		V value = null;
		if (mCache.containsKey(key)) {
			value = mCache.get(key).get();
		}
		
		if (value == null) {// 当为null是表示value被垃圾回收，调用清理ConcurrentHashMap<>中对应的软引用
			clearCache();
		}
		
		return value;
	}
	
	/**
	 * 将key，value存入缓存
	 */
	public void put(K key, V value) {
		SoftRef<K, V> valueRef = new SoftRef<K, V>(key, value, mQueue);
		mCache.put(key, valueRef);
	}
	
	/** 当value被垃圾回收，那么SoftRef将没什么作用，清除SoftRef **/
	@SuppressWarnings("unchecked")
	private void clearCache() {
		SoftRef<K, V> bitmapRef = null;
		while ((bitmapRef = (SoftRef<K, V>) mQueue.poll()) != null) {
			mCache.remove(bitmapRef._key);
		}
	}
	
	/**
	 * 清楚Cache内的全部内容
	 */
	public void clearAllCache() {
		clearCache();
		mCache.clear();
		System.gc();
		System.runFinalization();
	}
	
	@SuppressWarnings("hiding")
	private class SoftRef<K, V> extends SoftReference<V> {
		public K _key;
		
		public SoftRef(K key, V r, ReferenceQueue<? super V> q) {
			super(r, q);
			this._key = key;
		}
	}
}