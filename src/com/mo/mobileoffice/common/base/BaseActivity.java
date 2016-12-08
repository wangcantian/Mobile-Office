package com.mo.mobileoffice.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.mo.mobileoffice.common.app.ActivityCollector;
import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.widget.DefaultToast;

public abstract class BaseActivity extends FragmentActivity implements OnClickListener {
	
	protected abstract int setContentViewId();
	
	protected abstract void init();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(setContentViewId());
		init();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends View> T findViewByIds(int id) {
		return (T) this.findViewById(id);
	}
	
	protected void toastShow(String msg) {
		DefaultToast.show(this, msg);
	}
	
	protected void openActivity(Class<?> toActivityClass) {
		openActivity(toActivityClass, null);
	}

	protected void openActivity(Class<?> toActivityClass, Bundle bundle) {
		Intent intent = new Intent(this, toActivityClass);
		if (bundle != null)
			intent.putExtras(bundle);
		startActivity(intent);
	}

	protected void openActivityForResult(Class<?> toActivityClass, int requestCode) {
		openActivityForResult(toActivityClass, requestCode, null);
	}

	protected void openActivityForResult(Class<?> toActivityClass, int requestCode, Bundle bundle) {
		Intent intent = new Intent(this, toActivityClass);
		if (bundle != null)
			intent.putExtras(bundle);
		startActivityForResult(intent, requestCode);
	}
	
	protected void openIdeaActivity(FragmentEnum fragType) {
		openIdeaActivity(fragType, null);
	}
	
	protected void openIdeaActivity(FragmentEnum fragType, Bundle bundle) {
		if (bundle == null)
			bundle = new Bundle();
		bundle.putSerializable("fragment_type", fragType);
		openActivity(IdeaActivity.class, bundle);
	}
	
	protected void openIdeaActivityForResult(FragmentEnum fragType, int requestCode) {
		openIdeaActivityForResult(fragType, requestCode, null);
	}

	protected void openIdeaActivityForResult(FragmentEnum fragType, int requestCode, Bundle bundle) {
		if (bundle == null)
			bundle = new Bundle();
		bundle.putSerializable("fragment_type", fragType);
		openActivityForResult(IdeaActivity.class, requestCode, bundle);
	}
}
