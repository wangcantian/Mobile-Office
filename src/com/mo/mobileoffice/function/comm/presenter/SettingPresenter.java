package com.mo.mobileoffice.function.comm.presenter;

import android.content.Context;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.mvp.BaseMvpPresenter;
import com.mo.mobileoffice.common.tool.FileTool;
import com.mo.mobileoffice.function.comm.contract.SettingContract;

public class SettingPresenter extends BaseMvpPresenter<SettingContract.View>
		implements SettingContract.Presenter {

	public SettingPresenter(Context context) {
		super(context);
	}

	@Override
	public void detachView(boolean retainInstance) {
		
	}

	@Override
	public void clearCache() {
		showProgressDialog();
		boolean flag = FileTool.clearCDCardCache();
		dismissProgressDialog();
		if (flag) {
			toastShowOnUI(R.string.clear_cache_success);
		} else {
			toastShowOnUI(R.string.clear_cache_fail);
		}
	}

}
