package com.mo.mobileoffice.common.helper;

import java.io.IOException;

import com.mo.mobileoffice.common.base.BasePresenter.IUploadCallBack;
import com.mo.mobileoffice.common.net.OkHttpClientManager.IDownloadCallBack;
import com.mo.mobileoffice.common.net.ProgressRequestBody;
import com.mo.mobileoffice.common.net.ProgressResponseBody;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class ProgressHelper {
	public static OkHttpClient addProgressResponseListener(OkHttpClient client, final IDownloadCallBack listener) {
		// 克隆
		OkHttpClient clone = client.clone();
		clone.networkInterceptors().add(new Interceptor() {

			@Override
			public Response intercept(Chain arg0) throws IOException {
				// 拦截
				Response originalResponse = arg0.proceed(arg0.request());
				return originalResponse
						.newBuilder()
						.body(new ProgressResponseBody(originalResponse.body(),listener))
						.build();
			}

		});
		return clone;
	}
	
	public static ProgressRequestBody addProgressRequestListener(RequestBody requestBody, IUploadCallBack listener) {
		return new ProgressRequestBody(requestBody, listener);
	}
}
