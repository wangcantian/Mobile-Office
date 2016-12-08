package com.mo.mobileoffice.function.comm.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.ActivityCollector;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.app.FragmentFactory;
import com.mo.mobileoffice.common.widget.DefaultToast;

/** 主页面，侧滑页 **/
public class MainActivity extends SlidingFragmentActivity {
	private static final int FRAGMENT_CONTENT_ID = 0x1;
	private static final int FRAGMENT_LEFT_MANU_ID = 0x2;
	private static final String FRAGMENT_CONTENT = "fragment_content";
	private static final String FRAGMENT_LEFT_MANU = "fragment_left_manu";
	private final int mExitTime = 2000;

	private long mCurTime = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(createFrameLayout(FRAGMENT_CONTENT_ID));
		initSlideMenu();
		initFragment();
	}

	// 初始化侧滑菜单
	private void initSlideMenu() {
		setBehindContentView(createFrameLayout(FRAGMENT_LEFT_MANU_ID));
		SlidingMenu mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT);
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		mSlidingMenu.setShadowDrawable(null);
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlidingMenu.setFadeDegree(0.35f);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		mSlidingMenu.setBehindScrollScale(0.0f);
	}
	
	private FrameLayout createFrameLayout(int id) {
		FrameLayout frame = new FrameLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		frame.setLayoutParams(params);
		frame.setId(id);
		return frame;
	}

	private void initFragment() {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		ft.replace(FRAGMENT_LEFT_MANU_ID, FragmentFactory.getFragment(FragmentEnum.FRAGMENT_LEFTMENU), FRAGMENT_LEFT_MANU);
		ft.replace(FRAGMENT_CONTENT_ID, FragmentFactory.getFragment(FragmentEnum.FRAGMENT_CONTNET), FRAGMENT_CONTENT);

		ft.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!getSlidingMenu().isMenuShowing()) {
				if (System.currentTimeMillis() - mCurTime > mExitTime) {
					DefaultToast.show(this, "再按一次退出程序");
					mCurTime = System.currentTimeMillis();
					return true;
				} else {
					finish();
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 获取左侧边栏对象
	 * 
	 * @return
	 */
	public LeftMenuFragment getLeftMenuFragment() {
		FragmentManager fm = getSupportFragmentManager();
		return (LeftMenuFragment) fm.findFragmentByTag(FRAGMENT_LEFT_MANU);
	}

	/**
	 * 获取主页面fragment
	 * 
	 * @return
	 */
	public ContentFragment getContentFragment() {
		FragmentManager fm = getSupportFragmentManager();
		return (ContentFragment) fm.findFragmentByTag(FRAGMENT_CONTENT);
	}
	
	@Override
	protected void onDestroy() {
		//主页面推出后clear所有的fragment
		super.onDestroy();
		FragmentFactory.clear();
		ActivityCollector.removeActivity(this);
	}

}
