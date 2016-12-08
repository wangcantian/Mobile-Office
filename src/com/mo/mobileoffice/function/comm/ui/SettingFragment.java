package com.mo.mobileoffice.function.comm.ui;

import android.content.Intent;
import android.view.View;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.app.ActivityCollector;
import com.mo.mobileoffice.common.mvp.MvpIdeaFragment;
import com.mo.mobileoffice.function.comm.contract.SettingContract;
import com.mo.mobileoffice.function.comm.contract.SettingContract.Presenter;
import com.mo.mobileoffice.function.comm.presenter.SettingPresenter;
import com.mo.mobileoffice.function.user.ui.LoginActivity;

public class SettingFragment extends MvpIdeaFragment<SettingContract.Presenter>
		implements SettingContract.View {

	@Override
	protected void init() {
		setTitle(getResources().getString(R.string.setting));
		findViewById(R.id.tv_exit).setOnClickListener(this);
		findViewById(R.id.tv_clear_cache).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.tv_exit:
			ActivityCollector.finishAll();
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getActivity().startActivity(intent);
			break;
		case R.id.tv_clear_cache:
			getPresenter().clearCache();
			break;
		}
	}

	@Override
	public void rightOnClick() {

	}

	@Override
	protected Presenter createPresenter() {
		return new SettingPresenter(getActivity());
	}

	@Override
	protected int setContentViewId() {
		return R.layout.fragment_setting;
	}

}
