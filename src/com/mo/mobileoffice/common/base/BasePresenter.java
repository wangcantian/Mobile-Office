package com.mo.mobileoffice.common.base;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.dialog.LoadingDialog;
import com.mo.mobileoffice.common.net.OkHttpClientManager;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.net.RequestFactory;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.common.tool.NetTool;
import com.mo.mobileoffice.common.widget.DefaultToast;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public abstract class BasePresenter {
	public static final int SUCCESS = 200;
	public static final int OFFLINE_FLAG = 403;

	protected Handler mUIHandler;
	protected Context mContext;

	public BasePresenter(Context context) {
		this.mContext = context;
		mUIHandler = new Handler(Looper.getMainLooper());
	}

	protected Handler getUIHandler() {
		return mUIHandler;
	}

	protected Context getContext() {
		return mContext;
	}

	/** 普通请求 **/
	protected void request(ACTION action, Object requestObject,
			final CallBack callback) {
		if (NetTool.isNetWorkAvailable(mContext)) {
			Request request = RequestFactory.createRequest(action,
					requestObject);
			OkHttpClientManager.asyn(request, new InterceptCallBack(callback));
		} else {
			toastShowOnUI(R.string.no_net);
		}
	}

	/** 上传请求 **/
	protected void upload(ACTION action, File[] files, String[] filenames,
			Object requestObject, final IUploadCallBack callback) {
		if (NetTool.isNetWorkAvailable(mContext)) {
			Request request = RequestFactory.createUploadRequest(action, files,
					filenames, requestObject);
			OkHttpClientManager.asynUpload(request, new InterceptCallBack(
					callback), callback);
		} else {
			toastShowOnUI(R.string.no_net);
		}
	}

	/** Toast在UI线程上显示 **/
	protected void toastShowOnUI(final String msg) {
		mUIHandler.post(new Runnable() {

			@Override
			public void run() {
				DefaultToast.show(mContext, msg);
			}
		});
	}

	/** Toast在UI线程上显示 **/
	protected void toastShowOnUI(final int resId) {
		mUIHandler.post(new Runnable() {

			@Override
			public void run() {
				String msg = mContext.getResources().getString(resId);
				DefaultToast.show(mContext, msg);
			}
		});
	}

	/** 拦截回调，判断是否强制下线 **/
	private class InterceptCallBack implements Callback {
		private CallBack callback;

		public InterceptCallBack(CallBack callback) {
			this.callback = callback;
		}

		@Override
		public void onFailure(Request arg0, IOException arg1) {
			System.out.println("onFailure");
			toastShowOnUI(R.string.request_exception);
			this.callback.onFailure(arg0, arg1);
		}

		@Override
		public void onResponse(Response arg0) throws IOException {
			System.out.println("onResponse");
			final String string = arg0.body().string();
			if (GsonTool.getInt(string, "flag") == OFFLINE_FLAG) {
				// 强制下线
				Intent intent = new Intent("ForceOfflineBroadCaseReceiver");
				mContext.sendBroadcast(intent);
				return;
			}
			try {
				this.callback.onResponse(string);
			} catch(Exception e) {
				getUIHandler().post(new Runnable() {
					
					@Override
					public void run() {
						DefaultToast.show(getContext(), getContext().getResources().getString(R.string.net_args_error));
						dismissProgressDialog();
					}
				});
			}
		}

	}

	protected void baseShowDialog() {
		mUIHandler.post(new Runnable() {

			@Override
			public void run() {
				showDialog();
			}
		});
	}

	protected void baseShowProgressDialog() {
		mUIHandler.post(new Runnable() {

			@Override
			public void run() {
				showProgressDialog();
			}
		});
	}

	protected void baseDismissProgressDialog() {
		mUIHandler.post(new Runnable() {

			@Override
			public void run() {
				dismissProgressDialog();
			}
		});
	}

	protected void baseShowErrorDialog() {
		mUIHandler.post(new Runnable() {

			@Override
			public void run() {
				showErrorDialog();
			}
		});
	}

	public void showDialog() {
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
		mBuilder.setMessage("数据已成功发送");
		mBuilder.setCancelable(false);
		mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				((Activity) mContext).finish();
			}
		});
		mBuilder.show();
	}

	private LoadingDialog mDialog;

	public void showProgressDialog() {
		if (mDialog == null) {
			mDialog = new LoadingDialog(mContext);
		}
		mDialog.show(false);
	}

	public void dismissProgressDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
		}
	}

	public void showErrorDialog() {
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
		mBuilder.setMessage("数据发送失败");
		mBuilder.setCancelable(false);
		mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		mBuilder.show();
	}

	public interface CallBack {
		public void onResponse(String responseStr) throws IOException;

		public void onFailure(Request request, IOException exception);
	}

	public interface IUploadCallBack extends CallBack {
		public void onRequestProgress(long bytesWritten, long contentLength,
				boolean done);
	}
}
