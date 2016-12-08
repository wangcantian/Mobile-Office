package com.mo.mobileoffice.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.base.BasePopView;
import com.mo.mobileoffice.common.tool.DisplayTool;

public class SlipPopWin extends PopupWindow implements OnTouchListener {
	private static final int DOWNUP = 0; // 手指离开触屏
	private static final int DOWNINCONTROLBAR = 1;// 手指按在下拉栏上
	private static final int DOWNINOUTSIDE = 2;// 手指按在内容区域的外部
	
	private static final int DURATION = 300;// 默认动画的时间隔（单位为毫秒）
	private static final int ALPHA = 150;// 默认的背景透明值为150,也就是最大值
	private static final float BACK_Y = 0.35f;// 往下拉回弹Y值的百分比

	private Context mContext;
	private LinearLayout mContent;// 主内容区（包含下拉区和显示内容去）
	private RelativeLayout mControlBar;// 下拉栏区
	private RelativeLayout mOutside;// 半透明背景区
	private FrameLayout mContentView;// 显示内容区
	private OnScrollListener mListener; // 滑动监听

	private int mHeight;// 内容区域显示的高度
	private boolean mIsAnim = false;// 当前是否在进行动画
	private int mCurrDownRongle = DOWNUP;// 记录手指按下的区域
	private double mAlp_Acceleration;// 透明度变化加速度
	private double mMove_Start_Speed;// 上升滑动的初始速度
	private double mMove_Acceleration;// 滑动的加速度

	public SlipPopWin(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	@SuppressLint("InflateParams") 
	private void init() {
		mHeight = (int) (DisplayTool.getScreenHeight(mContext) * 0.7f);
		mMove_Acceleration = mHeight * 2 / Math.pow(DURATION, 2);
		mMove_Start_Speed = Math.sqrt(2 * mMove_Acceleration * mHeight);
		mAlp_Acceleration = ALPHA * 2 / Math.pow(DURATION, 2);
		

		View mView = LayoutInflater.from(mContext).inflate(R.layout.layout_popwindow, null);
		mControlBar = (RelativeLayout) mView.findViewById(R.id.rl_controlbar);
		mOutside = (RelativeLayout) mView.findViewById(R.id.rl_outside);
		mContentView = (FrameLayout) mView.findViewById(R.id.fl_content);
		mContent = (LinearLayout) mView.findViewById(R.id.ll_content);
		LayoutParams layoutParams = mContent.getLayoutParams();
		layoutParams.height = mHeight;
		mContent.setLayoutParams(layoutParams);
		mOutside.getBackground().setAlpha(ALPHA);

		setContentView(mView);
		setFocusable(true);
		setTouchable(true);
		setOutsideTouchable(true);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.MATCH_PARENT);
		ColorDrawable cd = new ColorDrawable(mContext.getResources().getColor(android.R.color.transparent));
		setBackgroundDrawable(cd);
		setTouchInterceptor(this);
	}

	private int mDownY = -1;

	@SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouch(View v, MotionEvent event) {
		if (!mIsAnim) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
	
				if (mCurrDownRongle == DOWNINOUTSIDE) {
					hiddenContent(0);
				} else if (mCurrDownRongle == DOWNINCONTROLBAR) {
					int scrollY = mContent.getScrollY();
					if (mHeight * BACK_Y < Math.abs(scrollY) || scrollY == 0) {
						hiddenContent(scrollY);
					} else {
						mContent.scrollTo(0, 0);
						mOutside.getBackground().setAlpha(ALPHA);
					}
				}
	
				mCurrDownRongle = DOWNUP;
				break;
			case MotionEvent.ACTION_DOWN:
				mDownY = (int) event.getRawY();
	
				int[] location = new int[2];
				mControlBar.getLocationOnScreen(location);
				if (mDownY >= location[1] && mDownY <= location[1] + mControlBar.getHeight()) {
					mCurrDownRongle = DOWNINCONTROLBAR;
				} else if (mDownY < location[1]) {
					mCurrDownRongle = DOWNINOUTSIDE;
				}
	
				break;
			case MotionEvent.ACTION_MOVE:
	
				int moveY = (int) (event.getRawY() - mDownY);
				if (mCurrDownRongle == DOWNINCONTROLBAR && moveY > 0) {
					float precent = moveY / Float.valueOf(mHeight);
					mOutside.getBackground().setAlpha(ALPHA - (int)(ALPHA * precent));
					mContent.scrollTo(0, -moveY);
				}
			default:
				break;
			}
		}
		return false;
	}
	
	public void setPopView(BasePopView popView) {
		mContentView.addView(popView.getView(), new android.widget.LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
		popView.setSlipPopWin(this);
		popView.OnCreate();
	}
	
	/** 设置内容区View **/
	public void setMainView(View view) {
		mContentView.addView(view, new android.widget.LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT));
	}
	
	/** 设置滑动监听 **/
	public void setOnScrollListener(OnScrollListener listener) {
		this.mListener = listener;
	}
	
	private boolean mIsSuperDismiss = false;
	@Override
	public void dismiss() {
		if (!mIsSuperDismiss)
			hiddenContent(0);
		else
			super.dismiss();
	}

	@Override
	public void showAsDropDown(View anchor) {
		showContent();
		super.showAsDropDown(anchor);
	}

	@Override
	public void showAsDropDown(View anchor, int xoff, int yoff) {
		showContent();
		super.showAsDropDown(anchor, xoff, yoff);
	}

	@SuppressLint("NewApi")
	@Override
	public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
		showContent();
		super.showAsDropDown(anchor, xoff, yoff, gravity);
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		showContent();
		super.showAtLocation(parent, gravity, x, y);
	}

	private void showContent() {
		ShowAsyn show = new ShowAsyn();
		show.execute();
	}

	private void hiddenContent(int scrollY) {
		HiddenAsyn hidden = new HiddenAsyn();
		hidden.execute(scrollY);
	}

	private class HiddenAsyn extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... params) {
			mIsAnim = true;
			float percent = (mHeight - Math.abs(params[0])) / Float.valueOf(mHeight);
			float alp = ALPHA * percent;
			int times = (int) (DURATION * percent);
			for (int i = 5; i <= times; i+= 5) {
				publishProgress((int)(Math.abs(params[0]) + mMove_Acceleration * i * i), (int)(alp - (mAlp_Acceleration * i * i) / 2));
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			mContent.scrollTo(0, -values[0]);
			mOutside.getBackground().setAlpha(values[1]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (mListener != null) {
				mListener.OnScrollToEnd();
			}
			mIsSuperDismiss = true;
			mIsAnim = false;
			SlipPopWin.this.dismiss();
		}
	}
	
	private class ShowAsyn extends AsyncTask<Void, Integer, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			mIsAnim = true;
			for (int i = 5; i <= DURATION; i += 5) {
				publishProgress((int)(mMove_Start_Speed * i - (mMove_Acceleration * i * i) / 2), (int)(mAlp_Acceleration * i * i) / 2);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			mContent.scrollTo(0, -mHeight + values[0]);
			mOutside.getBackground().setAlpha(values[1]);
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (mListener != null) {
				mListener.OnScrollToTopEnd();
			}
			mIsAnim = false;
		}
		
	}
	
	public interface OnScrollListener {
		/** 滑动到顶部监听 **/
		public void OnScrollToTopEnd();
		
		/** 滑动到底部监听 **/
		public void OnScrollToEnd();
	}
}
