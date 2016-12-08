package com.mo.mobileoffice.function.approval.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;

import com.mo.mobileoffice.common.app.FragmentEnum;
import com.mo.mobileoffice.common.base.IdeaActivity;
import com.mo.mobileoffice.common.widget.DefaultToast;

public abstract class  ChildBaseFragment extends Fragment implements OnClickListener,OnItemClickListener {
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(setContentViewId(), container, false);
	}

	protected abstract int setContentViewId();
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	protected abstract void init();
	
	@SuppressWarnings("unchecked")
	public <T> T findViewById(int id){
		return (T)getView().findViewById(id);
	}
	
	
	protected void ToastShow(String msg) {
		DefaultToast.show(getActivity(), msg);
	}
	
	protected void openActivity(Class<?> toActivityClass) {
		openActivity(toActivityClass, null);
	}

	protected void openActivity(Class<?> toActivityClass, Bundle bundle) {
		Intent intent = new Intent(getActivity(), toActivityClass);
		if (bundle != null)
			intent.putExtras(bundle);
		startActivity(intent);
	}

	protected void openActivityForResult(Class<?> toActivityClass, int requestCode) {
		openActivityForResult(toActivityClass, requestCode, null);
	}

	protected void openActivityForResult(Class<?> toActivityClass, int requestCode, Bundle bundle) {
		Intent intent = new Intent(getActivity(), toActivityClass);
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
