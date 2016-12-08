package com.mo.mobileoffice.function.upload.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.tool.DisplayTool;
import com.mo.mobileoffice.function.upload.tool.ImageLoader;

public class PicSeletorAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> mList;
	private OnPicSelectedListener mListener;

	public PicSeletorAdapter(Context context, List<String> list, OnPicSelectedListener listener) {
		mContext = context;
		mList = list;
		mListener = listener;
	}

	@Override
	public int getCount() {
		return mList.size() + 1;
	}

	@Override
	public String getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" }) @Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (position == 0) {
			ImageView imageView = new ImageView(mContext);
			imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, DisplayTool.dp2px(mContext, 100)));
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setImageResource(R.drawable.ico_camera);
			return imageView;
		} 
		View view = LayoutInflater.from(mContext).inflate(R.layout.listitem_pic_selector, null);
		final ImageView imageView = (ImageView) view.findViewById(R.id.iv_pic);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		CheckBox check = (CheckBox) view.findViewById(R.id.cb_select);
		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			private String path = getItem(position - 1);
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!isChecked) {
					imageView.clearColorFilter();
					mListener.onPicRemove(path);
				} else {
					boolean checkable = mListener.onPicSelected(path);
					buttonView.setChecked(checkable);
					if (checkable)
						imageView.setColorFilter(Color.parseColor("#77000000"));
				}
			}
		});
		view.setTag(check);
		check.setChecked(mListener.isPicPathSelected(getItem(position - 1)));
		
//		Picasso.with(mContext).load(getItem(position - 1)).into(imageView);
//		Picasso.with(mContext).load(new File(getItem(position - 1))).resize(120, 120).into(imageView);
		ImageLoader.getInstance().loadImageFormDisk(imageView, getItem(position - 1), R.drawable.bg_default_pic, 120, 120);
		
		return view;
	}

	public interface OnPicSelectedListener {
		public boolean onPicSelected(String path);
		public void onPicRemove(String path);
		public boolean isPicPathSelected(String path);
	}

}
