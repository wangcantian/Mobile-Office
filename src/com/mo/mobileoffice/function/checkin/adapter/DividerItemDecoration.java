package com.mo.mobileoffice.function.checkin.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.State;
import android.util.TypedValue;
import android.view.View;

public class DividerItemDecoration extends ItemDecoration {
	private int mOrientation = LinearLayoutManager.VERTICAL;
	private int mItemSize = 1;

	private Paint mPaint = null;

	public DividerItemDecoration(Context context, int orientation) {
		this.mOrientation = orientation;
		if (orientation != LinearLayoutManager.VERTICAL) {
			throw new IllegalArgumentException("请传入正常参数");
		}
		mItemSize = (int) TypedValue.applyDimension(mItemSize,
				TypedValue.COMPLEX_UNIT_DIP, context.getResources()
						.getDisplayMetrics());
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.DKGRAY);
		mPaint.setStyle(Paint.Style.FILL);
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent, State state) {
		super.onDraw(c, parent, state);
		if (mOrientation == LinearLayoutManager.VERTICAL) {
			drawVertical(c, parent);
		}
	}

	private void drawVertical(Canvas c, RecyclerView parent) {
		final int left = parent.getPaddingLeft();
		final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
		int size = parent.getChildCount();
		for (int i = 0; i < size; i++) {
			final View view = parent.getChildAt(i);
			RecyclerView.LayoutParams lp = (LayoutParams) view
					.getLayoutParams();
			final int top = view.getBottom() + lp.bottomMargin;
			final int bottom = top + mItemSize;
			c.drawRect(left, top, right, bottom, mPaint);
		}
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
			State state) {
		super.getItemOffsets(outRect, view, parent, state);
		if (mOrientation == LinearLayoutManager.VERTICAL) {
			outRect.set(0, 0, 0, mItemSize);
		}
	}
}
