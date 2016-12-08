package com.mo.mobileoffice.common.net;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

import com.mo.mobileoffice.common.net.OkHttpClientManager.IDownloadCallBack;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.ResponseBody;

public class ProgressResponseBody extends ResponseBody {
	private final ResponseBody mResponseBody;
	private final IDownloadCallBack mProgressResponseListener;
	private BufferedSource mBufferedSource;
	
	public ProgressResponseBody(ResponseBody responseBody, IDownloadCallBack progressResponseListener) {
		mResponseBody = responseBody;
		mProgressResponseListener = progressResponseListener;
	}

	@Override
	public long contentLength() throws IOException {
		return mResponseBody.contentLength();
	}

	@Override
	public MediaType contentType() {
		return mResponseBody.contentType();
	}

	@Override
	public BufferedSource source() throws IOException {
		if (mBufferedSource == null) {
			mBufferedSource = Okio.buffer(source(mResponseBody.source()));
		}
		return mBufferedSource;
	}
	
	private Source source(Source source) {
		return new ForwardingSource(source) {
			// 当前读取字节数
			long totalBytesRead = 0l;
			@Override
			public long read(Buffer sink, long byteCount) throws IOException {
				long bytesRead =  super.read(sink, byteCount);
				// 如果读取完成了byteRead会返回-1
				totalBytesRead += (bytesRead != -1) ? bytesRead : 0;
				if (mProgressResponseListener != null)
					mProgressResponseListener.onResponseProgress(totalBytesRead, mResponseBody.contentLength(), bytesRead == -1);
				return bytesRead;
			}
		};
	}
}
