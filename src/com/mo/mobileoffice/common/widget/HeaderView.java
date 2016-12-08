package com.mo.mobileoffice.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mo.mobileoffice.R;

public class HeaderView extends LinearLayout {
	public static final int STATE_NORMAL = 0;
	public static final int STATE_PULL = 1;
	public static final int STATE_REFRESHING = 2;

	private int currentState = STATE_NORMAL;

	private ProgressBar progressbar;
	private ImageView arrow;
	private TextView header_Text;
	private LinearLayout header;
	//����
	private RotateAnimation startRotate;
	private RotateAnimation endRotate;
	

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HeaderView(Context context) {
		this(context, null);
		initView(context);
	}

	private void initView(Context context) {
		header = (LinearLayout) View.inflate(context, R.layout.layout_listheader, null);
		progressbar = (ProgressBar) header.findViewById(R.id.progressbar);
		arrow = (ImageView) header.findViewById(R.id.arrow);
		header_Text = (TextView) header.findViewById(R.id.header_text);
//		header = (RelativeLayout) view.findViewById(R.id.header);
		
		startRotate = new RotateAnimation(0, 180,RotateAnimation.RELATIVE_TO_SELF,0.5f, RotateAnimation.RELATIVE_TO_SELF,0.5f);
		endRotate = new RotateAnimation(-180,0,RotateAnimation.RELATIVE_TO_SELF,0.5f, RotateAnimation.RELATIVE_TO_SELF,0.5f);
		setRotateAnimation();
		
		LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		this.addView(header, lp);
		this.setGravity(Gravity.BOTTOM);
	}
	
	
	private void setRotateAnimation(){
		startRotate.setDuration(100);
		startRotate.setFillAfter(true);
		endRotate.setDuration(100);
		endRotate.setFillAfter(true);
	}

	public void setState(int state) {
		if (state == currentState) {
			return;
		}

		switch (state) {
		case STATE_NORMAL:
			progressbar.setVisibility(View.GONE);
			arrow.setVisibility(View.VISIBLE);
			header_Text.setText(R.string.refresh_header_text);
			arrow.clearAnimation();
			arrow.startAnimation(endRotate);
			break;
		case STATE_PULL:
			progressbar.setVisibility(View.GONE);
			arrow.setVisibility(View.VISIBLE);
			header_Text.setText(R.string.refresh_header_release_to_text);
			arrow.clearAnimation();
			arrow.startAnimation(startRotate);
			break;
		case STATE_REFRESHING:
			progressbar.setVisibility(View.VISIBLE);
			arrow.setVisibility(View.GONE);
			header_Text.setText(R.string.refresh_header_refreshing);
			arrow.clearAnimation();
			break;
		}

		currentState = state;
	}

	public int getHeaderHeight() {
		return header.getHeight();
	}

	public void setCurrentState(int state) {
		currentState = state;
	}

	public int getCurrentState() {
		return currentState;
	}
	
	public void resetView(){
		progressbar.setVisibility(View.GONE);
		arrow.setVisibility(View.VISIBLE);
		header_Text.setText(R.string.refresh_header_text);
		currentState = STATE_NORMAL;
	}

	public void setHeaderHeight(int height) {
		if (height <=0) {
			height = 0;
		}
		LayoutParams lp = (LinearLayout.LayoutParams) header.getLayoutParams();
		lp.height = height;
		header.setLayoutParams(lp);
	}
}
