package com.mo.mobileoffice.function.announce.ui;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.common.tool.ImageTool;
import com.mo.mobileoffice.function.announce.contract.SendAnnoContract;
import com.mo.mobileoffice.function.announce.contract.SendAnnoContract.Presenter;
import com.mo.mobileoffice.function.announce.presenter.SendAnnoPresenter;

public class SendAnnoFragment extends
		MvpIdeaFragment<SendAnnoContract.Presenter> implements
		SendAnnoContract.View {

	private EditText et_title;
	private EditText et_content;
	private ImageView iv_camera;
	private GridLayout gl_pic;

	@Override
	protected void init() {
		et_title = findViewById(R.id.et_anno_title);
		et_content = findViewById(R.id.et_anno_content);
		iv_camera = findViewById(R.id.iv_camera);
		gl_pic = findViewById(R.id.gl_pic);

		iv_camera.setOnClickListener(this);

		setTitle(getResources().getString(R.string.send_anno));
		setRight(getResources().getString(R.string.send));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_camera:
			getPresenter().openPicSelector();
			break;
		default:
			break;
		}
	}

	@Override
	public void rightOnClick() {
		String title = et_title.getText().toString();
		String content = et_content.getText().toString();
		getPresenter().requestSendAnno(title, content);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getPresenter().onActivityResult(requestCode, resultCode, data);
	}

	@SuppressLint("InflateParams")
	@Override
	public void notifyGridLayoutDraw(List<String> pathLists) {
		gl_pic.removeAllViews();
		for (String path : pathLists) {
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.listitem_del_pic, null);
			ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
			final ImageView iv_del = (ImageView) view.findViewById(R.id.iv_del);
			iv_del.setTag(path);
			iv_del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					getPresenter().removePath((String) iv_del.getTag());
				}
			});
			iv_pic.setImageBitmap(ImageTool.getCompressBitmap(path, 120, 120));
			gl_pic.addView(view);
		}
	}

	@Override
	public void SendSuccess() {
		getActivity().setResult(1);
		getActivity().finish();
	}
	
	@Override
	public void openPicSelector(Bundle bundle, int requestCode) {
		openIdeaActivityForResult(FragmentEnum.FRAGMENT_PIC_SELECTOR, requestCode, bundle);
	}
	
	@Override
	protected int setContentViewId() {
		return R.layout.fragment_send_anno;
	}

	@Override
	protected Presenter createPresenter() {
		return new SendAnnoPresenter(getActivity());
	}



}
