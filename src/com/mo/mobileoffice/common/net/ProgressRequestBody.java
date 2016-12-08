package com.mo.mobileoffice.common.net;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

import com.mo.mobileoffice.common.base.BasePresenter.IUploadCallBack;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

public class ProgressRequestBody extends RequestBody {
	private final RequestBody mRequestBody;
	private final IUploadCallBack mProgressRequestListener;
	private BufferedSink mBufferedSink;
	
	public ProgressRequestBody(RequestBody requestBody, IUploadCallBack progressRequestListener) {
		mRequestBody = requestBody;
		mProgressRequestListener = progressRequestListener;
	}

	@Override
	public MediaType contentType() {
		return mRequestBody.contentType();
	}
	
	@Override
	public long contentLength() throws IOException {
		return mRequestBody.contentLength();
	}

	@Override
	public void writeTo(BufferedSink arg0) throws IOException {
		if (mBufferedSink == null) {
			mBufferedSink = Okio.buffer(sink(arg0));
		}
		// 写入
		mRequestBody.writeTo(mBufferedSink);
		// 必须调用flush， 否则最后一部分数据可能不会被写入
		mBufferedSink.flush();
	}

	private Sink sink(Sink sink) {
		return new ForwardingSink(sink) {
			// 当前写入字节数
			long bytesWritten = 0l;
			// 总字节长度
			long contentLength = 0l;
			@Override
			public void write(Buffer source, long byteCount) throws IOException {
				super.write(source, byteCount);
				if (contentLength == 0) {
					contentLength = contentLength();
				}
				bytesWritten += byteCount;
				if (mProgressRequestListener != null)
					mProgressRequestListener.onRequestProgress(bytesWritten, contentLength, bytesWritten == contentLength);
			}
		};
	}
}
