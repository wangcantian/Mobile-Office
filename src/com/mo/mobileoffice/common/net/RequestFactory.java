package com.mo.mobileoffice.common.net;

import java.io.File;

import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

public class RequestFactory {
	/** 创建一个okhttp请求 **/
	public static Request createRequest(ACTION action, Object requestBean) {
		return buildRequest(action, requestBean);
	}

	/** 创建用于上传文件的请求 **/
	public static Request createUploadRequest(ACTION action, File[] files,
			String[] filenames, Object requestBean) {
		return buildUpload(action, files, filenames, requestBean);
	}

	public static Request buildRequest(ACTION action, Object requestBean) {
		String json = GsonTool.toJson(requestBean);
		RequestBody body = new FormEncodingBuilder().add(RequestArr.requestArg,
				json).build();
		String url = RequestArr.mainUrl + RequestArr.mUrls.get(action);
		return new Request.Builder().url(url).post(body).build();
	}

	private static Request buildUpload(ACTION action, File[] files,
			String[] filenames, Object requestBean) {
		String json = GsonTool.toJson(requestBean);
		MultipartBuilder builder = new MultipartBuilder()
				.type(MultipartBuilder.FORM);
		if (files != null) {
			for (int i = 0; i < filenames.length; i++) {
				builder.addPart(Headers.of("Content-Disposition",
						"form-data;name=\"" + "file" + i + "\";filename=\""
								+ filenames[i] + "\""), RequestBody.create(
						MediaType.parse("application/octet-stream"), files[i]));
			}
		}
		builder.addPart(
				Headers.of("Content-Disposition", "form-data; name=\""
						+ RequestArr.requestArg + "\""),
				RequestBody.create(null, json));
		String url = RequestArr.mainUrl + RequestArr.mUrls.get(action);
		return new Request.Builder().url(url).post(builder.build()).build();
	}
}
