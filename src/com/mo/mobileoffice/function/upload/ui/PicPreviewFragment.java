package com.mo.mobileoffice.function.upload.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.function.upload.adapter.PreviewAdapter;
import com.mo.mobileoffice.function.upload.contract.PicPreviewContract;
import com.mo.mobileoffice.function.upload.contract.PicPreviewContract.Presenter;
import com.mo.mobileoffice.function.upload.presenter.PicPreviewPresenter;

public class PicPreviewFragment extends MvpIdeaFragment<PicPreviewContract.Presenter> implements PicPreviewContract.View  {
	private ViewPager vp_preview;
	private CheckBox cb_preview;
	
	private PreviewAdapter mAdapter;
	private ArrayList<String> mPicDatas; // 上个activity传进来的数据
	private ArrayList<String> mNowDatas; // setResult的数据
	
	@Override
	protected void init() {
		mPicDatas = getBundle().getStringArrayList("pic_list");
		mNowDatas = new ArrayList<String>();
		mNowDatas.addAll(mPicDatas);
		
		vp_preview = findViewById(R.id.vp_preview);
		cb_preview = findViewById(R.id.cb_preview);
		
		vp_preview.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				cb_preview.setTag(arg0);
				cb_preview.setChecked(!StringTool.isEmpty(mNowDatas.get(arg0)));
				setTitle((arg0 + 1) + " / " + mPicDatas.size());
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		cb_preview.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				int position = (int) buttonView.getTag();
				if (!isChecked) {
					mNowDatas.set(position, "");
					cb_preview.setText(getResources().getString(R.string.unselected));
				} else {
					mNowDatas.set(position, mPicDatas.get(position));
					cb_preview.setText(getResources().getString(R.string.selected));
				}
			}
		});
		
		cb_preview.setTag(0);
		cb_preview.setText(getResources().getString(R.string.selected));
		setTitle("1 / " + mPicDatas.size());
		setRight(getResources().getString(R.string.finish));
		mAdapter = new PreviewAdapter(getActivity(), mPicDatas);
		vp_preview.setAdapter(mAdapter);
	}
	
	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void rightOnClick() {
		List<String> list = new ArrayList<String>();
		list.add("");
		mNowDatas.removeAll(list);
		Intent intent = new Intent();
		intent.putStringArrayListExtra("pic_list", mNowDatas);
		getActivity().setResult(Activity.RESULT_OK, intent);
		getActivity().finish();
	}
	
	@Override
	public void leftOnClick() {
		List<String> list = new ArrayList<String>();
		list.add("");
		mNowDatas.removeAll(list);
		Intent intent = new Intent();
		intent.putStringArrayListExtra("pic_list", mNowDatas);
		getActivity().setResult(Activity.RESULT_CANCELED, intent);
		getActivity().finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			leftOnClick();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_pic_preview;
	}

	@Override
	protected Presenter createPresenter() {
		return new PicPreviewPresenter(getActivity());
	}

}
