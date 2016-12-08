package com.mo.mobileoffice.common.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {

	private final SparseArray<View> mViews;
	private View mContentView;

	public ViewHolder(Context context, ViewGroup parent, int layoutId) {
		mViews = new SparseArray<View>();
		mContentView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		mContentView.setTag(this);
	}

	public static ViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId);
		}
		return (ViewHolder) convertView.getTag();
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mContentView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public View getContentView() {
		return mContentView;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId, String text) {
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param bm
	 * @return
	 */
	public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView iv = getView(viewId);
		iv.setImageBitmap(bm);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawable
	 * @return
	 */
	public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
		ImageView iv = getView(viewId);
		iv.setImageDrawable(drawable);
		return this;
	}
	
	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawable
	 * @return
	 */
	public ViewHolder setImageResId(int viewId, int id) {
		ImageView iv = getView(viewId);
		iv.setImageResource(id);
		return this;
	}
	
	public ViewHolder setImageViewVisiable(int viewId, int visiable) {
		ImageView iv = getView(viewId);
		iv.setVisibility(visiable);
		return this;
	}
}
