package com.mo.mobileoffice.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.mo.mobileoffice.R;

public class DeletableEditText extends EditText implements TextWatcher, OnFocusChangeListener {
	private Drawable mRightDrawable;

	public DeletableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public DeletableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DeletableEditText(Context context) {
		super(context);
		init();
	}

	private void init() {
		this.addTextChangedListener(this);
		this.setOnFocusChangeListener(this);
		mRightDrawable = getCompoundDrawables()[2];
		if (mRightDrawable == null) {
			mRightDrawable = getResources().getDrawable(R.drawable.ico_del);
		}
		mRightDrawable.setBounds(0, 0, mRightDrawable.getIntrinsicWidth(), mRightDrawable.getIntrinsicWidth());
	}

	private void setIcoVisiable(boolean visible) {
		Drawable drawable = visible ? mRightDrawable : null;
		setCompoundDrawables(getCompoundDrawables()[0],
				getCompoundDrawables()[1], drawable, getCompoundDrawables()[3]);
	}
	
	@SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouchEvent(MotionEvent event) {
		if (getCompoundDrawables()[2] != null) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				boolean touchable = event.getX() > (getWidth() - getPaddingRight() - mRightDrawable.getIntrinsicWidth())
									&& event.getX() < (getWidth() - getPaddingRight());
				if (touchable) {
					setText("");
				}
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		setIcoVisiable(s.length() > 0);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			setIcoVisiable(getText().length() > 0);
		} else {
			setIcoVisiable(false);
		}
	}
}
