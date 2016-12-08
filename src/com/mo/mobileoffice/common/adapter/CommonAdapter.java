package com.mo.mobileoffice.common.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 万能适配器
 * 
 * @author Paul
 * 
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> mLists;
	protected final int mItemLayoutId;

	public CommonAdapter(Context context, List<T> list, int itemLayoutId) {
		this.mContext = context;
		this.mLists = list;
		this.mItemLayoutId = itemLayoutId;
	}

	public void setList(List<T> list) {
		if (list != null) {
			this.mLists = list;
		}
	}

	public void removeAll() {
		if (mLists != null && mLists.size() > 0) {
			List<T> list = mLists;
			mLists.removeAll(list);
		}
	}

	@Override
	public int getCount() {
		return mLists.size();
	}

	@Override
	public T getItem(int position) {
		return mLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder = ViewHolder.get(mContext, convertView,
				parent, mItemLayoutId);
		convert(viewHolder, getItem(position), position);
		return viewHolder.getContentView();
	}

	public abstract void convert(ViewHolder holder, T item, final int position);
}
