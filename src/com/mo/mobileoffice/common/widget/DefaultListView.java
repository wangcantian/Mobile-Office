package com.mo.mobileoffice.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

public class DefaultListView extends ListView {
	private int headerHeight = 0;
	private HeaderView headerView;

	public DefaultListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView(context);
	}

	public DefaultListView(Context context) {
		this(context, null);
	}

	private void initHeaderView(Context context) {
		headerView = new HeaderView(context);
		headerView.setClickable(false);
		mScroller = new Scroller(context);
		headerView.measure(0, 0);
		headerHeight = headerView.getMeasuredHeight();
		headerView.setHeaderHeight(0);
		this.addHeaderView(headerView);
	}

	private float startY = 0.0f;
	private Scroller mScroller;

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int currentY = (int) ((ev.getY() - startY) * 0.7);
			startY = ev.getY();
			if ((headerView.getHeaderHeight() > 0 || currentY > 0)
					&& getFirstVisiblePosition() == 0 && canPullToRefreshViw()) {
				updateHeaderState((currentY * 0.7f));
			}

			break;
		case MotionEvent.ACTION_UP:
			if (headerView.getCurrentState() == HeaderView.STATE_NORMAL) {
				mScroller.startScroll(0, headerView.getHeaderHeight(), 0,
						-headerView.getHeaderHeight(), 300);
			} else if (headerView.getCurrentState() == HeaderView.STATE_PULL) {
				headerView.setState(HeaderView.STATE_REFRESHING);
				mScroller.startScroll(0, headerView.getHeaderHeight(), 0,
						-headerView.getHeaderHeight() + headerHeight, 100);
				// 网络请求罗网络的接口
				if (mListener != null) {
					mListener.onRefresh();
				}
			}
			startY = 0.0f;
			break;

		}
		return super.onTouchEvent(ev);
	}

	// 下拉刷新头部headerView
	public void resetHeaderView() {
		mScroller.startScroll(0, headerView.getHeaderHeight(), 0,
				-headerView.getHeaderHeight(), 100);
		headerView.resetView();
	}

	private void updateHeaderState(float delatY) {
		headerView
				.setHeaderHeight((int) (delatY + headerView.getHeaderHeight()));
		if (headerView.getCurrentState() != HeaderView.STATE_REFRESHING) {
			if (headerView.getHeaderHeight() > headerHeight) {
				headerView.setState(HeaderView.STATE_PULL);
			} else {
				headerView.setState(HeaderView.STATE_NORMAL);
			}
		}
		// ע��
		setSelection(0);
	}

	private boolean canPullToRefreshViw() {
		if (headerView.getCurrentState() == HeaderView.STATE_NORMAL
				|| headerView.getCurrentState() == HeaderView.STATE_PULL) {
			return true;
		}
		return false;
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			headerView.setHeaderHeight(mScroller.getCurrY());
		}
	}

	private RequestRefreshListener mListener;

	// 刷新的接口
	public interface RequestRefreshListener {
		void onRefresh();
	}

	public void setRequestRefreshListView(RequestRefreshListener mListener) {
		this.mListener = mListener;
	}

	private android.widget.AdapterView.OnItemClickListener mItemListener;
	private android.widget.AdapterView.OnItemLongClickListener mItemLongListener;

	@Override
	public void setOnItemClickListener(
			android.widget.AdapterView.OnItemClickListener listener) {
		mItemListener = listener;
		super.setOnItemClickListener(new OnItemClickListener());
	}

	@Override
	public void setOnItemLongClickListener(
			android.widget.AdapterView.OnItemLongClickListener listener) {
		mItemLongListener = listener;
		super.setOnItemLongClickListener(new OnItemLongClickListener());
	}

	private class OnItemClickListener implements
			android.widget.AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (position != 0) {
				mItemListener.onItemClick(parent, view, position
						- getHeaderViewsCount(), id);
			}
		}

	}

	private class OnItemLongClickListener implements
			android.widget.AdapterView.OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			if (position != 0) {
				return mItemLongListener.onItemLongClick(parent, view, position
						- getHeaderViewsCount(), id);
			}
			return false;
		}
	}
}
