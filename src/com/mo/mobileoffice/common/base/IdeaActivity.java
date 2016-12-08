package com.mo.mobileoffice.common.base;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.app.FragmentFactory;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;

/**
 * 所有含有titlebar的activity类，内容区只能是继承IdeaFragment的碎片
 * @author Paul
 *
 */
public class IdeaActivity extends BaseActivity {
	private FragmentEnum mFragmentType;
	private MvpIdeaFragment<?> mFragment;

	private TextView tv_left;
	private TextView tv_center;
	private TextView tv_right;

	@Override
	protected void init() {
		mFragmentType = (FragmentEnum) getIntent().getSerializableExtra("fragment_type");
		
		tv_left = findViewByIds(R.id.tv_left);
		tv_center = findViewByIds(R.id.tv_center);
		tv_right = findViewByIds(R.id.tv_right);
		
		tv_left.setOnClickListener(this);
		tv_right.setOnClickListener(this);
		
		tv_center.setText("");
		tv_right.setText("");
		mFragment = FragmentFactory.getFragment(mFragmentType);
		getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_left:
			mFragment.leftOnClick();
			break;
		case R.id.tv_right:
			mFragment.rightOnClick();
		default:
			break;
		}
	}

	@Override
	protected int setContentViewId() {
		return R.layout.activity_idea;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		FragmentFactory.removeFrag(mFragmentType);// 页面销毁记得remove当前碎片
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return mFragment.onKeyDown(keyCode, event) == false ? super.onKeyDown(keyCode, event) : true;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	public void setTitle(String str) {
		tv_center.setText(str);
	}
	
	public void setRight(String str) {
		tv_right.setText(str);
	}

	public void setRightEnabled(boolean enabled) {
		tv_right.setEnabled(enabled);
	}

	
}
