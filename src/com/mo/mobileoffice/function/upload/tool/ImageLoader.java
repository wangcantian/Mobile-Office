package com.mo.mobileoffice.function.upload.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.net.OkHttpClientManager;
import com.mo.mobileoffice.common.net.OkHttpClientManager.IResultCallBack;
import com.mo.mobileoffice.common.tool.FileTool;
import com.mo.mobileoffice.common.tool.ImageTool;
import com.mo.mobileoffice.common.tool.MD5Encoder;
import com.mo.mobileoffice.function.upload.bean.BitmapInfo;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class ImageLoader {
	private BitmapCache mCache;
	private Type mType;
	// 任务队列
	private LinkedList<Runnable> mTaskQueue;
	// 线程池
	private ExecutorService mThreadPool = null;
	private Thread mPoolThread;
	// Handler
	private Handler mPoolThreadHandler;
	private Handler mUIHandler;
	// 信号量
	private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
	private Semaphore mSemaphoreThreadPool;

	private final int mThreadCount = 4;
	private static ImageLoader mImageLoader;

	public enum Type {
		FIFO, LIFO
	}
	
	public static ImageLoader getInstance() {
		if (mImageLoader == null) {
			synchronized (ImageLoader.class) {
				if (mImageLoader == null) {
					mImageLoader = new ImageLoader();
				}
			}
		}
		return mImageLoader;
	}

	private ImageLoader() {
		initBackThread();
		
		mType = Type.LIFO;
		mCache = new BitmapCache();
		mUIHandler = new Handler(Looper.getMainLooper());
		mSemaphoreThreadPool = new Semaphore(mThreadCount);
		mThreadPool = Executors.newFixedThreadPool(mThreadCount);
		mTaskQueue = new LinkedList<Runnable>();
	}

	private void initBackThread() {
		// 后台轮询线程
		mPoolThread = new Thread() {
			
			@SuppressLint("HandlerLeak") @Override
			public void run() {
				Looper.prepare();
				mPoolThreadHandler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						// 消耗一个信息量
						try {
							mSemaphoreThreadPool.acquire();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Runnable runnable = null;
						if (!mThreadPool.isShutdown() && (runnable = getTask()) != null)
							mThreadPool.execute(runnable);
					}
				};
				mSemaphorePoolThreadHandler.release();
				Looper.loop();
			}
		};
		mPoolThread.start();
	}
	
	/** 给ImageView设置图片 **/
	public void loadImageFormDisk(ImageView view, String path, int defaultResId, int width, int height) {
		view.setTag(path);
		BitmapInfo bitmapInfo = mCache.getBitmapFromCache(path);
		if (bitmapInfo != null&&bitmapInfo.bitmap!=null)
			if (bitmapInfo.width == width && bitmapInfo.height == height) {
				showBitmap(view, path, bitmapInfo.bitmap);
				return;
			}
		view.setImageResource(defaultResId);
		addTask(buildTask(view, path, width, height));
	}
	
	public void loadImageFormNet(final ImageView view, String url, final int width, final int height, final boolean diskCache) {
		String md5Filename = null;
		try {
			md5Filename = MD5Encoder.encode(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		final File imageFile = new File(FileTool.getPublicCacheFile(), md5Filename);
		if (imageFile.exists()) {
			loadImageFormDisk(view, imageFile.getPath(), R.drawable.bg_default_pic, width, height);
			return;
		}
		OkHttpClientManager.asyn(new Request.Builder().url(url).build(), new IResultCallBack() {
			
			@Override
			public void onResponse(Response arg0) throws IOException {
				InputStream is = arg0.body().byteStream();
				if (diskCache) {
					FileOutputStream fos = new FileOutputStream(imageFile);
					byte[] bytes = new byte[1024];
					int len = 0;
					while((len = is.read(bytes)) != -1) {
						fos.write(bytes, 0, len);
					}
					fos.close();
					is.close();
					mUIHandler.post(new Runnable() {
						
						@Override
						public void run() {
							loadImageFormDisk(view, imageFile.getPath(), R.drawable.bg_default_pic, width, height);
						}
					});	
				} else {
					final Bitmap bitmap = ImageTool.getCompressBitmap(is, 100, 100);
					mUIHandler.post(new Runnable() {
						
						@Override
						public void run() {
							view.setImageBitmap(bitmap);
						}
					});
				}
			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				
			}
			
			@Override
			public void onStart() {
				
			}
			
			@Override
			public void onFinish() {
				
			}
		});
	}

	/** 创建一个任务 **/
	private Runnable buildTask(ImageView view, String path, int width, int height) {
		return new LoadIamgeRunnable(path, view, width, height);
	}
	
	/** 把任务加入队列 **/
	private void addTask(Runnable runnable) {
		mTaskQueue.add(runnable);
		
		if (mPoolThreadHandler == null) {
			try {
				mSemaphorePoolThreadHandler.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		mPoolThreadHandler.sendEmptyMessage(0x111);
	}
	
	/** 从任务队列取出一个方法 **/
	private Runnable getTask() {
		if (mType == Type.FIFO)
			return mTaskQueue.removeFirst();
		else if (mType ==Type.LIFO)
			return mTaskQueue.removeLast();
		return null;
	}
	
	/** bitmap显示在ImageView上 **/
	private synchronized void showBitmap(ImageView view, String path, Bitmap bitmap) {
		if (view.getTag().equals(path)) {
			view.setImageBitmap(bitmap);
		}
	}
	
	/** 清楚所有的缓存 **/
	public void clearCache() {
		mCache.clearCache();
		mTaskQueue.clear();
//		mTaskQueue = null;
		mImageLoader = null;
	}

	/** 取消线程池的所有任务 **/
	public void cancelTask() {
		if (mThreadPool != null) {
			mThreadPool.shutdown();
		}
		if (mPoolThread != null) {
			if (mPoolThreadHandler != null) {
				mPoolThreadHandler.removeMessages(0x111);
			}
			mPoolThread.interrupt();
		}
	}
	
	private class LoadIamgeRunnable implements Runnable {
		private String mPath;
		private ImageView mImageView;
		private int mWidth;
		private int mHeight;
		
		public LoadIamgeRunnable(String path, ImageView iv, int width, int height) {
			this.mPath = path;
			this.mImageView = iv;
			this.mWidth = width;
			this.mHeight = height;
		}
		
		@Override
		public void run() {
			final Bitmap bitmap = ImageTool.getCompressBitmap(mPath, mWidth, mHeight);
			if (bitmap != null) {
				mCache.addBitmapToCache(mPath, new BitmapInfo(bitmap, mWidth, mHeight));
				mUIHandler.post(new Runnable() {
					
					public void run() {
						showBitmap(mImageView, mPath, bitmap);
					}
				});
			}
			mSemaphoreThreadPool.release();
		}
	}
}
