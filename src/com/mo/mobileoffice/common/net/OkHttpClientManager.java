package com.mo.mobileoffice.common.net;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.mo.mobileoffice.common.base.BasePresenter.IUploadCallBack;
import com.mo.mobileoffice.common.helper.ProgressHelper;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class OkHttpClientManager {
	private static final int TIMEOUT = 10;

	private static OkHttpClientManager mInstance;
	private OkHttpClient mOkHttpClient;

	public static OkHttpClientManager getInstance() {
		if (mInstance == null) {
			synchronized (OkHttpClientManager.class) {
				if (mInstance == null) {
					mInstance = new OkHttpClientManager();
				}
			}
		}
		return mInstance;
	}

	private OkHttpClientManager() {
		mOkHttpClient = new OkHttpClient();
		mOkHttpClient.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(TIMEOUT, TimeUnit.SECONDS);
	}

	/** 普通请求 **/
	public static void asyn(Request request, IResultCallBack callBack) {
		getInstance()._asyn(request, callBack);
	}
	
	/** 普通请求 **/
	public static void asyn(Request request, Callback callBack) {
		getInstance()._asyn(request, callBack);
	}
//	
//	/** 上传请求 **/
//	public static void asynUpload(Request request, IUploadCallBack callBack ) {
//		getInstance()._asynUpload(request, callBack);
//	}
	
	/** 上传请求 **/
	public static void asynUpload(Request request, Callback callBack, IUploadCallBack uploadcallBack) {
		getInstance()._asynUpload(request, callBack, uploadcallBack);
	}
	
//	private void _asynUpload(Request request, IUploadCallBack callBack) {
//		RequestBody body = null;
//		if ((body = request.body()) != null) {
//			RequestBody newBody = ProgressHelper.addProgressRequestListener(body, callBack);
//			request = request.newBuilder().post(newBody).build();
//		}
//		deliveryResult(request, callBack);
//	}
	
	private void _asynUpload(Request request, Callback callBack, IUploadCallBack uploadcallBack) {
		RequestBody body = null;
		if ((body = request.body()) != null) {
			RequestBody newBody = ProgressHelper.addProgressRequestListener(body, uploadcallBack);
			request = request.newBuilder().post(newBody).build();
		}
		mOkHttpClient.newCall(request).enqueue(callBack);
	}
	
	private void _asyn(Request request, Callback callBack) {
		mOkHttpClient.newCall(request).enqueue(callBack);
	}
	
	private void _asyn(Request request, IResultCallBack callBack) {
		deliveryResult(request, callBack);
	}
	
	private void deliveryResult(Request request, final IResultCallBack callBack) {
		callBack.onStart();
		mOkHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {
				callBack.onFinish();
				callBack.onResponse(arg0);
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				callBack.onFinish();
				callBack.onFailure(arg0, arg1);
			}
		});
	}
	
	/** 下载回调 **/
	public interface IDownloadCallBack extends IResultCallBack {
		public void onResponseProgress(long bytesRed, long contentLength, boolean done);
	}
//	
//	/** 上传回调 **/
//	public interface IUploadCallBack extends IResultCallBack {
//		public void onRequestProgress(long bytesWritten, long contentLength, boolean done);
//	}

	/** 普通请求回调 **/
	public interface IResultCallBack extends Callback {
		public void onStart();
		public void onFinish();
	}

}
