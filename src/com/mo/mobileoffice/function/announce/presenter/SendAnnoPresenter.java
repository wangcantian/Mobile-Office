package com.mo.mobileoffice.function.announce.presenter;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.base.CommBean;
import com.mo.mobileoffice.common.dialog.ProgressDialog;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.net.RequestArr.ACTION;
import com.mo.mobileoffice.common.tool.FileTool;
import com.mo.mobileoffice.common.tool.GsonTool;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.announce.bean.SendAnno_Request;
import com.mo.mobileoffice.function.announce.contract.SendAnnoContract;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.okhttp.Request;

public class SendAnnoPresenter extends BaseMvpPresenter<SendAnnoContract.View>
		implements SendAnnoContract.Presenter {
	private final int MAX_PIC_COUNT = 3; // 最多上传图pain数量
	private final int REQUESTCODE_PHOTO = 2;

	private ArrayList<String> mPathLists;
	private UserModel mUserModel;
	private ProgressDialog mDialog;

	public SendAnnoPresenter(Context context) {
		super(context);
		mUserModel = new UserModel(context);
		mPathLists = new ArrayList<String>();
		mDialog = new ProgressDialog(context);
	}

	@Override
	public void detachView(boolean retainInstance) {

	}

	@Override
	public void requestSendAnno(final String title, final String content) {
		if (StringTool.isEmpty(title)) {
			toastShowOnUI(R.string.anno_title_not_empty);
			return;
		}
		if (StringTool.isEmpty(content)) {
			toastShowOnUI(R.string.anno_content_not_empty);
			return;
		}
		
		// 显示进度条dialog
		mDialog.show(1, "", false);
		new Thread() {

			// 压缩操作耗时，所以开线程
			public void run() {
				// 进行图片压缩并保存到缓存文件下
				final String[] paths = mPathLists.size() > 0 ? FileTool
						.getPicCompressPaths(FileTool
								.getStringArray(mPathLists)) : null;
				SendAnno_Request bean = new SendAnno_Request(title, content, mUserModel.getUserId(), mUserModel.getUserToken());
				if (paths == null) { // 没有图片上传就直接普通的请求
					request(ACTION.ACTION_SEND_ANNO, bean, new CallBack() {

						@Override
						public void onResponse(final String responseStr) throws IOException {
							getUIHandler().post(new Runnable() {
								
								@Override
								public void run() {
									onSendResponse(responseStr);
									mDialog.setProgress(100);
									mDialog.dismiss();
								}
							});
						}

						@Override
						public void onFailure(Request request, IOException exception) {
							getUIHandler().post(new Runnable() {
								
								@Override
								public void run() {
									mDialog.dismiss();
								}
							});
						}
					});
				} else {
					upload(ACTION.ACTION_SEND_ANNO, FileTool.getFiles(paths),
							FileTool.getFilaNames(paths), bean,
							new IUploadCallBack() {

								@Override
								public void onResponse(final String responseStr) throws IOException {
									getUIHandler().post(new Runnable() {
										
										@Override
										public void run() {
											onSendResponse(responseStr);
											mDialog.setProgress(100);
											mDialog.dismiss();
										}
									});
								}

								@Override
								public void onFailure(Request request, IOException exception) {
									getUIHandler().post(new Runnable() {
										
										@Override
										public void run() {
											mDialog.dismiss();
										}
									});
								}

								@Override
								public void onRequestProgress(
										final long bytesWritten, final long contentLength,
										boolean done) {
									getUIHandler().post(new Runnable() {
										
										@Override
										public void run() {
											mDialog.setProgress((int) ((100 * bytesWritten) / contentLength));
											mDialog.setText(StringTool.bytes2kb(bytesWritten) + "/"
													+ StringTool.bytes2kb(contentLength));
										}
									});
								}
							});
				}
				;
			};
		}.start();
	}

	private void onSendResponse(String str) {
		CommBean<?> result = GsonTool.getData(str, CommBean.class);
		if (result.getFlag() == 200) {
			toastShowOnUI(result.getMsg());
			if (getView() != null) {
				getUIHandler().post(new Runnable() {

					@Override
					public void run() {
						getView().SendSuccess();
					}
				});
			}
		} else {
			toastShowOnUI(result.getMsg());
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == REQUESTCODE_PHOTO) {
			mPathLists = data.getStringArrayListExtra("pic_list");
			getView().notifyGridLayoutDraw(mPathLists);
		}
	}

	@Override
	public void removePath(String path) {
		if (mPathLists != null) {
			mPathLists.remove(path);
			getView().notifyGridLayoutDraw(mPathLists);
		}
	}

	@Override
	public void openPicSelector() {
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("pic_list", mPathLists);
		bundle.putInt("pic_count", MAX_PIC_COUNT);
		getView().openPicSelector(bundle, REQUESTCODE_PHOTO);
	}

}
